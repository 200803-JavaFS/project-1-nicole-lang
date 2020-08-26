package com.revature.service;

import com.revature.models.LoginDTO;
import com.revature.dao.LoginDAO;
public class LoginService {

	public boolean login(LoginDTO l)
	{
		//temporary functionality, pre-database
		//call loginDAO method to check password validity
		if(l.userName.equals("nlang") && l.password.equals("82798"))
			return true;
		else
			return false;
	}
	
}
