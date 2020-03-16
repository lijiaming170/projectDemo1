package com.shop.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shop.service.UserService;

public class Checkusername extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username=request.getParameter("username");
		System.out.println(username);
		UserService service=new UserService();
		boolean folg=service.Checkusername(username);
		String json="{\"folg\":"+folg+"}";//注意json的数据格式，可以用字符串的json格式传输,因为单引号无法解析，所以要使用单引号
		response.getWriter().write(json);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}