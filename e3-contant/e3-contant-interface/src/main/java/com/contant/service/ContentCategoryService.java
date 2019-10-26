package com.contant.service;

import java.util.List;

import com.jiaoyushop.util.E3Result;
import com.jiaoyushop.util.EasyUITreeNode;

public interface ContentCategoryService {
	List<EasyUITreeNode> getContentCatList(long parentId);
	E3Result addContentCategory(long parentId,String name);
}
