package com.shi.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.shi.common.RespJsonUtils;
import com.shi.entity.Permi;
import com.shi.entity.User;

/**
 * 权限认证的拦截器
 */
public class AuthInterceptor implements HandlerInterceptor {

	/**
	 * Handler执行完成之后调用这个方法
	 */
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception exc)
			throws Exception {

	}

	/**
	 * Handler执行之后，ModelAndView返回之前调用这个方法
	 */
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	/**
	 * Handler执行之前调用这个方法
	 */
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		// 获取请求的URL
		String url = request.getRequestURI();
		HttpSession session = request.getSession();
		List<Permi> noPermiList = (List<Permi>) session
				.getAttribute("noPermis");
		// 特殊用途的路径可以直接访问
		if (noPermiList != null && noPermiList.size() > 0) {
			for (Permi permi : noPermiList) {
				if (url.indexOf(permi.getReqUrl()) >= 0) {
					// 不符合条件的
					JSONObject data = new JSONObject();
					data.put("code", "101");
					data.put("errMsg", "未获得该权限");
					RespJsonUtils.json(response, data);
					return false;
				}
			}
		}

		return true;
	}

}
