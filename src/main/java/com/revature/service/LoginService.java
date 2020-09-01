package com.revature.service;

import com.revature.models.LoginDTO;
import com.revature.models.User;
import com.revature.dao.UserDAO;
public class LoginService implements UserDAO{
	
	public boolean login(LoginDTO l) throws Exception
	{
		//initialize CryptoService
		CryptoService cs = new CryptoService();
		
		//check if user exists and password matches
		User u = UserDAO.selectByUsername(l.username);
		if(u!=null && l.password.equals(cs.decrypt(u.getPassword())))
			return true;
		else
			return false;
	}
	
	public User getUser(String username) {
		//called after login method returns true
		return UserDAO.selectByUsername(username);
	}
	
}
