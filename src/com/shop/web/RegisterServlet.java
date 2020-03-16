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

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

import com.shop.domain.User;
import com.shop.service.UserService;
import com.shop.utils.CommentUtils;
import com.shop.utils.MailUtils;

public class RegisterServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//request.setCharacterEncoding("UTF-8");
		Map<String, String[]> properties = request.getParameterMap();
		/*
		 * 要注意封装的表单的数据并不全*/
		User user=new User();
		try {
			ConvertUtils.register(new Converter(){

				@Override
				public Object convert(Class arg0, Object value) {
					// TODO Auto-generated method stub
					//value就是接收到的值，将其转换为指定的日期格式
					SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
					Date par=null;
					try {
						par=format.parse((String) value);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return par;
					
				}}, Date.class);
			BeanUtils.populate(user, properties);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		user.setUid(CommentUtils.getUuid());
		//id
		//telephone
		user.setTelephone(null);
		//private int state;//是否激活
		user.setState(0);
		//private String code;//激活码
		String uuid=CommentUtils.getUuid();
		user.setCode(uuid);
		UserService userService=new UserService();
	    boolean flog=userService.regist(user);
	    if(flog){
	    	//要是注册成功，就前往邮箱激活，激活码，通过激活码找到相应的客户
	    	//发送激活邮件给客户
	    	//激活内容
	    	//String emila="sdfasdhfa<a href='http:/active?="+uuid+"'>"+"zvz"+uuid+"</a>";
	    	String emailMsg="如需激活请点击这个链接<a href='http://localhost:8080/shop/active?activecode="+uuid+"'>"+"http://localhost:8080/shop/active?activecode="+uuid+"</a>";
	    	try {
				MailUtils.sendMail("liuzihao_lzh@163.com", emailMsg);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	response.sendRedirect(request.getContextPath()+"/registsuccess.jsp");
	    }else{
	    	response.sendRedirect(request.getContextPath()+"/registfile.jsp");

	    }
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}