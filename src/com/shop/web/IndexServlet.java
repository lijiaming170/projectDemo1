package com.shop.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shop.domain.Category;
import com.shop.domain.Product;
import com.shop.service.CategoryService;
import com.shop.service.ProductService;

public class IndexServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     /*获得最新
      * 最热的商品*/
		ProductService service=new ProductService();
		List<Product>hotproductlist=service.findhotproductlist();
		request.setAttribute("hotproductlist", hotproductlist);//将准备的数据存储在request域当中
	
		List<Product>newproductlist=service.findnewproductlist();
		request.setAttribute("newproductlist", newproductlist);
		
		/*CategoryService categoryservice=new CategoryService();
		List<Category>categorylist=categoryservice.findcategorylist();
		 request.setAttribute("categorylist", categorylist);*/
		 
		request.getRequestDispatcher("/index.jsp").forward(request, response);
		/*
		 *查询商品分类list*/
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}