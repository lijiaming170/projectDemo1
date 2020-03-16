package com.shop.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.shop.domain.User;
import com.shop.utils.DataSourceUtils;

public class UserDao {

	public int regist(User user) {
		// TODO Auto-generated method stub
		QueryRunner qy=new QueryRunner(DataSourceUtils.getDataSource());
		String sql="insert into user values(?,?,?,?,?,?,?,?,?,?)";
		int clow=0;
		try {
			 clow=qy.update(sql, user.getUid(),user.getUsername(),user.getPassword(),
					user.getName(),user.getEmail(),user.getTelephone(),
					user.getBirthday(),user.getSex(),user.getState(),user.getCode());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return clow;
	}

	public void active(String activecode) {
		// TODO Auto-generated method stub
		QueryRunner qy=new QueryRunner(DataSourceUtils.getDataSource());
         String sql="update user set state=1 where code=?";
         try {
			qy.update(sql, activecode);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public Long Checkusername(String username) {
		// TODO Auto-generated method stub
		QueryRunner qy=new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select count(*) from user where username=?";
		//默认是返回object类型的数据，但是其实是Long 的数据类型，如果要转换使用intValue方法
		Long lo=null;
		try {
			lo = (Long) qy.query(sql, new ScalarHandler(), username);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lo;
	}

	public User userlogin(String username, String password) {
		// TODO Auto-generated method stub
		QueryRunner qy=new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from user where username=? and password=? and state=?";
		User user=null;
		try {
			user=qy.query(sql, new BeanHandler<User>(User.class), username,password,1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return user;
	}

	
	
}
