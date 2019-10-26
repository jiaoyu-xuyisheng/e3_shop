package com.search.service;

import com.jiaoyushop.util.E3Result;
import com.jiaoyushop.util.SearchResult;

public interface SearchItemService {
	
	public E3Result importItems();
	public SearchResult search(String keyWord,int page,int rows) throws Exception;
	
}
