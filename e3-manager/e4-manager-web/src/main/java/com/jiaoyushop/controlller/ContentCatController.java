package com.jiaoyushop.controlller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.contant.service.ContentCategoryService;
import com.jiaoyushop.util.E3Result;
import com.jiaoyushop.util.EasyUITreeNode;

@Controller
public class ContentCatController {
	
	@Autowired
	private ContentCategoryService contentCategoryService;

	@RequestMapping("/content/category/list")
	@ResponseBody
	public List<EasyUITreeNode> getContentCatList(@RequestParam(name="id",defaultValue="0")long parentId){
		return contentCategoryService.getContentCatList(parentId);
	}
	
	@RequestMapping(value="/content/category/create",method=RequestMethod.POST)
	@ResponseBody
	public E3Result addContentCategory(long parentId,String name) {
		return contentCategoryService.addContentCategory(parentId, name);
	}
}
