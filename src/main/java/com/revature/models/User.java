package com.revature.models;

import java.io.Serializable;

public class User implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int userID;
	private int roleID;
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private String email;

}
