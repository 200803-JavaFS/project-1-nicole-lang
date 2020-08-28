package com.revature.service;

import com.revature.models.LoginDTO;
import com.revature.models.User;
import com.revature.dao.UserDAO;
public class LoginService {

	public User login(LoginDTO l)
	{
		//temporary functionality, pre-database
		//call loginDAO method to check password validity
		UserDAO uDao = new UserDAO();
		return uDao.selectByUsername(l.userName);
	}
	
}
