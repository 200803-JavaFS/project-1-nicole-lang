package com.revature.service;

import com.revature.models.LoginDTO;
import com.revature.models.User;
import com.revature.dao.UserDAO;
public class LoginService implements UserDAO{
	
	//Service class for user login
	public boolean login(LoginDTO l) throws Exception
	{
		//log in as an existing user; returns a boolean
		
		//initialize CryptoService
		CryptoService cs = new CryptoService();
		
		//check if user exists and password matches
		User u = UserDAO.selectByUsername(l.username);
		return(u!=null && l.password.equals(cs.decrypt(u.getPassword())));
	}
	
	public User getUser(String username) {
		//called after login method returns true; returns a user
		return UserDAO.selectByUsername(username);
	}
	
}
