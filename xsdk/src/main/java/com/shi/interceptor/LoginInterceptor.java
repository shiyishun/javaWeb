package com.shi.interceptor;

import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.shi.common.ComUtil;
import com.shi.common.RespJsonUtils;
import com.shi.entity.User;

/**
 * 登录认证的拦截器
 */
public class LoginInterceptor implements HandlerInterceptor {

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

		/*
		 * //获取请求的URL String url = request.getRequestURI(); String[] strs = {
		 * "forget_pwd", "login", "logout", "img", "js", "img" }; //
		 * 特殊用途的路径可以直接访问 if (strs != null && strs.length > 0) { for (String str
		 * : strs) { if (url.indexOf(str) >= 0) { return true; } } }
		 */

		// HttpSession session = request.getSession();
		// User user = (User)session.getAttribute("user");
		// System.out.println(session.getId());
		// if(user != null){
		// return true;
		// }

		JSONObject data = new JSONObject();
		String token = request.getHeader("token");
		if (token == null) {
			data.put("code", "100");
			data.put("errmsg", "未获取认证");
			RespJsonUtils.json(response, data);
			return false;		
		}
		HashMap<String, Object> userMap =  ComUtil.loginMap.get(token);	
		if (userMap == null) {
			data.put("code", "100");
			data.put("errmsg", "用户未登陆");
			RespJsonUtils.json(response, data);
			return false;
		}
		Date timestampe = (Date) userMap.get("timestamp");
		Date newDate = new Date();
		long diff =  newDate.getTime() - timestampe.getTime();
	    System.out.println(diff);
		if(diff>30*60*1000){
			data.put("code", "101");
			data.put("errmsg", "用户登陆超时");
			RespJsonUtils.json(response, data);
			return false;		
		}
	   request.getSession().setAttribute("user", userMap.get("user"));
       return true;
	}

}
