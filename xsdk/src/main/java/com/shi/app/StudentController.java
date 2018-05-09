package com.shi.app;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shi.common.DictUtil;
import com.shi.entity.Dict;



@Controller
@RequestMapping(value = "app/student/")
public class StudentController {

	
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
	
}
