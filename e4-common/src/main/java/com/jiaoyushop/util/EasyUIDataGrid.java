package com.jiaoyushop.util;

import java.io.Serializable;
import java.util.List;

public class EasyUIDataGrid implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7490405484820449251L;
	private Integer total;
	private List rows;
	
	
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public List getRows() {
		return rows;
	}
	public void setRows(List rows) {
		this.rows = rows;
	}
	
}
