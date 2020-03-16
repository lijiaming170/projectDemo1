package com.shop.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

import com.shop.domain.User;
import com.shop.service.UserService;
import com.shop.utils.CommentUtils;
import com.shop.utils.MailUtils;

/**
 * Servlet implementation class UserServlet
 */
public class UserServlet extends BasicServlet {
    /*
     * 
     * 用户模块 
     * 包括 注册，校验名字，激活等代码*/     
	/*用户退出的方法*/
	public void exituser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//退出的操作就是把session区域当中的user变成null
		HttpSession session = request.getSession();
		   session.setAttribute("user", null);
		   response.sendRedirect(request.getContextPath()+"/default.jsp");
		
	}
	//用户登陆的方法
	public void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		UserService service=new UserService();
		User user=service.userlogin(username,password);
		if(user==null){
			//提示用户用户名或者密码不正确
			String hint="你的用用户名密码不正确或未激活";
			request.setAttribute("hint", hint);
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			return;	
		}
		else{
			//如果不为空，那么久将个人信息储存在session域当中，然后转发到主页 并且显示用户的个人信息。
			session.setAttribute("user", user);
		    response.sendRedirect(request.getContextPath()+"/default.jsp");
		}
		
	}
	
	// 用户注册方法
	public void register(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//	request.setCharacterEncoding("UTF-8");
		Map<String, String[]> properties = request.getParameterMap();
		/*
		 * 要注意封装的表单的数据并不全
		 */
		User user = new User();
		try {
			ConvertUtils.register(new Converter() {

				@Override
				public Object convert(Class arg0, Object value) {
					// TODO Auto-generated method stub
					// value就是接收到的值，将其转换为指定的日期格式
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					Date par = null;
					try {
						par = format.parse((String) value);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return par;

				}
			}, Date.class);
			BeanUtils.populate(user, properties);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		user.setUid(CommentUtils.getUuid());
		// id
		// telephone
		user.setTelephone(null);
		// private int state;//是否激活
		user.setState(0);
		// private String code;//激活码
		String uuid = CommentUtils.getUuid();
		user.setCode(uuid);
		UserService userService = new UserService();
		boolean flog = userService.regist(user);
		if (flog) {
			// 要是注册成功，就前往邮箱激活，激活码，通过激活码找到相应的客户
			// 发送激活邮件给客户
			// 激活内容
			// String emila="sdfasdhfa<a
			// href='http:/active?="+uuid+"'>"+"zvz"+uuid+"</a>";
			String emailMsg = "如需激活请点击这个链接<a href='http://localhost:8080/shop/user?method=active&activecode=" + uuid + "'>"
					+ "http://localhost:8080/shop/active?activecode=" + uuid + "</a>";
			try {
				MailUtils.sendMail("liuzihao_lzh@163.com", emailMsg);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response.sendRedirect(request.getContextPath() + "/registsuccess.jsp");
		} else {
			response.sendRedirect(request.getContextPath() + "/registfile.jsp");

		}
	}
//            用户校验名字是否存在的方法
	public void checkusername(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		UserService service = new UserService();
		boolean folg = service.Checkusername(username);
		String json = "{\"folg\":" + folg + "}";// 注意json的数据格式，可以用字符串的json格式传输,因为单引号无法解析，所以要使用单引号
		response.getWriter().write(json);
	}
	//用户激活方法
	public void active(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//点击后，实际上是去数据库修改激活的状态码即可
		String activecode=request.getParameter("activecode");
		UserService userService=new UserService();
		userService.active(activecode);
		response.sendRedirect(request.getContextPath()+"/login.jsp");
		
}}
