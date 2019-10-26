package com.contant.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.contant.service.ContentService;
import com.jiaoyushop.mapper.TbContentMapper;
import com.jiaoyushop.pojo.TbContent;
import com.jiaoyushop.pojo.TbContentExample;
import com.jiaoyushop.pojo.TbContentExample.Criteria;
import com.jiaoyushop.util.E3Result;
import com.jiaoyushop.util.JedisClient;
import com.jiaoyushop.util.JsonUtils;

@Service
public class ContentServiceImpl implements ContentService {
	
	@Autowired
	private TbContentMapper tbContentMapper;
	
	@Value("${CONTENT_LIST}")
	private String CONTENT_LIST;
	
	
	@Autowired
	private JedisClient jedisClient;

	
	public E3Result addContent(TbContent content) {
		content.setCreated(new Date());
		content.setUpdated(new Date());
		tbContentMapper.insert(content);
		jedisClient.hdel(CONTENT_LIST, content.getCategoryId().toString());
		return E3Result.ok();
	}


	@Override
	public List<TbContent> getContentListByCid(long cid) {
		//查询缓存
		try {
			String liststring = jedisClient.hget(CONTENT_LIST, cid+"");
			if(StringUtils.isNotBlank(liststring)) {
				List<TbContent> list = JsonUtils.jsonToList(liststring,TbContent.class);
				return list;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		//如果缓存中有直接响应结果
		//如果没有查询数据库
		
		TbContentExample example=new TbContentExample();
		Criteria c = example.createCriteria();
		c.andCategoryIdEqualTo(cid);
		List<TbContent> list = tbContentMapper.selectByExampleWithBLOBs(example);
		//将数据添加到缓存
		try {
			jedisClient.hset(CONTENT_LIST, cid+"",JsonUtils.objectToJson(list));
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}

}
