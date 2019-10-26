package com.jiaoyushop.controlller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.contant.service.ContentService;
import com.jiaoyushop.pojo.TbContent;
import com.jiaoyushop.util.E3Result;

@Controller
public class ContentController {
	@Autowired
	private ContentService contentService;
  
	@RequestMapping(value="/content/save",method=RequestMethod.POST)
	@ResponseBody
	public E3Result addContent(TbContent content) {
		
		return contentService.addContent(content);
	}
}
