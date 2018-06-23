package com.shi.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Date;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.shi.entity.Course;
import com.shi.entity.Dict;
import com.shi.entity.User;
import com.shi.entity.UserCourseRel;
import com.shi.service.CallTherollService;
import com.shi.service.CourseMngservice;
import com.shi.service.CourseService;
import com.shi.service.CourseTimeService;
import com.shi.service.UserCourseRelService;
import com.shi.service.UserMngService;


@Controller
@RequestMapping(value = "app/student/")
public class StudentApp {

	private static Logger logger = LogManager
			.getLogger(StudentApp.class);
	
	@Autowired
	private CourseMngservice courseMngservice;
	@Autowired
	private UserMngService userService;
	@Autowired
	private CourseService courseService;
	@Autowired
	private CallTherollService callTherollService;
	@Autowired
	private CourseTimeService courseTimeService;
	@Autowired
	private UserCourseRelService userCourseRelService;
	
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
				return json;
			}		
			if (user.getStatus() == 1) {
				json.put("code", "2");
				json.put("errmsg", "账号已被锁定，请联系管理员");
				return json;
			}

			String pwd = ComUtil.toMd5Str(password);

			if (!user.getPwd().equals(pwd)) {
				json.put("code", "3");
				json.put("errmsg", "密码错误");
				return json;
			} 
			if(user.getTeachStu().getIsTeacher()!=1){	
				json.put("code", "4");
				json.put("errmsg", "请输入学生账号");
				return json;
			}
			
				String token = ComUtil.GetGUID();
				HashMap<String, Object> userMap = new HashMap<String, Object>();
				userMap.put("user", user);
				ComUtil.loginMap.put(token, userMap);
				
				JSONObject uJson = new JSONObject();
				uJson.put("userId",user.getUserId());
				uJson.put("userName",user.getUserName());
				uJson.put("token",token);
				json.put("code", "0");
				json.put("data", uJson);
				logger.info("手机用户："+user.getUserName() +"-登陆成功!");
				return json;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("登陆异常" ,e);
			json.put("code", "101");
			json.put("errmsg", "服务异常");
			return json;
		}
		
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "logout")
	public JSONObject logout(HttpServletRequest request) {

		JSONObject json = new JSONObject();
		String token = request.getHeader("token");
		HashMap<String, Object> userMap =  ComUtil.loginMap.get(token);	
		User user = (User) userMap.get("user");
		ComUtil.loginMap.remove(token);
		json.put("code", "0");
		json.put("data", "");
		logger.info("手机用户："+user.getUserName() +"-退出登陆!");
		return json;
	}
	
	
	
	@ResponseBody
	@RequestMapping(value = "getc")
	public JSONObject getDictCateByName(String cateName) {

		List<Dict> dictList = DictUtil.dictCategoryList.get(cateName);
		String jsonText = JSON.toJSONString(dictList, false);
		JSONArray jsonArray= JSONArray.parseArray(jsonText);
		JSONObject json = new JSONObject();
		json.put("code", "0");
		json.put("data", jsonArray);
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "getPersonCourse")
	public JSONObject getPersonCourse(String param, String no, String size) {
		
		if(no==null||no.equals("")){
			no = "1";
		}
		if(size==null||size.equals("")){
			size="10";
		}
	    int pageNo = Integer.valueOf(no);
		int pageSize = Integer.valueOf(size);
		Page<Course> page = courseMngservice.getCoursePage(param, pageNo, pageSize);
		JSONObject json = new JSONObject();
		json.put("code", "0");
		json.put("data", page);
		return json;
	}
	
	/**
	 * 通过用户ID获取所有课程
	 * @param request
	 * @param response
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAllCourseList")
	public JSONObject getAllCourseList(String userId, String no, String size) {

		List<Map<String, Object>> course = courseMngservice.getAllCourseList(userId);
		JSONObject json = new JSONObject();
		json.put("code", "0");
		json.put("data", course);
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "getCourseList")
	public JSONObject getCourseList(String param, String no, String size) {

		List<Map<String, Object>> course = courseMngservice.getCourseList(param);
		JSONObject json = new JSONObject();
		json.put("code", "0");
		json.put("data", course);
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "signIn")
	public JSONObject signIn(String cid,String uid,String callPosition,String callState,String courseTimeId) {
		Map<String, Object> res = courseMngservice.signIn(cid,uid,callPosition, callState, courseTimeId);
		
        System.out.print("qiandaoqiandao");
		JSONObject json = new JSONObject();
		
		json.put("code", "0");
		json.put("data", res);
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "getScoreList")
	public JSONObject getScoreList(String userId) {
		List<Map<String, Object>> res = courseMngservice.getScoreList(userId);
		
        System.out.print("qiandaoqiandao");
		JSONObject json = new JSONObject();
		
		json.put("code", "0");
		json.put("data", res);
		return json;
	}
	
	@ResponseBody
	@RequestMapping(value = "getRecordList")
	public JSONObject getRecordList(String userId) {
		List<Map<String, Object>> res = courseMngservice.getRecordList(userId,"0");
		List<Map<String, Object>> res1 = courseMngservice.getRecordList(userId,"2");
		for( int i = 0 ; i < res.size() ; i++) {//内部不锁定，效率最高，但在多线程要考虑并发操作的问题。
		    System.out.println(res.get(i).get("course_id"));
		    String cid1="";
		    String countQj="";
		    String cid = res.get(i).get("course_id").toString();
	               for(int j=0;j<res1.size();j++){
	            	   cid1 = res1.get(j).get("course_id").toString();
	            	   countQj=res1.get(j).get("count").toString();
	            	   if(cid.equals(cid1)){
	       	        	res.get(i).put("qj", countQj);
	       	        }
	               }
	       
	         
		}
		
        System.out.print("qiandaoqiandao");
		JSONObject json = new JSONObject();
//		JSONObject dataJson = new JSONObject();
//		dataJson.put("qj", res1);
//		dataJson.put("kk", res);
		json.put("code", "0");
		json.put("data", res);
		return json;
	}
}
