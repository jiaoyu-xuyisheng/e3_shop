package com.jiaoyushop.controlller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jiaoyushop.service.ItemCatService;
import com.jiaoyushop.util.EasyUITreeNode;

@Controller
public class ItemCartController {
	
	@Autowired
	private ItemCatService itemCatService;
	
	@RequestMapping("/item/cat/list")
	@ResponseBody
	public List<EasyUITreeNode> getItemCatList(@RequestParam(value="id",defaultValue="0")Long parentId){
		List<EasyUITreeNode> list = itemCatService.getCatListByParentId(parentId);
		return list;
	}

}
