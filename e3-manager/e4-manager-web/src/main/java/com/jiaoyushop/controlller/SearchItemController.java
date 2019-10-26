package com.jiaoyushop.controlller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jiaoyushop.util.E3Result;
import com.search.service.SearchItemService;

@Controller
public class SearchItemController {
	
	@Autowired
	private SearchItemService searchItemSearch;
	
	@RequestMapping("/index/item/import")
	@ResponseBody
	public E3Result importItemIndex() {
		return searchItemSearch.importItems();
	}
	
	

}
