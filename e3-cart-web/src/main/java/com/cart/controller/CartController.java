package com.cart.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cart.service.CartService;
import com.jiaoyushop.pojo.TbItem;
import com.jiaoyushop.pojo.TbUser;
import com.jiaoyushop.service.ItemService;
import com.jiaoyushop.util.CookieUtils;
import com.jiaoyushop.util.E3Result;
import com.jiaoyushop.util.JsonUtils;

@Controller
public class CartController {
	
	@Value("${CART}")
	private String CART;
	@Value("${CART_EXPIRE}")
	private Integer CART_EXPIRE;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private CartService cartService;
	
	
	@RequestMapping("/cart/add/{itemId}")
	public String addCart(@PathVariable Long itemId,@RequestParam(defaultValue="1")Integer number,
	HttpServletRequest request,HttpServletResponse response)
	{	
		TbUser user = (TbUser) request.getAttribute("user");
		if(user!=null) {
			cartService.addCart(user.getId(), itemId, number);
			return "cartSuccess";
		}
		List<TbItem> list = getCartList(request);
		boolean hasItem=false;
		for (TbItem tbItem : list) {
			if(tbItem.getId()==itemId.longValue()) {
				tbItem.setNum(tbItem.getNum()+number);
				hasItem=true;
				break;
			}
		}
		if(!hasItem) {
			TbItem tbItem=itemService.getItemById(itemId);
			String image = tbItem.getImage();
			if(StringUtils.isNotBlank(image)) {
				String[] images = image.split(",");
				tbItem.setImage(images[0]);
			}
			tbItem.setNum(number);
			list.add(tbItem);
		}
		CookieUtils.setCookie(request, response, CART, JsonUtils.objectToJson(list),CART_EXPIRE,true);
		//从cookie中取
		return "cartSuccess";
	}
	
	private List<TbItem> getCartList(HttpServletRequest request){
		String json = CookieUtils.getCookieValue(request,CART,true);
		if(StringUtils.isNotBlank(json)) {
			List<TbItem> list = JsonUtils.jsonToList(json, TbItem.class);
			return list;
		}
		return new ArrayList<>();
	}
	
	@RequestMapping("/cart/cart")
	public String showCartList(HttpServletRequest request,Model model,HttpServletResponse response) {
		TbUser user = (TbUser) request.getAttribute("user");
		List<TbItem> cartList = getCartList(request);
		if(user!=null) {
			cartService.mergeCart(user.getId(), cartList);
			CookieUtils.deleteCookie(request, response, CART);
			 cartList = cartService.getCartList(user.getId());
		}	
		model.addAttribute("cartList", cartList);
		return "cart";
	}
	
	@RequestMapping("/cart/update/num/{itemId}/{num}")
	@ResponseBody
	public E3Result updateNum(@PathVariable Long itemId,
			@PathVariable Integer num,HttpServletRequest request,
			HttpServletResponse response) {
		TbUser user = (TbUser) request.getAttribute("user");
		if(user!=null) {
			cartService.updateCartNum(user.getId(), itemId, num);
			return E3Result.ok();
		}	
	
		List<TbItem> cartList = getCartList(request);
		for (TbItem tbItem : cartList) {
			if(tbItem.getId()==itemId.longValue()) {
				tbItem.setNum(num);
			}
		}
		CookieUtils.setCookie(request, response, CART,JsonUtils.objectToJson(cartList),CART_EXPIRE,true);
		return E3Result.ok();
	}
	
	@RequestMapping("/cart/delete/{itemId}")
	public String deleteCartItem(@PathVariable Long itemId,HttpServletRequest request,
			HttpServletResponse response) {
		TbUser user = (TbUser) request.getAttribute("user");
		if(user!=null) {
			cartService.deleteCartItem(user.getId(), itemId);
			return "redirect:/cart/cart.html";
		}
		List<TbItem> cartList = getCartList(request);
		for (TbItem tbItem : cartList) {
			if(tbItem.getId()==itemId.longValue()) {
				cartList.remove(tbItem);
				break;
			}
		}
		CookieUtils.setCookie(request, response, CART,JsonUtils.objectToJson(cartList),CART_EXPIRE,true);
		return "redirect:/cart/cart.html";
	}
	
}
