package net.easyUI.common.web.contain;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import net.easyUI.common.profiler.TimeProfiler;
import net.easyUI.common.web.velocity.eventhandler.DirectOutput;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.tools.view.context.ViewContext;

public class Contain {
	private static final Log log = LogFactory.getLog(Contain.class);

	/**
	 * 保存在request中,contain被调用的标记key
	 */
	private static final String ContainFlagKey = "iContain";

	protected ViewContext viewContext;

	private HttpServletRequest request;

	private HttpServletResponse response;

	private static final int recursiveLevel = 20;

	private static final ThreadLocal<VaryInt> count = new ThreadLocal<VaryInt>();// 递归调用计数器

	public Contain() {
		super();
	}

	public void init(Object obj) {
		if (!(obj instanceof ViewContext)) {
			throw new IllegalArgumentException(
					"Tool can only be initialized with a ViewContext");
		}
		viewContext = (ViewContext) obj;
		this.request = viewContext.getRequest();
		this.response = viewContext.getResponse();
	}

	public ControlRender setTemplate(String controlName) {
		if (controlName == null) {
			throw new NullPointerException(
					"control template name can't be null.");
		}
		return new ControlRender(controlName);
	}

	public ControlRender get(String controlName) {
		return setTemplate(controlName);
	}

	public class ControlRender implements DirectOutput {

		private String controlName;
		private String encoding = "UTF-8";

		private Set<ContainParameter> parameters = new HashSet<ContainParameter>();

		public ControlRender(String controlName) {
			this.controlName = controlName;
		}

		public ControlRender addQueryData(String key, Object value) {
			// TODO(是否应该删掉?)
			// 保留request中老的value，当渲染完后重新设置,也就是说，传递给contain的参数作用域仅限于contain的invoke
//			this.put(key, value);
			// 同时,往URL中添加URL的参数,以便支持传递参数.
			if (value == null)
				value = "";
			try {
				String kv = key
						+ "="
						+ URLEncoder.encode((String.valueOf(value)),
								this.encoding);
				if (this.controlName.indexOf("?") < 0)
					this.controlName = this.controlName + "?" + kv;
				else
					this.controlName = this.controlName + "&" + kv;
			} catch (UnsupportedEncodingException e) {
				log.error("addQueryData参数添加到URL发生异常:", e);
			}
			return this;
		}

		/**
		 * 与addQueryData相似,不过,只是将参数放在Request中,没有放到URL的参数中,会导致Action的参数中注入变量无效,
		 * 但是在Request中可以获取.
		 * 
		 * @param key
		 * @param value
		 * @return
		 */
		public ControlRender put(String key, Object value) {
			// 保留request中老的value，当渲染完后重新设置,也就是说，传递给contain的参数作用域仅限于contain的invoke
			parameters.add(new ContainParameter(Contain.this.request, key,
					value));
			return this;
		}

		public ControlRender setParameter(String key, Object value) {
			return this.put(key, value);
		}

		@Override
		public String toString() {
			if (enter()) {
				log.error("contain recursive invoked,so exist.");
				return "";
			}
			parameters.add(new ContainParameter(Contain.this.request,
					ContainFlagKey, Boolean.TRUE));
			ResponseWrapper responseWrapper = new ResponseWrapper(
					Contain.this.response);
			if (TimeProfiler.isProfileEnable()) {
				TimeProfiler.beginTask("execute contain :" + controlName);
			}
			try {
				Contain.this.request.getRequestDispatcher(controlName).include(
						Contain.this.request, responseWrapper);
				String back = responseWrapper.sw.toString();
				if (this.parameters != null) {
					for (ContainParameter cp : this.parameters) {
						cp.recover(Contain.this.request);
					}
				}
				return back;
			} catch (ServletException e) {
				if (log.isErrorEnabled()) {
					log.error("error in control render.", e);
				}
				return e.getMessage();
			} catch (IOException e) {
				if (log.isErrorEnabled()) {
					log.error("error in control render.", e);
				}
				return e.getMessage();
			} finally {
				if (TimeProfiler.isProfileEnable()) {
					TimeProfiler.endTask();
				}
				leave();
			}
		}

		/**
		 * 调用了一次,返回是否超过递归层次
		 * 
		 * @return
		 */
		private boolean enter() {
			VaryInt vi = count.get();
			if (vi == null) {
				vi = new VaryInt();
				vi.add();
				count.set(vi);
				return false;
			}
			int now = vi.add();
			if (now >= recursiveLevel) {
				vi.i = 0;
				return true;
			}
			return false;
		}

		private void leave() {
			VaryInt vi = count.get();
			if (vi == null) {// 不能吧?
				count.set(new VaryInt());
				return;
			}
			vi.sub();
		}
	}

	private static class ContainParameter {
		private String key;
		private Object original;

		public ContainParameter(HttpServletRequest request, String key,
				Object newValue) {
			this.key = key;
			this.original = request.getAttribute(key);
			request.setAttribute(key, newValue);
		}

		public void recover(HttpServletRequest request) {
			request.setAttribute(this.key, this.original);
		}

	}

	private class ResponseWrapper extends HttpServletResponseWrapper {
		public StringWriter sw = new StringWriter();

		public ResponseWrapper(HttpServletResponse r) {
			super(r);
		}

		@Override
		public ServletOutputStream getOutputStream() throws IOException {
			return new ServletOutputStream() {
				@Override
				public void write(int b) throws IOException {
					sw.write(b);
				}
			};
		}

		@Override
		public PrintWriter getWriter() throws IOException {
			return new PrintWriter(sw);
		}

		@Override
		public void reset() {
		}

		@Override
		public void resetBuffer() {
		}

		@Override
		public boolean isCommitted() {
			return false;
		}

		@Override
		public void flushBuffer() throws IOException {

		}

		@Override
		public void setBufferSize(int arg0) {

		}

		@Override
		public void setCharacterEncoding(String arg0) {

		}

		@Override
		public void setContentLength(int arg0) {

		}

		@Override
		public void setLocale(Locale arg0) {

		}

		@Override
		public void addDateHeader(String arg0, long arg1) {

		}

		@Override
		public void addHeader(String arg0, String arg1) {

		}

		@Override
		public void addIntHeader(String arg0, int arg1) {

		}

		@Override
		public void setDateHeader(String arg0, long arg1) {

		}

		@Override
		public void setHeader(String arg0, String arg1) {

		}

		@Override
		public void setIntHeader(String arg0, int arg1) {

		}

		@Override
		public void setStatus(int arg0, String arg1) {

		}

		@Override
		public void setStatus(int arg0) {

		}
	}

	private static final class VaryInt {
		int i = 0;

		public int add() {
			i++;
			return i;
		}

		public int sub() {
			i--;
			return i;
		}
	}
}
