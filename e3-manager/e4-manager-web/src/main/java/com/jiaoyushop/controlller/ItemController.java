package com.jiaoyushop.controlller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jiaoyushop.pojo.TbItem;
import com.jiaoyushop.service.ItemService;
import com.jiaoyushop.util.E3Result;
import com.jiaoyushop.util.EasyUIDataGrid;

@Controller
public class ItemController{
	
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/item/{itemId}")
	@ResponseBody
	public TbItem getItemById(@PathVariable Long itemId) {
		TbItem tbItem = itemService.getItemById(itemId);
		return tbItem;
	}
	
	
	@RequestMapping("/my/hello")
	public void print() {
		System.out.println("hello oo");
	}
	
	
	
	@RequestMapping("item/list")
	@ResponseBody
	public EasyUIDataGrid getItemList(Integer page,Integer rows) {
		EasyUIDataGrid result = itemService.getItemList(page, rows);
		return result;
	}
	
	@RequestMapping(value="item/save",method=RequestMethod.POST)
	@ResponseBody
	public E3Result addItem(TbItem item,String desc) {
		
		return itemService.addItem(item, desc);
	}
	
	

}
