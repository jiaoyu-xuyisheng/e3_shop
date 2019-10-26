package com.jiaoyushop.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jiaoyushop.mapper.TbItemCatMapper;
import com.jiaoyushop.pojo.TbItemCat;
import com.jiaoyushop.pojo.TbItemCatExample;
import com.jiaoyushop.pojo.TbItemCatExample.Criteria;
import com.jiaoyushop.service.ItemCatService;
import com.jiaoyushop.util.EasyUITreeNode;

@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper tbItemCatMapper;
	
	@Override
	public List<EasyUITreeNode> getCatListByParentId(long parentId) {
		TbItemCatExample example=new TbItemCatExample();
		Criteria c = example.createCriteria();
		c.andParentIdEqualTo(parentId);
		List<TbItemCat> list = tbItemCatMapper.selectByExample(example);
		List<EasyUITreeNode> Nodelist = new ArrayList<>();
		for (TbItemCat tbItemCat : list) {
			EasyUITreeNode node=new EasyUITreeNode();
			node.setId(tbItemCat.getId());
			node.setText(tbItemCat.getName());
			node.setState(tbItemCat.getIsParent()?"closed":"open");
			Nodelist.add(node);
		}
		return Nodelist;
	}

}
