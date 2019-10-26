package com.order.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import com.jiaoyushop.mapper.TbOrderItemMapper;
import com.jiaoyushop.mapper.TbOrderMapper;
import com.jiaoyushop.mapper.TbOrderShippingMapper;
import com.jiaoyushop.pojo.TbOrderItem;
import com.jiaoyushop.pojo.TbOrderShipping;
import com.jiaoyushop.util.E3Result;
import com.jiaoyushop.util.JedisClient;
import com.order.pojo.OrderInfo;
import com.order.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private TbOrderMapper orderMapper;
	@Autowired
	private TbOrderItemMapper orderItemMapper;
	@Autowired
	private TbOrderShippingMapper orderShippingMapper;
	@Autowired
	private JedisClient jedisClient;
	
	//订单号生成的key
	@Value("${ORDER_GEN_KEY")
	private String ORDER_GEN_KEY;
	//订单的初使值
	@Value("${ORDER_ID_START}")
	private String ORDER_ID_START;
	
	@Value("${ORDER_ITEM_ID_GEN_KEY}")
	private String ORDER_ITEM_ID_GEN_KEY;
	
	
	//向三个和订单表相关的表插入数据
	@Override
	public E3Result createOrder(OrderInfo orderInfo) {
		//生成主键 先看有没有主键如果没有就设置一个如果有就自增
		if(!jedisClient.exists(ORDER_GEN_KEY)) {
			jedisClient.set(ORDER_GEN_KEY, ORDER_ID_START);
		}
		String orderId = jedisClient.incr(ORDER_GEN_KEY).toString();
		orderInfo.setOrderId(orderId);
		orderInfo.setPostFee("0");
		//1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭
		orderInfo.setStatus(1);
		Date date = new Date();
		orderInfo.setCreateTime(date);
		orderInfo.setUpdateTime(date);
		// 3、向订单表插入数据。
		orderMapper.insert(orderInfo);
		List<TbOrderItem> orderItems = orderInfo.getOrderItems();
		for (TbOrderItem tbOrderItem : orderItems) {
			String orderItemId = jedisClient.incr(ORDER_ITEM_ID_GEN_KEY).toString();
			tbOrderItem.setId(orderItemId);
			tbOrderItem.setOrderId(orderId);
			orderItemMapper.insert(tbOrderItem);		
		}
		TbOrderShipping orderShipping = orderInfo.getOrderShipping();
		orderShipping.setOrderId(orderId);
		orderShipping.setCreated(date);
		orderShipping.setUpdated(date);
		orderShippingMapper.insert(orderShipping);
		return E3Result.ok(orderId);
	}

}
