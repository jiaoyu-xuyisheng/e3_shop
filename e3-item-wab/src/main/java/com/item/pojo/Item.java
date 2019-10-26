package com.item.pojo;

import java.util.Date;

import com.jiaoyushop.pojo.TbItem;

public class Item extends TbItem{
	
	
	
	public Item(TbItem tbItem) {
		this.setId(tbItem.getId());
		this.setNum(tbItem.getNum());
	    this.setTitle(tbItem.getTitle());
	    this.setSellPoint(tbItem.getSellPoint());
	    this.setPrice(tbItem.getPrice());
	    this.setBarcode(tbItem.getBarcode());
	    this.setImage(tbItem.getImage());
	    this.setCid(tbItem.getCid());
	    this.setStatus(tbItem.getStatus());
	    this.setCreated(tbItem.getCreated());
	    this.setUpdated(tbItem.getUpdated());

	}
	

	public String[] getImages() {
		String image2 = this.getImage();
		if(image2!=null && !"".equals(image2)) {
			String[] split = image2.split(",");
			return split;
		}
		return null;
	}
}
