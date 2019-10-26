package com.order.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cart.service.CartService;
import com.jiaoyushop.pojo.TbItem;
import com.jiaoyushop.pojo.TbUser;
import com.jiaoyushop.util.E3Result;
import com.order.pojo.OrderInfo;
import com.order.service.OrderService;

@Controller
public class OrderController {
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private OrderService orderService;
	
	
	@RequestMapping("/order/order-cart")
	public String showOrderCart(HttpServletRequest request,Model model) {
		TbUser user=(TbUser) request.getAttribute("user");
		List<TbItem> cartList = cartService.getCartList(user.getId());
		model.addAttribute("cartList", cartList);
		return "order-cart";
	}
	
	
	@RequestMapping(value="/order/create",method=RequestMethod.POST)
	public String createOrder(OrderInfo orderInfo,HttpServletRequest request) {
		TbUser user=(TbUser) request.getAttribute("user");
		orderInfo.setUserId(user.getId());
		orderInfo.setBuyerNick(user.getUsername());
		E3Result result = orderService.createOrder(orderInfo);
		if(result.getStatus()==200) {
			//清空购物车
			cartService.cleanCartItem(user.getId());
		}
		String orderId = result.getData().toString();
		//返回订单号
		request.setAttribute("orderId", orderId);
		request.setAttribute("payment", orderInfo.getPayment());
		DateTime dateTime=new DateTime();
		dateTime=dateTime.plusDays(3);
		request.setAttribute("date", dateTime.toString("yyyy-MM-dd"));
		return "success";
		
		
	}
	
	
	
	

}
