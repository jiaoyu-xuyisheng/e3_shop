package com.cart.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cart.service.CartService;
import com.jiaoyushop.mapper.TbItemMapper;
import com.jiaoyushop.pojo.TbItem;
import com.jiaoyushop.util.E3Result;
import com.jiaoyushop.util.JedisClient;
import com.jiaoyushop.util.JsonUtils;

@Service
public class CartServiceImpl implements CartService {
	
	@Autowired
	private JedisClient jedisClient;
	@Value("${REDIS_CART_PRE")
	private String REDIS_CART_PRE;
	@Autowired
	private TbItemMapper tbItemMapper;

	
	

	@Override
	public E3Result addCart(long userId, long itemId,int num) {
		Boolean hexists = jedisClient.hexists(REDIS_CART_PRE+":"+userId,itemId+"");
		if(hexists) {
			String json = jedisClient.hget(REDIS_CART_PRE+":"+userId,itemId+"");
			TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
			tbItem.setNum(tbItem.getNum()+num);
			jedisClient.hset(REDIS_CART_PRE+":"+userId,itemId+"", JsonUtils.objectToJson(tbItem));
		}
		TbItem tbItem = tbItemMapper.selectByPrimaryKey(itemId);
		tbItem.setNum(num);
		String image = tbItem.getImage();
		if(StringUtils.isNotBlank(image)) {
			String imageOne = image.split(",")[0];
			tbItem.setImage(imageOne);
		}
		jedisClient.hset(REDIS_CART_PRE+":"+userId,itemId+"", JsonUtils.objectToJson(tbItem));
		return E3Result.ok();
	}


	@Override
	public E3Result mergeCart(long userId, List<TbItem> itemList) {
		
		for (TbItem tbItem : itemList) {
			addCart(userId,tbItem.getId(),tbItem.getNum());
		}
		return E3Result.ok();
	}


	@Override
	public List<TbItem> getCartList(long userId) {
		List<String> list = jedisClient.hvals(REDIS_CART_PRE+":"+userId);
		List<TbItem> itemList=new ArrayList<>();
		for (String string : list) {
			TbItem tbItem = JsonUtils.jsonToPojo(string, TbItem.class);
			itemList.add(tbItem);
		}
		return itemList;
	}


	@Override
	public E3Result updateCartNum(long userId, long itemId, int num) {
		TbItem tbItem = JsonUtils.jsonToPojo(jedisClient.hget(REDIS_CART_PRE+":"+userId,itemId+""), TbItem.class);
		tbItem.setNum(num);
		jedisClient.hset(REDIS_CART_PRE+":"+userId,itemId+"", JsonUtils.objectToJson(tbItem));	
		return E3Result.ok();
	}


	@Override
	public E3Result deleteCartItem(long userId, long itemId) {
		jedisClient.hdel(REDIS_CART_PRE+":"+userId,itemId+"");
		return E3Result.ok();
	}


	@Override
	public E3Result cleanCartItem(long userId) {
		jedisClient.del(REDIS_CART_PRE+":"+userId);	
		return E3Result.ok();
	}

}
