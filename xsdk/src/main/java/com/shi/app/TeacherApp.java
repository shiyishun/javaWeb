package com.shi.app;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shi.entity.CallTheroll;
import com.shi.entity.Course;
import com.shi.entity.TeachStu;
import com.shi.entity.User;
import com.shi.entity.UserCourseRel;
import com.shi.service.CallTherollService;
import com.shi.service.CourseService;
import com.shi.service.UserMngService;


@Controller
@RequestMapping(value = "app/teacher/")
public class TeacherApp {
   
	@Autowired
	private UserMngService userService;
	@Autowired
	private CourseService courseService;
	@Autowired
	private CallTherollService callTherollService;
	
	
	/**
	 * 通过用户ID获取所有课程
	 * @param request
	 * @param response
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "get_course")
    public JSONObject getCourse(HttpServletRequest request, 
    		HttpServletResponse response, String userId){
	
		JSONObject json = new JSONObject();
		User user = userService.getById(userId);
		Set<UserCourseRel> ucrSet = user.getUserCourseRelSet();
		Iterator<UserCourseRel> it = ucrSet.iterator(); 
		List<Course> courseList = new ArrayList<Course>();
		while (it.hasNext()) {  
			UserCourseRel ucr = it.next();  
			courseService.addClassInfo(ucr.getCourse());
		    courseList.add(ucr.getCourse()); 
		} 
		String jsonText = JSON.toJSONString(courseList, false);
		JSONArray jsonArray= JSONArray.parseArray(jsonText);
		json.put("code", "0");
		json.put("data", jsonArray);
		return json;
	}
	
	
	/**
	 * 根据课程ID生成签到表
	 * @param request
	 * @param response
	 * @param courseId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "create_signin")
    public JSONObject createSignin(HttpServletRequest request, 
    		HttpServletResponse response, String courseId){
	
		JSONObject json = new JSONObject();
		Course course = courseService.getById(courseId);
		Set<UserCourseRel> ucrSet = course.getUserCourseRelSet();
		Iterator<UserCourseRel> it = ucrSet.iterator(); 
		
		while (it.hasNext()) {  
			UserCourseRel ucr = it.next();  
		    TeachStu ts = ucr.getUser().getTeachStu();
		    if(1==ts.getIsTecacher()){
		    	CallTheroll callTheroll = new CallTheroll();
		    	callTheroll.setCallDate(new Date());
		    	callTheroll.setCallPosition("0*0");
		    	callTheroll.setCallState(0);
		    	callTheroll.setCourse(course);
		    	callTheroll.setStuName(ts.getName());
		    	callTheroll.setCourseName(course.getCourseName());
		    	callTheroll.setStuNo(ts.getNo());
		    	callTheroll.setUser(ucr.getUser());
		    	callTherollService.save(callTheroll);
		    }
		   
		}

		json.put("code", "0");
		json.put("data", "");
		return json;
	}
	
	
	
	/**
	 * 通过课程ID与时间获取签到表
	 * @param request
	 * @param response
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "find_call_theroll")
    public JSONObject findCourse(HttpServletRequest request, 
    		HttpServletResponse response, String courseId, String date){
		
		
		JSONObject json = new JSONObject();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startCallDate = null;
		Date endCallDate = null;
		try {
			endCallDate = sdf.parse(date+" 23:59:59");
			startCallDate = sdf.parse(date+" 00:00:00");
		} catch (ParseException e) {
			json.put("code", "123");
			json.put("errmsg", "时间格式错误");
			e.printStackTrace();
			return json;
			
		}
	
        List<CallTheroll> callTheroll = callTherollService.findByCouserIdAndDate(courseId, startCallDate, endCallDate);
		String jsonText = JSON.toJSONString(callTheroll, false);
		JSONArray jsonArray= JSONArray.parseArray(jsonText);
		json.put("code", "0");
		json.put("data", jsonArray);
		return json;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(); 
		
		  Map<String, Object> map1 = new HashMap<String, Object>();
		  map1.put("week", "星期一");
		  map1.put("period", "5-6节");
		  map1.put("periodVal", "56");
		  list.add(map1);
		  Map<String, Object> map2 = new HashMap<String, Object>();
		  map2.put("week", "星期一");
		  map2.put("period", "1-2节");
		  map2.put("periodVal", "12");
		  list.add(map2);
		  Map<String, Object> map3 = new HashMap<String, Object>();
		  map3.put("week", "星期一");
		  map3.put("period", "3-4节");
		  map3.put("periodVal", "34");
		  list.add(map3);
		  Map<String, Object> map4 = new HashMap<String, Object>();
		  map4.put("week", "星期二");
		  map4.put("period", "1-6节");
		  map4.put("periodVal", "16");
		  list.add(map4);
		  Map<String, Object> map5 = new HashMap<String, Object>();
		  map5.put("week", "星期二");
		  map5.put("period", "7-8节");
		  map5.put("periodVal", "78");
		  list.add(map5);
		  Map<String, Object> map6 = new HashMap<String, Object>();
		  map6.put("week", "星期一");
		  map6.put("period", "11-12节");
		  map6.put("periodVal", "1112");
		  list.add(map6);
		  sort(list);
		  for(Map m: list){
			  System.out.println(m.toString());
		  }
	}
	
	public static void sort(List<Map<String, Object>> list) {

		if (null != list && list.size() > 0) {
			Collections.sort(list, new Comparator<Map>() {
				@Override
				public int compare(Map o1, Map o2) {
					int ret = o1.get("week").toString().compareTo(o2.get("week").toString());
					if (ret==0) {
                        int one = Integer.valueOf(o1.get("periodVal").toString());  
                        int two = Integer.valueOf(o2.get("periodVal").toString());  			
						return (int)(one-two);
					}else{
						return ret;
					}
				
				}
			});
		}
	}
	
}
