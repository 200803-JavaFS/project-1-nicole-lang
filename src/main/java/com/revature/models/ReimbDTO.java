package com.revature.models;

import java.io.Serializable;
import java.sql.Timestamp;

public class ReimbDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public double amt;
	public String desc;
	public User author;	
	public User resolver;
	public ReimbStatus status;	
	public ReimbType type;
}
