package com.shop.service;

import java.util.List;
import java.util.Map;

import com.shop.domain.Category;
import com.shop.domain.Order;
import com.shop.domain.PageBean;
import com.shop.domain.Product;
import com.shop.dao.AdminDao;

public class AdminService {

	   AdminDao dao=new AdminDao();
	public List<Category> findAllcategory() {
		// TODO Auto-generated method stub
		return dao.findAllcategory();
	}
	public void addproduct(Product product) {
		// TODO Auto-generated method stub
		dao.addproduct(product);
	}
	public List<Order> findAllorders() {
		// TODO Auto-generated method stub
		return dao.findAllorders();
	}
	public List<Map<String, Object>> orderitemAndProductllist(String oid) {
		// TODO Auto-generated method stub
		return dao.orderitemAndProductllist(oid);
	}
	public List<Product> findAllproductlist() {
		// TODO Auto-generated method stub
		return dao.findAllproductlist();
	}
	public PageBean getPagebean(int currentpage, int currentcount) {
		// TODO Auto-generated method stub
		//封装当前页
		PageBean pagebean=new PageBean();
		pagebean.setCurrentpage(currentpage);
		//封装当前页面条数
		pagebean.setCurentcount(currentcount);
		
		//总条数
		int totalcount=dao.findcount();
		pagebean.setTotalcount(totalcount);
		//总的页数
		/*	int pagetotal=(int) Math.ceil(1.0*totalcount/currentcount);*/
		int totalpage=(totalcount+currentcount-1)/currentcount;
		pagebean.setTotalpage(totalpage);
		//当前页的productlist
		int index=(currentpage-1)*7;
		List<Product>productlistbycid=dao.findproduct(index, currentcount);
		//封装当前分类的当前页数的productlist
		pagebean.setProductlistbycid(productlistbycid);
		return pagebean;
	}
	


}
