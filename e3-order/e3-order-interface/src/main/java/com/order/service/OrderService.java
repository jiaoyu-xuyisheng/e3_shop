package com.order.service;

import com.jiaoyushop.util.E3Result;
import com.order.pojo.OrderInfo;

public interface OrderService {
	
	public E3Result createOrder(OrderInfo orderInfo);
}
