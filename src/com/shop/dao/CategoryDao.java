package com.shop.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.shop.domain.Category;
import com.shop.utils.DataSourceUtils;

public class CategoryDao {

	public List<Category> findcategorylist() {
		QueryRunner qy= new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select *  from category";
		List<Category>categorylist=null;
		try {
			categorylist=qy.query(sql, new BeanListHandler<Category>(Category.class));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return categorylist;
		
	}

}
