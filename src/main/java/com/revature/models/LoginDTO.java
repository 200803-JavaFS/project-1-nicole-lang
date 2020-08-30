package com.revature.models;

import java.io.Serializable;

public class LoginDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public String userName;
	public String password;
	public int type;
	public int userID;
	
}
