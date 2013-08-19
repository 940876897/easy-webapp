package net.easyUI.common.web.adapter;


import java.lang.reflect.Method;

import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.ModelAndView;

public abstract class AnnotationMethodHandlerInterceptorAdapter implements
		AnnotationMethodHandlerInterceptor {

	public void preInvoke(Method handlerMethod, Object handler,
			ServletWebRequest webRequest) {
	}

	public void postInvoke(Method handlerMethod, Object handler,
			ServletWebRequest webRequest, ModelAndView mav) {
	}

}
