package net.easyUI.web.action.common;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.easyUI.access.PageType;
import net.easyUI.service.common.CacheService;
import net.easyUI.service.common.CommonService;
import net.easyUI.common.dto.DwzJson;
import net.easyUI.common.dto.ServiceRequest;
import net.easyUI.common.dto.ServiceResult;
import net.easyUI.common.dto.enums.EnumDwzJsonStatusType;
import net.easyUI.common.web.action.BaseAction;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.AbstractMultipartHttpServletRequest;

@Controller
public class CommonAction extends BaseAction {
	@Autowired
	private CommonService commonService;
	@Autowired
	private CacheService cacheService;
	@Value("${cookie.lifeTime.imgCheckCode}")
	private Long imgCheckCodeLiveTime;

	@RequestMapping("/index")
	public String login(ModelMap model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return "/index";

	}

	/**
	 * ajax上传文件
	 * 
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/ajaxUpload")
	@PageType("JsonPage")
	public void ajaxUploadFile(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws IOException {
		if (request instanceof AbstractMultipartHttpServletRequest == false) {
			throw new RuntimeException(
					"Did not recieve an instance of AbstractMultipartHttpServletRequest.");
		}
		response.setCharacterEncoding("utf-8");
		Map<String, MultipartFile> paramToFileMap = ((AbstractMultipartHttpServletRequest) request)
				.getFileMap();
		ServiceRequest serseq = new ServiceRequest();
		serseq.setResDto(paramToFileMap.values());
		ServiceResult<List<String>> sr = commonService.uploadFileList(serseq);
		if (sr.error()) {
			throw new RuntimeException(
					"Did not recieve an instance of AbstractMultipartHttpServletRequest.");
		} else {
			List<String> fileUrls = sr.getDataObj();
			if (CollectionUtils.isEmpty(fileUrls))
				throw new RuntimeException(
						"Did not recieve an instance of AbstractMultipartHttpServletRequest.");
			response.getWriter().print(
					fileUrls.size() == 1 ? fileUrls.get(0) : fileUrls);
		}
	}

	/**
	 * TODO 表单上传文件
	 * 
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/upload")
	@PageType("JsonPage")
	@ResponseBody
	public DwzJson uploadFile(/* MultipartFile file, */HttpServletRequest req,
			ModelMap model) throws IOException {
		DwzJson dwzJson = new DwzJson();
		dwzJson = new DwzJson(EnumDwzJsonStatusType.ERROR.getCode(),
				EnumDwzJsonStatusType.ERROR.getDesc());
		return dwzJson;
	}

	// /**
	// * 更新角色权限缓存
	// */
	// @RequestMapping(value = "/cacheManager/reloadRolePermissionCache")
	// @PageType("JsonPage")
	// @ResponseBody
	// public DwzJson reloadRolePermissionCache(ModelMap model) {
	// DwzJson dwzJson = new DwzJson();
	// ServiceResult<String> sr = cacheService.reloadRolePermission();
	// if (sr.error()) {
	// dwzJson = new DwzJson(EnumDwzJsonStatusType.ERROR.getCode(),
	// EnumDwzJsonStatusType.ERROR.getDesc() + sr.getErrorInfo());
	// }
	// return dwzJson;
	// }

	/**
	 * 更新系统配置缓存
	 */
	@RequestMapping(value = "/cacheManager/reloadWebConfigCache")
	@PageType("JsonPage")
	@ResponseBody
	public DwzJson reloadWebConfigCache(ModelMap model) {
		DwzJson dwzJson = new DwzJson();
		ServiceResult<Map<String, String>> sr = cacheService.reloadAllCache();
		if (sr.error()) {
			dwzJson = new DwzJson(EnumDwzJsonStatusType.ERROR.getCode(),
					EnumDwzJsonStatusType.ERROR.getDesc() + sr.getErrorInfo());
		}
		return dwzJson;
	}

	// /** 验证码的字体 */
	// private static final List<Font> englishFonts = Arrays.asList(new
	// Font("Lucida Sans",
	// Font.ITALIC, 60), new Font("SansSerif",
	// Font.ITALIC, 70));
	//
	// /**
	// * 简单的字符验证码
	// *
	// * <pre>
	// * &lt;span&gt;&lt;a href=&quot;JavaScript:changeCheckcode();&quot;
	// title=&quot;请输入此图片上的内容,看不清楚?点击换一张!&quot;&gt;&lt;img
	// id=&quot;imgCheckCode&quot;
	// src=&quot;$imageServer.get('style/images/imgCheckCode.jpg')&quot;
	// alt=&quot;请输入此图片上的内容,看不清楚?点击换一张!&quot; width=&quot;75&quot;
	// height=&quot;24&quot; /&gt;&lt;/a&gt;&lt;/span&gt;
	// * </pre>
	// */
	// @RequestMapping(value = "/imgCheckCode1")
	// public void simpleCheckCode(HttpServletResponse response, Cookyjar
	// cookyjar) throws IOException {
	// Captcha captcha = new Captcha.Builder(220, 80)
	// .addText(new EasyCharTextProducer(), new FixedWordRenderer(Color.black,
	// englishFonts))
	// .gimp(new RippleGimpyRenderer())
	// .gimp(new BlockGimpyRenderer())
	// .gimp(new DropShadowGimpyRenderer())
	// // .addNoise(new CurvedLineNoiseProducer(Color.black, 1.8f))
	// // .addNoise(new CurvedLineNoiseProducer(Color.black, 3.0f))
	// // .addNoise(new CurvedLineNoiseProducer(Color.black, 2.3f))
	// // .addNoise(new CurvedLineNoiseProducer())
	// // .addNoise(new CurvedLineNoiseProducer())
	// .addNoise(new CurvedLineNoiseProducer())
	// .addBackground(new GradiatedBackgroundProducer()).build();
	// String codeStr = captcha.getAnswer().toLowerCase();
	// Long milliseconds = new Date().getTime();
	// codeStr = Md5.md5Str(codeStr + milliseconds / imgCheckCodeLiveTime);
	// cookyjar.set("imgCheckCode", codeStr);
	// response.setHeader("Cache-Control", "no-store");
	// response.setHeader("Pragma", "no-cache");
	// response.setDateHeader("Expires", 0);
	// response.setContentType("image/jpeg");
	// OutputStream os = response.getOutputStream();
	// ImageIO.write(captcha.getImage(), "JPEG", os);
	// }
	//
	// /**
	// * 加减算术的验证码
	// *
	// * <pre>
	// * &lt;span&gt;&lt;a href=&quot;JavaScript:changeCheckcode();&quot;
	// title=&quot;请输入此图片上的数学运算结果,看不清楚?点击换一张!&quot;&gt;&lt;img
	// id=&quot;imgCheckCode&quot;
	// src=&quot;$imageServer.get('style/images/imgCheckCode.jpg')&quot;
	// alt=&quot;请输入此图片上的数学运算结果,看不清楚?点击换一张!&quot; width=&quot;75&quot;
	// height=&quot;24&quot; /&gt;&lt;/a&gt;&lt;/span&gt;
	// * </pre>
	// */
	// @RequestMapping(value = "/imgCheckCode2")
	// public void arithmeticCheckCode(HttpServletResponse response, Cookyjar
	// cookyjar)
	// throws IOException {
	// final Random random = new Random();
	// final int one = 1 + random.nextInt(49);
	// final int two = random.nextInt(49);
	// String text = "";
	// int value = 0;
	// if (random.nextBoolean()) {
	// text = new
	// StringBuilder().append(one).append('+').append(two).toString();
	// value = one + two;
	// } else {
	// if (one > two) {
	// text = new
	// StringBuilder().append(one).append('-').append(two).toString();
	// value = one - two;
	// } else {
	// text = new
	// StringBuilder().append(two).append('-').append(one).toString();
	// value = two - one;
	// }
	// }
	// String codeStr = value + "";
	// Long milliseconds = new Date().getTime();
	// codeStr = Md5.md5Str(codeStr + milliseconds / imgCheckCodeLiveTime);
	// cookyjar.set("imgCheckCode", codeStr);
	// final String _text = text;
	// Captcha captcha = new Captcha.Builder(220, 80)
	// .addText(new TextProducer() {
	// public String getText() {
	// return _text;
	// }
	// }, new FixedWordRenderer(Color.black, englishFonts))
	// .gimp(new RippleGimpyRenderer())
	// .gimp(new DropShadowGimpyRenderer())
	// // .addNoise(new CurvedLineNoiseProducer(Color.black, 2.8f))
	// // .addNoise(new CurvedLineNoiseProducer(Color.black, 1.3f))
	// // .addNoise(new CurvedLineNoiseProducer())
	// // .addNoise(new CurvedLineNoiseProducer())
	// .addNoise(new CurvedLineNoiseProducer())
	// .addBackground(new GradiatedBackgroundProducer()).build();
	// response.setHeader("Cache-Control", "no-store");
	// response.setHeader("Pragma", "no-cache");
	// response.setDateHeader("Expires", 0);
	// response.setContentType("image/jpeg");
	// OutputStream os = response.getOutputStream();
	// ImageIO.write(captcha.getImage(), "JPEG", os);
	// }

}
