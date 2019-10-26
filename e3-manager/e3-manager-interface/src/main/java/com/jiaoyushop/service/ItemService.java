package com.jiaoyushop.service;

import com.jiaoyushop.pojo.TbItem;
import com.jiaoyushop.pojo.TbItemDesc;
import com.jiaoyushop.util.E3Result;
import com.jiaoyushop.util.EasyUIDataGrid;

public interface ItemService {
	TbItem getItemById(Long id);
	EasyUIDataGrid getItemList(Integer page,Integer rows);
	E3Result addItem(TbItem item,String desc);
	TbItemDesc getItemDescById(long itemId);
}
