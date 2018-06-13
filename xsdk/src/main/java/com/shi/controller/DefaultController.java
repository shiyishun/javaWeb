package com.shi.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DefaultController {

	private static Logger logger = LogManager
			.getLogger(DefaultController.class);
	
	@RequestMapping("/index")
	public void index(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.sendRedirect("demo/login.html");
		} catch (IOException e) {
			logger.error(e.toString());
			
		}
	}
}
