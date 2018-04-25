package com.shi.controller;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shi.common.ComUtil;
import com.shi.common.DictUtil;
import com.shi.common.Page;
import com.shi.entity.Dict;
import com.shi.entity.Permi;
import com.shi.entity.Role;
import com.shi.entity.RolePermiRel;
import com.shi.entity.User;
import com.shi.entity.UserRoleRel;
import com.shi.service.PermiService;
import com.shi.service.RoleMngService;
import com.shi.service.UserMngService;
import com.shi.service.UserRoleRelService;

@Controller
@RequestMapping(value = "user_mng/")
public class UserMngController {
	@Autowired
	private UserMngService userService;
	@Autowired
	private RoleMngService roleMngService;
	@Autowired
	private UserRoleRelService userRoleRelService;
    @Autowired
    private PermiService permiService; 
	
	
	private static Logger logger = LogManager
			.getLogger(UserMngController.class);

	@ResponseBody
	@RequestMapping(value = "save")
	public List<Dict> save() {

		User user = new User();
		user.setUserNo("0");
		user.setUserName("张三");
		user.setPhone("18060905555");
		user.setPwd("123");
		Role role = roleMngService.findAll().get(0);
		UserRoleRel userRoleRel = new UserRoleRel();
		userRoleRel.setRole(role);
		userRoleRel.setUser(user);
		Set<UserRoleRel> userRoleRelSet = new HashSet<UserRoleRel>();
		userRoleRelSet.add(userRoleRel);
		user.setUserRoleRelSet(userRoleRelSet);
		userService.save(user);

		List<Dict> dictList = DictUtil.dictCategoryList.get("性别");

		return dictList;
	}

	@ResponseBody
	@RequestMapping(value = "page")
	public Page<User> page() {

		Page<User> page = userService.getPage("", null, 1, 5);

		return page;
	}

	@ResponseBody
	@RequestMapping(value = "json")
	public JSONObject json() {

		Page<User> page = userService.getPage("", null, 1, 5);
		JSONObject json = new JSONObject();
		json.put("code", "0");
		json.put("data", page);
		return json;
	}

	@ResponseBody
	@RequestMapping(value = "md5")
	public JSONObject md5() {

		String data = "1";
		JSONObject json = new JSONObject();
		try {
			MessageDigest md = MessageDigest.getInstance("md5");
			byte[] md5 = md.digest(data.getBytes());
			json.put("code", "0");
			json.put("data", ComUtil.toHex(md5));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			json.put("code", "1");
			json.put("data", "加密失败");
			logger.error(e.toString());
			e.printStackTrace();
		}

		return json;
	}

	public static void main(String[] args) {

		String data = "123456";
		try {
			MessageDigest md = MessageDigest.getInstance("md5");
			byte[] md5 = md.digest(data.getBytes());
			System.out.println(ComUtil.toHex(md5));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 用户登陆
	 * 
	 * @param request
	 * @param loginName
	 * @param password
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "login")
	public JSONObject login(HttpServletRequest request, String loginName,
			String password) {

		User user = userService.findByLoginName(loginName);
		JSONObject json = new JSONObject();
		if (user == null) {
			json.put("code", "1");
			json.put("errmsg", "账号不存在");
		} else if (!user.getPwd().equals(password)) {
			json.put("code", "2");
			json.put("errmsg", "密码错误");
		} else {
			request.getSession().setAttribute("user", user);
			json.put("code", "0");
			json.put("data", "");
		}
		return json;
	}

	/**
	 * 用户退出登陆
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "logout")
	public JSONObject login(HttpServletRequest request) {

		JSONObject json = new JSONObject();
		request.getSession().setAttribute("user", null);
		json.put("code", "0");
		json.put("data", "");
		return json;
	}
    /**
     * 查询登陆用户的所有权限
     * @param request
     * @return
     */
	@ResponseBody
	@RequestMapping(value = "permis")
	public JSONObject permis(HttpServletRequest request) {

		User user = (User) request.getSession().getAttribute("user");
		JSONObject json = new JSONObject();
		if (user == null) {
			json.put("code", "11");
			json.put("errMsg", "获取用户信息失败");
			return json;
		}
		List<Permi> permiList = new ArrayList<Permi>();
		permiList = permiService.findByUser(user);
		String jsonText = JSON.toJSONString(permiList, false);
		JSONArray obj= JSONArray.parseArray(jsonText);
		json.put("code", "0");
		json.put("data", obj);

		return json;
	}

}
