package com.shop.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.shop.domain.Category;
import com.shop.domain.Order;
import com.shop.domain.Orderitem;
import com.shop.domain.Product;
import com.shop.utils.DataSourceUtils;

public class ProductDao {

	public List<Product> findhotproductlist() {
		// TODO Auto-generated method stub
		QueryRunner qy= new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from product where is_hot=? limit ?,?";
		List<Product>hotproductlist=null;
		try {
			hotproductlist=qy.query(sql, new BeanListHandler<Product>(Product.class), 1,0,9);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hotproductlist;
	}

	public List<Product> findnewproductlist() {
		QueryRunner qy= new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from product order by pdate desc limit ?,?";
		List<Product>newproductlist=null;
		try {
		      newproductlist=qy.query(sql, new BeanListHandler<Product>(Product.class),0,9);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newproductlist;

}

	public int findCountByCid(String cid) {
		// TODO Auto-generated method stub
		QueryRunner qy= new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select count(*) from product where cid=? ";
		Long lo=null;
		try {
			 lo=(Long) qy.query(sql, new ScalarHandler() , cid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lo.intValue();
	}

	public List<Product> findProductListByCid(String cid, int index, int currentcount) {
		// TODO Auto-generated method stub
		QueryRunner qy= new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from product where cid=? limit ?,?";
		List<Product>list=null;
		  try {
			list = qy.query(sql, new BeanListHandler<Product>(Product.class), cid,index,currentcount);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public Product findProductInfo(String pid) {
		// TODO Auto-generated method stub
		QueryRunner qy= new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from product where pid=?";
		Product product=null;
		try {
			 product=qy.query(sql, new BeanHandler<Product>(Product.class), pid);
			 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return product;
		
	}
      //向数据库添加order信息
	public void setOrder(Order order) {
		// TODO Auto-generated method stub
		try {
			QueryRunner qy= new QueryRunner();
			Connection con=DataSourceUtils.getConnection();
			String sql="insert into orders values(?,?,?,?,?,?,?,?)";
			qy.update(con, sql, order.getOid(),order.getOrdertime(),order.getTotal(),
					order.getState(),order.getAddress(),order.getName(),order.getTelephone(),order.getUser().getUid());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public void setOrderitem(Order order) {
		// TODO Auto-generated method stub
		QueryRunner qy= new QueryRunner();
		Connection con=null;
		try {
		 con=DataSourceUtils.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		String sql="insert into orderitem values(?,?,?,?,?)";
		for(Orderitem item:order.getOrderitem()){
			try {
				qy.update(con, sql,item.getItemid(),
						item.getCount(),item.getSubtotal(),item.getProduct().getPid(),item.getOrder().getOid());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	public void addorderAddress(String address, String name, String telephone, String oid) {
		QueryRunner qy= new QueryRunner(DataSourceUtils.getDataSource());
		String sql="update orders set address=?, name=?,telephone=? where oid=?";
		try {
			qy.update(sql, address,name,telephone,oid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void setStatic(String r6_Order) {
		// TODO Auto-generated method stub
		QueryRunner qy= new QueryRunner(DataSourceUtils.getDataSource());
		String sql="update orders set state=? where oid=?";
		try {
			qy.update(sql, 1,r6_Order);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public List<Order> findAllOrdersByUid(String uid) {
		QueryRunner qy= new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from orders where uid=?";
		List<Order>list=null;
		try {
			list=qy.query(sql, new BeanListHandler<Order>(Order.class), uid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<Map<String, Object>> findProductAndOrderitemsListByOid(String oid) {
		QueryRunner qy= new QueryRunner(DataSourceUtils.getDataSource());
		// i.count,i.subtotal,p.pimage,p.pname,p.shop_price 
		String sql="select * from product p,orderitem i where p.pid=i.pid and i.oid=?";	
		List<Map<String,Object>>ProductAndOrderitemsList=null;
		try {
			ProductAndOrderitemsList=qy.query(sql, new MapListHandler(), oid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ProductAndOrderitemsList;
	}

	public Product findproductBypid(String pid) {
		QueryRunner qy= new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from  product where pid=?";
		Product pro=null;
		 try {
			pro=qy.query(sql, new BeanHandler<Product>(Product.class), pid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pro;
	}
   //通过pid找cid

	public Category findcategoryBypid(String pid) {
		QueryRunner qy= new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select c.cid,c.cname from  product p, category c where p.cid=c.cid and p.pid=?";
		Category cate=null;
		 try {
			cate=qy.query(sql, new BeanHandler<Category>(Category.class), pid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cate;
	}


	
}