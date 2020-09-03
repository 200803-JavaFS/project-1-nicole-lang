package com.revature.models;

import java.io.Serializable;
import java.sql.Timestamp;

public class ReimbDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public int reimbId;
	public double amt;
	public String desc;
	public String author;	
	public String resolver;
	public int statusId;	
	public int typeId;
}
