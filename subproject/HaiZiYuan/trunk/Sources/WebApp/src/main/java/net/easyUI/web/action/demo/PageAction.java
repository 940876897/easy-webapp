package net.easyUI.web.action.demo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageAction {


	@RequestMapping("/demo/front/chatPage")
	public String chatPage(ModelMap model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return "/demo/front/chat";
	}
}
