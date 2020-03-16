package com.shop.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.shop.domain.Category;
import com.shop.service.CategoryService;

public class CategoryListServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CategoryService categoryservice=new CategoryService();
		List<Category>categorylist=categoryservice.findcategorylist();
		Gson json=new Gson();
		String categorylist1 = json.toJson(categorylist);
		System.out.println(categorylist1);
		response.setContentType("text/html;charset=UTF-8");
		//向jsp页面输入json数据 ，由ajax接收
		response.getWriter().write(categorylist1);
		//request.setAttribute("categorylist", categorylist);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}