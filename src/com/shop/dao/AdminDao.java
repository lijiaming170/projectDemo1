package com.shop.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.shop.domain.Category;
import com.shop.domain.Order;
import com.shop.domain.Product;
import com.shop.utils.DataSourceUtils;

public class AdminDao {

	public List<Category> findAllcategory() {
		QueryRunner qy= new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from category";
		List<Category>list=null;
		try {
			list=qy.query(sql, new BeanListHandler<Category>(Category.class));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list ;
	}

	public void addproduct(Product product) {
		// TODO Auto-generated method stub
				QueryRunner qy=new QueryRunner(DataSourceUtils.getDataSource());
				try {
					String sql="insert into product values(?,?,?,?,?,?,?,?,?,?)";
					qy.update(sql,product.getPid(),product.getPname(),product.getMarket_price(),
							product.getShop_price(),product.getPimage(),product.getPdate(),
							product.getIs_hot(),product.getPdesc(),product.getPflag(),product.getCategory().getCid());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
	}

	public List<Order> findAllorders() {
		QueryRunner qy= new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from orders";
		List<Order>list=null;
		try {
			list=qy.query(sql, new BeanListHandler<Order>(Order.class));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<Map<String, Object>> orderitemAndProductllist(String oid) {
		QueryRunner qy= new QueryRunner(DataSourceUtils.getDataSource());
		// i.count,i.subtotal,p.pimage,p.pname,p.shop_price 
		String sql="select  i.count,i.subtotal,p.pimage,p.pname,p.shop_price from product p,orderitem i where p.pid=i.pid and i.oid=?";	
		List<Map<String,Object>>orderitemAndProductllist=null;
		try {
			orderitemAndProductllist=qy.query(sql, new MapListHandler(), oid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return orderitemAndProductllist;
	}

	public List<Product> findAllproductlist() {
		// TODO Auto-generated method stub
		
		QueryRunner qy=new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from product";
		List<Product>list=null;
		try {
			list=qy.query(sql, new BeanListHandler<Product>(Product.class));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public int findcount() {
		// TODO Auto-generated method stub
		QueryRunner qy=new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select count(*) from product";
		Long lo=null;
		try {
			lo=(Long) qy.query(sql, new ScalarHandler());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lo.intValue();
	}

	public List<Product> findproduct(int start, int currentcount) {
		QueryRunner qy=new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select *from product limit ?,?";
		List<Product>list=null;
	     try {
			list=qy.query(sql, new BeanListHandler<Product>(Product.class),start ,currentcount);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	

	

}
