package net.easyUI.web.resolver;


import net.easyUI.dto.common.UserAgent;
import net.easyUI.common.web.cookyjar.Cookyjar;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
/**
 * 注册一个Action方法参数自动注入类型,如:
 *  public String index(UserAgent agent, ModelMap model){...}
 * @author busfly
 *
 */
public class UserAgentArgumentResolver implements WebArgumentResolver {

	public Object resolveArgument(MethodParameter methodParameter,
			NativeWebRequest webRequest) throws Exception {
		if (methodParameter.getParameterType().equals(UserAgent.class)) {
			Cookyjar cookyjar = (Cookyjar) webRequest
					.getAttribute(Cookyjar.CookyjarInRequest,
							RequestAttributes.SCOPE_REQUEST);
			if (cookyjar != null) {
				return cookyjar.getObject(UserAgent.class);
			}
		}
		return UNRESOLVED;
	}
}
