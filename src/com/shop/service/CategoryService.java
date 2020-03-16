package com.shop.service;

import java.util.List;

import com.shop.domain.Category;
import com.shop.domain.Product;
import com.shop.dao.CategoryDao;

public class CategoryService {
       CategoryDao categoryDao=new CategoryDao();
	public List<Category> findcategorylist() {
		// TODO Auto-generated method stub
		return  categoryDao.findcategorylist();
	}
	
}
