package com.shop.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.shop.domain.Category;
import com.shop.domain.Order;
import com.shop.domain.PageBean;
import com.shop.domain.Product;
import com.shop.utils.DataSourceUtils;
import com.shop.dao.ProductDao;

public class ProductService {
       ProductDao productDao=new ProductDao();
	public List<Product> findhotproductlist() {
		// TODO Auto-generated method stub
		return 	productDao.findhotproductlist();
		
	}

	public List<Product> findnewproductlist() {
		// TODO Auto-generated method stub
		return 	productDao.findnewproductlist();
	}

	public PageBean findProductListByCid(String cid, int currentpage, int currentcount) {
		// TODO Auto-generated method stub
		PageBean pagebean = new PageBean();
		pagebean.setCurrentpage(currentpage);
		pagebean.setCurentcount(currentcount);
		//总条数
		int totalcount=productDao.findCountByCid(cid);
		pagebean.setTotalcount(totalcount);
		//总的页数
		/*	int pagetotal=(int) Math.ceil(1.0*totalcount/currentcount);*/
		int totalpage=(int) Math.ceil(1.0*totalcount/currentcount);
		pagebean.setTotalpage(totalpage);
		//当前页的productlist
		int index=(currentpage-1)*12;
		List<Product>productlistbycid=productDao.findProductListByCid(cid,index,currentcount);
		//封装当前分类的当前页数的productlist
		pagebean.setProductlistbycid(productlistbycid);
		
		return pagebean;
	}

	public Product findProductInfo(String pid) {
		// TODO Auto-generated method stub
		return productDao.findProductInfo(pid);

	}

	public void ordercart(Order order) {
		// TODO Auto-generated method stub
		/*
		 * service层执行两个工作
		 * 一个是添加订单，一个是添加订单项目
		 * 俩个要添加事物控制*/
		
			try {
				DataSourceUtils.startTransaction();
				productDao.setOrder(order);
				productDao.setOrderitem(order);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				try {
					DataSourceUtils.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			}finally{
				try {
					DataSourceUtils.commitAndRelease();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
	
	

}

	public void addorderAddress(String address, String name, String telephone, String oid) {
		// TODO Auto-generated method stub
		productDao.addorderAddress( address,  name,  telephone,oid);
		
	}

	public void setStatic(String r6_Order) {
		// TODO Auto-generated method stub
		productDao.setStatic(r6_Order);
		
	}

	public List<Order> findAllOrdersByUid(String uid) {
		// TODO Auto-generated method stub
		return productDao.findAllOrdersByUid( uid) ;
	}

	public List<Map<String, Object>> findProductAndOrderitemsListByOid(String oid) {
		// TODO Auto-generated method stub
		return productDao.findProductAndOrderitemsListByOid( oid);
	}

	public Product findproductBypid(String pid) {
		Product product = new Product ();
		product=productDao.findproductBypid(pid);
		Category cate=productDao.findcategoryBypid(pid);
		product.setCategory(cate);
		return product;
	}}
