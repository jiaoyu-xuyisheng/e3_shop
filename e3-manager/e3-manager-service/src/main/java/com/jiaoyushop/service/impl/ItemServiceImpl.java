package com.jiaoyushop.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import javax.jms.Destination;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiaoyushop.mapper.TbItemCatMapper;
import com.jiaoyushop.mapper.TbItemDescMapper;
import com.jiaoyushop.mapper.TbItemMapper;
import com.jiaoyushop.pojo.TbItem;
import com.jiaoyushop.pojo.TbItemDesc;
import com.jiaoyushop.pojo.TbItemExample;
import com.jiaoyushop.pojo.TbItemExample.Criteria;
import com.jiaoyushop.service.ItemService;
import com.jiaoyushop.util.E3Result;
import com.jiaoyushop.util.EasyUIDataGrid;
import com.jiaoyushop.util.IDUtils;
import com.jiaoyushop.util.JedisClient;
import com.jiaoyushop.util.JsonUtils;

@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private TbItemMapper tbItemMapper;
	
	@Autowired
	private TbItemDescMapper tbItemDescMapper;
	
	@Autowired
	private JmsTemplate jmsTemplate;
	@Resource
	private Destination topicDestination;
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${ITEM_INFO_PRE}")
	private String ITEM_INFO_PRE;
	@Value("${ITEM_INFO_EXPIRE}")
	private Integer ITEM_INFO_EXPIRE;
	
	@Override
	public TbItem getItemById(Long id) {
		
		try {
			String json=jedisClient.get(ITEM_INFO_PRE+":"+id+":BASE");
			if(StringUtils.isNotBlank(json)) {
				TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
				return item;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		TbItemExample example=new TbItemExample();
		Criteria c = example.createCriteria();
		c.andIdEqualTo(id);
		List<TbItem> list = tbItemMapper.selectByExample(example);
		if(list!=null) {
			TbItem tbItem = list.get(0);
			try {
				jedisClient.set(ITEM_INFO_PRE+":"+id+":BASE",JsonUtils.objectToJson(tbItem));
				jedisClient.expire(ITEM_INFO_PRE+":"+id+":BASE",ITEM_INFO_EXPIRE);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return tbItem;
		}
		return null;
	}

	@Override
	public EasyUIDataGrid getItemList(Integer page, Integer rows) {
		//设置分页信息
		PageHelper.startPage(page, rows);
		TbItemExample example=new TbItemExample();
		List<TbItem> list = tbItemMapper.selectByExample(example);
		//创建一个返回值对象
		EasyUIDataGrid result=new EasyUIDataGrid();
		result.setRows(list);
		PageInfo<TbItem> pageInfo=new PageInfo<TbItem>(list);
		Integer total = (int) pageInfo.getTotal();
		result.setTotal(total);	
		return result;
	}

	@Override
	public E3Result addItem(TbItem item, String desc) {
		//生成商品id
		final long id = IDUtils.genItemId();
		item.setId(id);
		//补完ibitem数据
		//1：正常，2，下架，3，删除 
		item.setStatus((byte) 1);
		item.setCreated(new Date());
		item.setUpdated(new Date());
		//向商品表插入数据;
		tbItemMapper.insert(item);
		//创建描述pojo
		TbItemDesc itemDesc=new TbItemDesc();
		//补全数据！！
		itemDesc.setItemId(id);
		itemDesc.setCreated(new Date());
		itemDesc.setItemDesc(desc);
		itemDesc.setUpdated(new Date());
		//向描述表中插入数据！！
		tbItemDescMapper.insert(itemDesc);
		//发
		
		jmsTemplate.send(topicDestination,new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
			
				return session.createTextMessage(id+"");
			}
		});
		return E3Result.ok();
	}

	@Override
	public TbItemDesc getItemDescById(long itemId) {
		try {
			String json=jedisClient.get(ITEM_INFO_PRE + ":" + itemId + ":DESC");
			if(StringUtils.isNotBlank(json)) {
				TbItemDesc tbItemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
				return tbItemDesc;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		TbItemDesc itemDesc = tbItemDescMapper.selectByPrimaryKey(itemId);	
		try {
			jedisClient.set(ITEM_INFO_PRE + ":" + itemId + ":DESC", JsonUtils.objectToJson(itemDesc));
			jedisClient.expire(ITEM_INFO_PRE + ":" + itemId + ":DESC", ITEM_INFO_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return itemDesc;
	}

}
