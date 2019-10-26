package com.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jiaoyushop.util.SearchResult;
import com.search.service.SearchItemService;

@Controller
public class SearchController {
	
	@Autowired
	private SearchItemService searchItemService;
	
	@Value("${PAGE_ROWS}")
	private Integer PAGE_ROWS;
	
	@RequestMapping("/search")
	public String search(String keyword,@RequestParam(defaultValue="1") Integer page, Model model) throws Exception {
		//需要转码
		keyword = new String(keyword.getBytes("iso8859-1"), "utf-8");
		//调用Service查询商品信息 
		SearchResult result = searchItemService.search(keyword, page, PAGE_ROWS);
		//把结果传递给jsp页面
		model.addAttribute("query", keyword);
		model.addAttribute("totalPages", result.getTotalPages());
		model.addAttribute("recourdCount", result.getRecourdCount());
		model.addAttribute("page", page);
		model.addAttribute("itemList", result.getItemList());
		//返回逻辑视图
		return "search";
	}

}
