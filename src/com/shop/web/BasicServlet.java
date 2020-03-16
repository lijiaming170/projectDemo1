package com.shop.web;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BasicServlet extends HttpServlet {

	 @Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		 req.setCharacterEncoding("UTF-8");//这个作用相当于过滤器，能够设置全局访问这个编码属性
		 resp.setContentType("text/html;charset=UTF-8");
		 try {
		    	String methodname=req.getParameter("method");
		    	Class clazz=this.getClass();//this代表当前访问这个这个对象
			Method method=	clazz.getMethod(methodname, HttpServletRequest.class,HttpServletResponse.class);
			//利用反射 实现方法
			method.invoke(this, req,resp);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				 
		 
	}

	}
