package com.shop.service;

import com.shop.domain.User;
import com.shop.dao.UserDao;

public class UserService {
     UserDao userDao=new UserDao();
 	public boolean regist(User user) {
		// TODO Auto-generated method stub
		int row=userDao.regist(user);
		return row>0?true:false;
	}
	public void active(String activecode) {
		// TODO Auto-generated method stub
	  userDao.active(activecode);
		
	}
	public boolean Checkusername(String username) {
		// TODO Auto-generated method stub
		Long lo=userDao.Checkusername(username);
		return lo>0?true:false;
	}
	public User userlogin(String username, String password) {
		// TODO Auto-generated method stub
		return  userDao.userlogin( username,  password);
	}
	
	
	

}
