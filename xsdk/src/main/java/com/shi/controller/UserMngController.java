package com.shi.controller;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shi.common.ComUtil;
import com.shi.common.DictUtil;
import com.shi.common.Page;
import com.shi.entity.Dict;
import com.shi.entity.Menu;
import com.shi.entity.Permi;
import com.shi.entity.Role;
import com.shi.entity.RolePermiRel;
import com.shi.entity.SchoolInfo;
import com.shi.entity.TeachStu;
import com.shi.entity.User;
import com.shi.entity.UserRoleRel;
import com.shi.service.MenuService;
import com.shi.service.PermiService;
import com.shi.service.RoleMngService;
import com.shi.service.SchoolInfoService;
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
    @Autowired
    private MenuService menuService;
    @Autowired
    private SchoolInfoService schoolInfoService;
	
	
	private static Logger logger = LogManager
			.getLogger(UserMngController.class);

    /**
     * 分页查询
     * @param param 查询参数
     * @param no 页码
     * @param size 页记录条数
     * @return
     */
	@ResponseBody
	@RequestMapping(value = "page")
	public JSONObject page(String param, String no, String size) {
        
		if(no==null||no.equals("")){
			no = "1";
		}
		if(size==null||size.equals("")){
			size="10";
		}
		
	    int pageNo = Integer.valueOf(no);
		int pageSize = Integer.valueOf(size);
		Page<User> page = userService.getSqlPage(param, pageNo, pageSize);
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

//		String data = "1234567";
//		try {
//			MessageDigest md = MessageDigest.getInstance("md5");
//			byte[] md5 = md.digest(data.getBytes());
//			System.out.println(ComUtil.toHex(md5));
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		}
		
		
		
		
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
  
		String No = df.format(new Date())+((int)(Math.random()*9+1)*1000);
		System.out.println(No);
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
	public JSONObject login(HttpServletRequest request, HttpServletResponse response,
			String loginName, String password) {
		JSONObject json = new JSONObject();
		try {
			User user = userService.findByLoginName(loginName);

			if (user == null||user.getStatus()==2) {
				json.put("code", "1");
				json.put("errmsg", "账号不存在");
			}
			
			if (user.getStatus() == 1) {
				json.put("code", "1");
				json.put("errmsg", "账号已被锁定，请联系管理员");
				return json;
			}

			String pwd = ComUtil.toMd5Str(password);

			if (!user.getPwd().equals(pwd)) {
				json.put("code", "2");
				json.put("errmsg", "密码错误");
				return json;
			} 
				List<Permi> permiList = permiService.findByUser(user);
				List<Permi> noPermiList = permiService.HasNoPermis(permiList);
				// request.getSession().setAttribute("user", user);
				// request.getSession().setAttribute("permis", permiList);
				// request.getSession().setAttribute("noPermis", noPermiList);
				String token = ComUtil.GetGUID();
				HashMap<String, Object> userMap = new HashMap<String, Object>();
				userMap.put("user", user);
				userMap.put("permis", permiList);
				userMap.put("noPermis", noPermiList);
				userMap.put("timestamp", new Date());
				ComUtil.loginMap.put(token, userMap);
				json.put("code", "0");
				json.put("data", token);
				// HttpSession session = request.getSession();
				// System.out.println(session.getId());
				return json;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			json.put("code", "101");
			json.put("errmsg", "服务异常");
			return json;
		}
		
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
//		request.getSession().setAttribute("user", null);
//		request.getSession().setAttribute("permis", null);
//		request.getSession().setAttribute("noPermis", null);
		String token = request.getHeader("token");
		ComUtil.loginMap.remove(token);
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
	public JSONObject permis(HttpServletRequest request, HttpServletResponse response) {

//		User user = (User) request.getSession().getAttribute("user");
		String token = request.getHeader("token");
		HashMap<String, Object> userMap =  ComUtil.loginMap.get(token);	
		User user = (User) userMap.get("user");
		JSONObject json = new JSONObject();
		List<Permi> permiList = new ArrayList<Permi>();
		permiList = permiService.findByUser(user);
//	    List<Permi> permiList = (List<Permi>) request.getSession().getAttribute("permis");
//		for(Permi permi: permiList){
//			System.out.println(permi.getPermiName());
//		}
		String jsonText = JSON.toJSONString(permiList, false);
		JSONArray jsonArray= JSONArray.parseArray(jsonText);
		json.put("code", "0");
		json.put("data", jsonArray);

		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "has_menus")
    public JSONObject hasMenus(HttpServletRequest request, 
    		HttpServletResponse response){
		String token = request.getHeader("token");
		HashMap<String, Object> userMap =  ComUtil.loginMap.get(token);	
		User user = (User) userMap.get("user");
		List<Map<String, Object>> menuMapList = menuService.findByUser(user);
		JSONObject json = new JSONObject();
		json.put("code", "0");
		json.put("data", menuMapList);
		return json;
	}
	
	
	/**
	 * 修改密码
	 * @param request
	 * @param response
	 * @param orginPassword
	 * @param newPassword
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "password_modify")
    public JSONObject passwordModify(HttpServletRequest request, 
    		HttpServletResponse response,
    		String orginPassword, String newPassword){
	  
		String token = request.getHeader("token");
		HashMap<String, Object> userMap = ComUtil.loginMap.get(token);	
		User user = (User) userMap.get("user");
		
		String orginPwd;
		String newPwd;
		JSONObject json = new JSONObject();
		try {
			orginPwd = ComUtil.toMd5Str(orginPassword);
			newPwd = ComUtil.toMd5Str(newPassword);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			json.put("code", "101");
			json.put("data", "服务异常");
			return json;
		}
		
		if(orginPassword==null||newPassword==null){
			json.put("code", "15");
			json.put("errmsg", "密码获取失败");
		}else if(!user.getPwd().equals(orginPwd)) {
			json.put("code", "2");
			json.put("errmsg", "密码错误");
		}else{
		
			user.setPwd(newPwd);
			userService.update(user);
			userMap.put("user", user);
			json.put("code", "0");
			json.put("data", "");
		}
		return json;
	}
	
	/**
	 * 获取用户信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "get_user_info")
    public JSONObject getUserInfo(HttpServletRequest request, 
    		HttpServletResponse response){
		String token = request.getHeader("token");
		HashMap<String, Object> userMap =  ComUtil.loginMap.get(token);	
		User user = (User) userMap.get("user");
	    Map<String, Object> map = userService.getUserInfo(user.getUserId());
		JSONObject json = new JSONObject();
		json.put("code", "0");
		json.put("data", map);
		return json;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "save")
    public JSONObject save(String userId, String userName, String phone, String email,
    		String status,String roleId, String no, String name, String gender, String birthday,
    		String schoolInfoId, String grade, String major, String classNo){
		
		JSONObject json = new JSONObject();
		if(userName==null||userName.trim().equals("")){
			json.put("code", "10001");
			json.put("errmsg", "用户名为空");
			return json;
		}
		if(phone==null||phone.trim().equals("")){
			json.put("code", "10002");
			json.put("errmsg", "手机号码为空");
			return json;
		}
		try {
		
	    User user = new User();
	    user.setUserNo(Long.valueOf(ComUtil.genNo()));
	    user.setPhone(phone);
	    user.setEmail(email);
	    user.setUserName(userName);	
	    user.setPwd(ComUtil.toMd5Str("123456"));
		user.setCreateTime(new Date());
		user.setStatus(Integer.valueOf(status));
		TeachStu teachStu = new TeachStu();
		teachStu.setClassNo(classNo);
		teachStu.setGender(Integer.valueOf(gender));
		teachStu.setUser(user);
		if(birthday!=null){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			teachStu.setBirthday(sdf.parse(birthday));  
		}
		teachStu.setNo(no);
		teachStu.setMajor(major);
		teachStu.setClassNo(classNo);
		teachStu.setGrade(grade);
		teachStu.setName(name);
		//是否位教师
	    if("1".equals(roleId)){//教师
	    	teachStu.setIsTecacher(0);
	    }else{//学生
	    	teachStu.setIsTecacher(1);
	    }
		
		SchoolInfo schoolInfo = schoolInfoService.getById(schoolInfoId);
		teachStu.setSchoolInfo(schoolInfo);
		user.setTeachStu(teachStu);
		if(roleId!=null&& !roleId.trim().equals("")){
	      Role role = roleMngService.getById(roleId);
	      Set<UserRoleRel> userRoleRelSet = new HashSet<UserRoleRel>();  
	      UserRoleRel urr = new UserRoleRel();
	      urr.setRole(role);
	      urr.setUser(user);
	      userRoleRelSet.add(urr);
	      user.setUserRoleRelSet(userRoleRelSet);
		}
	    userService.save(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			json.put("code", "101");
			json.put("errmsg", "服务异常");
			return json;
		}
		json.put("code", "0");
		json.put("data", "");
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "edit")
    public JSONObject edit(String userId, String phone, String email,
    		String status, String roleId, String no, String name, String gender, String birthday,
    		String schoolInfoId, String grade, String major, String classNo){
		
		JSONObject json = new JSONObject();
	    User user = userService.getById(userId);
		if(user==null){
			json.put("code", "10012");
			json.put("errmsg", "获取用户信息失败");
			return json;
		}
		
		if(phone==null||phone.trim().equals("")){
			json.put("code", "10002");
			json.put("errmsg", "手机号码为空");
			return json;
		}

		try {
		if(!phone.equals(user.getPhone())){
	       user.setPhone(phone);
		}
		if(!email.equals(user.getEmail())){
	       user.setEmail(email);
		}
		
		if(Integer.valueOf(status)!=user.getStatus()){
			user.setStatus(Integer.valueOf(status));
		}
		
		TeachStu teachStu = user.getTeachStu();
		if(teachStu!=null){
		   if(!classNo.equals(teachStu.getClassNo())){	
		     teachStu.setClassNo(classNo);
		   }
		   if(Integer.valueOf(gender)!=teachStu.getGender()){
		     teachStu.setGender(Integer.valueOf(gender));
		   }
		   if(!major.equals(teachStu.getMajor())){
		     teachStu.setMajor(major);
		   }

		   if(birthday!=null){
			 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			 if(teachStu.getBirthday().compareTo(sdf.parse(birthday))!=0) 
			   teachStu.setBirthday(sdf.parse(birthday));  
		   }else{
			 teachStu.setBirthday(null);    
		   }
		   
		   if(!no.equals(teachStu.getNo())){
			   teachStu.setNo(no);
		   }
		   if(!classNo.equals(teachStu.getClassNo())){
			   teachStu.setClassNo(classNo);
		   }
		   if(!grade.equals(teachStu.getGrade())){
			   teachStu.setGrade(grade);
		   }
		   
		   if(!name.equals(teachStu.getName())){
			   teachStu.setName(name);
		   }
		   
			//是否位教师
		    if("1".equals(roleId)){//教师
		    	teachStu.setIsTecacher(0);
		    }else{//学生
		    	teachStu.setIsTecacher(1);
		    }
		   
		  SchoolInfo schoolInfo = schoolInfoService.getById(schoolInfoId);
		  

		  if(schoolInfo!=teachStu.getSchoolInfo()){
		    teachStu.setSchoolInfo(schoolInfo);
		  }
		    
		}
        
		Set<UserRoleRel> userRoleRelSet = user.getUserRoleRelSet();
	    if(userRoleRelSet!=null){
		  for(UserRoleRel urr :userRoleRelSet){
			// 没有匹配得到得删除
			if(roleId.indexOf(urr.getRole().getRoleId())==-1){
			  userRoleRelService.del(urr);	
			}	
		 }
	    }
	    UserRoleRel urr = new UserRoleRel();
	    Role role = roleMngService.getById(roleId);
	    urr.setRole(role);
	    urr.setUser(user);
	    userRoleRelService.addNew(urr);	    
	    userService.update(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			json.put("code", "101");
			json.put("errmsg", "服务异常");
			return json;
		}
		json.put("code", "0");
		json.put("data", "");
		return json;
	}
	
	
	
	@ResponseBody
	@RequestMapping(value = "get")
    public JSONObject get(String userId){
		
		JSONObject json = new JSONObject();
	    Map<String, Object> map = userService.getUserInfo(userId);
		if(map==null){
			json.put("code", "1010");
			json.put("errmsg", "获取信息失败");
			return json;		
		}
		json.put("code", "0");
		json.put("data", map);
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "logoff")
    public JSONObject logoff(String userId){
		
		JSONObject json = new JSONObject();
	   
	    User user = userService.getById(userId);
		if(user==null){
			json.put("code", "10012");
			json.put("errmsg", "获取用户信息失败");
			return json;
		}
		user.setStatus(2);
		userService.update(user);
		json.put("code", "0");
		json.put("data", "");
		return json;
	}
	
}
