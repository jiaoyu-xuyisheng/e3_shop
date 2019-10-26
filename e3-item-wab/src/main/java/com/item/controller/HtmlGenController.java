package com.item.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.item.pojo.Student;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Controller
public class HtmlGenController {

	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	@RequestMapping("/item/genhtml")
	@ResponseBody
	public String genHtml() throws Exception{
		Configuration configuration=freeMarkerConfigurer.getConfiguration();
		Template template = configuration.getTemplate("student.ftl");
		Map<String,Object> dataModel=new HashMap<String,Object>();
		List<Student> studentList=new LinkedList<Student>();
		studentList.add(new Student("xiaowang",1,20,"jianxi"));
		studentList.add(new Student("xidada",1,70,"beijing"));
		studentList.add(new Student("panjilian",1,25,"xian"));
		studentList.add(new Student("wuda",1,40,"wuhan"));
		dataModel.put("StudentList", studentList);
		Writer writer=new FileWriter(new File("D:/second_ftl.html"));
		template.process(dataModel, writer);
		writer.close();
		return "OK";
	}
	
	
}
