package com.shop.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shop.domain.PageBean;
import com.shop.domain.Product;
import com.shop.service.ProductService;

public class ProductListByCidServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cid=request.getParameter("cid");
		request.setAttribute("cid", cid);
		ProductService  service=new ProductService();
		int currentcount=12;
		int currentpage=0;
		String current=request.getParameter("currentpage");
//		System.out.println(current+"-----current");
		if(current==null||current.equals("")){
			currentpage=1;
		}else{
			currentpage=Integer.parseInt(current);
		}
		  PageBean pageBean=service.findProductListByCid(cid,currentpage,currentcount);
		  List<Product>list=pageBean.getProductlistbycid();
		 /* for(Product pro:list){
			  System.out.println(pro);
			  
		  }*/
		 
		  request.setAttribute("pageBean", pageBean);
		  request.getRequestDispatcher("/product_list.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}