package com.contant.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contant.service.ContentCategoryService;
import com.jiaoyushop.mapper.TbContentCategoryMapper;
import com.jiaoyushop.pojo.TbContentCategory;
import com.jiaoyushop.pojo.TbContentCategoryExample;
import com.jiaoyushop.pojo.TbContentCategoryExample.Criteria;
import com.jiaoyushop.util.E3Result;
import com.jiaoyushop.util.EasyUITreeNode;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService{

	@Autowired
	private TbContentCategoryMapper tbContentCategoryMapper;
	@Override
	public List<EasyUITreeNode> getContentCatList(long parentId) {
		TbContentCategoryExample example=new TbContentCategoryExample();
		Criteria c = example.createCriteria();
		c.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example);
		List<EasyUITreeNode> Nodelist = new ArrayList<>();
		for (TbContentCategory tbContentCategory  : list) {
			EasyUITreeNode node=new EasyUITreeNode();
			node.setId(tbContentCategory.getId());
			node.setText(tbContentCategory.getName());
			node.setState(tbContentCategory.getIsParent()?"closed":"open");
			Nodelist.add(node);
		}
		return Nodelist;
	}
	@Override
	public E3Result addContentCategory(long parentId, String name) {
		TbContentCategory tbContentCategory =new TbContentCategory();
		tbContentCategory.setParentId(parentId);
		tbContentCategory.setName(name);
		tbContentCategory.setCreated(new Date());
		tbContentCategory.setStatus(1);
		tbContentCategory.setSortOrder(1);
		tbContentCategory.setUpdated(new Date());
		tbContentCategory.setIsParent(false);
		tbContentCategoryMapper.insert(tbContentCategory);
		TbContentCategory parent = tbContentCategoryMapper.selectByPrimaryKey(parentId);
		if(!parent.getIsParent()) {
			parent.setIsParent(true);
			//然后将新添加的叶子节点的父节点的属性更新到数据库
			tbContentCategoryMapper.updateByPrimaryKey(parent);
		}
		return E3Result.ok(tbContentCategory);
	}
	

}
