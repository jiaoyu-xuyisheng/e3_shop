package com.jiaoyushop.service;

import java.util.List;

import com.jiaoyushop.util.EasyUITreeNode;

public interface ItemCatService {
	//通过父节点得到叶了节点的集合
	List<EasyUITreeNode> getCatListByParentId(long parentId);
}
