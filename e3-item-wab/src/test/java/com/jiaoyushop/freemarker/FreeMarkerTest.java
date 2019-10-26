package com.jiaoyushop.freemarker;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import com.item.pojo.Student;
import freemarker.template.Configuration;
import freemarker.template.Template;


public class FreeMarkerTest {
	
	
	@Test
	public void genFile() throws Exception{
		Configuration configuration=new Configuration(Configuration.getVersion());
		configuration.setDirectoryForTemplateLoading(new File("D:\\WebProject3\\e3-item-wab\\src\\main\\webapp\\WEB-INF\\ftl"));
		configuration.setDefaultEncoding("utf-8");
		Template template = configuration.getTemplate("student2.ftl");
		Map<String,Object> dataModel=new HashMap<String,Object>();
		dataModel.put("hello","this is my fist ftl file");
		Student student=new Student("xiaoming",1,20,"jianxi");
		dataModel.put("student", student);
		Writer out=new FileWriter(new File("D:\\student.html"));
		template.process(dataModel, out);
		out.close();	
	}
}
