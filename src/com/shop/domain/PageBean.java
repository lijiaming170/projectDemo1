package com.shop.domain;

import java.util.List;

public class PageBean<T> {
	//当前是第几页
	private int currentpage;
	//当前显示条数
	private int curentcount;
	//总的页数
	private int totalpage;
	//总的条数
	private int totalcount;
	//当前页数的productlist
	private List<T>productlistbycid;
	public int getCurrentpage() {
		return currentpage;
	}
	public void setCurrentpage(int currentpage) {
		this.currentpage = currentpage;
	}
	public int getCurentcount() {
		return curentcount;
	}
	public void setCurentcount(int curentcount) {
		this.curentcount = curentcount;
	}
	public int getTotalpage() {
		return totalpage;
	}
	public void setTotalpage(int totalpage) {
		this.totalpage = totalpage;
	}
	public int getTotalcount() {
		return totalcount;
	}
	public void setTotalcount(int totalcount) {
		this.totalcount = totalcount;
	}
	public List<T> getProductlistbycid() {
		return productlistbycid;
	}
	public void setProductlistbycid(List<T> productlistbycid) {
		this.productlistbycid = productlistbycid;
	}
	@Override
	public String toString() {
		return "PageBean [currentpage=" + currentpage + ", curentcount=" + curentcount + ", totalpage=" + totalpage
				+ ", totalcount=" + totalcount + ", productlistbycid=" + productlistbycid + "]";
	}
	
	

}
