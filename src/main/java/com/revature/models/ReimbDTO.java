package com.revature.models;

import java.io.Serializable;
import java.sql.Timestamp;

public class ReimbDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public double amt;	
	public Timestamp submittedDate;
	public String desc;
	public User author;	
	public ReimbStatus status;	
	public ReimbType type;
}
