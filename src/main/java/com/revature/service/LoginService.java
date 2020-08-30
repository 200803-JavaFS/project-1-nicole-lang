package com.revature.service;

import com.revature.models.LoginDTO;
import com.revature.models.User;
import com.revature.dao.UserDAO;
public class LoginService {

	public boolean login(LoginDTO l)
	{
		//check if user exists and password matches
		UserDAO uDao = new UserDAO();
		User u = uDao.selectByUsername(l.userName);
		if(u!=null && l.password.equals(u.getPassword()))
		{
			return true;

		}else
			return false;
	}
	
	public User getUser(String username) {
		UserDAO uDao = new UserDAO();
		return uDao.selectByUsername(username);
	}
	
}
