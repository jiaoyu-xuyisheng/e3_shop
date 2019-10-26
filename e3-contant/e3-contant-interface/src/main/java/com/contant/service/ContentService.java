package com.contant.service;

import java.util.List;

import com.jiaoyushop.pojo.TbContent;
import com.jiaoyushop.util.E3Result;

public interface ContentService {
	E3Result addContent(TbContent content);
	List<TbContent> getContentListByCid(long cid);
}
