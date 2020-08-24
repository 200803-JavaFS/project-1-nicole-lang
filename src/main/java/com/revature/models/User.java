package com.revature.models;

import java.io.Serializable;

public class User implements Serializable{

	//User model
	private static final long serialVersionUID = 1L;
	
	private int userID;
	private int roleID;
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	
	public User(int userID, int roleID, String userName, String password, String firstName, String lastName,
			String email) {
		super();
		this.userID = userID;
		this.roleID = roleID;
		this.userName = userName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

}
