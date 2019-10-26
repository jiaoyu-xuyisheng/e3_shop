package com.search.mapper;

import java.util.List;

import com.jiaoyushop.util.SearchItem;

public interface ItemMapper {
	List<SearchItem> getItemByList();
	SearchItem getItemById(long itemid);
}
