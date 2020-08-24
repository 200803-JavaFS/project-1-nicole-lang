package com.revature.models;

import java.io.Serializable;
import java.sql.Timestamp;

public class Reimb implements Serializable{
	
	//Reimbursement model
	private static final long serialVersionUID = 1L;
	
	private int reimbID;
	private double amt;
	private Timestamp submittedDate;
	private Timestamp resolvedDate;
	private String desc;
	private String author;
	private String status;
	private String type;
	
	public Reimb(int reimbID, double amt, Timestamp submittedDate, Timestamp resolvedDate, String desc, String author,
			String status, String type, String resolver) {
		super();
		this.reimbID = reimbID;
		this.amt = amt;
		this.submittedDate = submittedDate;
		this.resolvedDate = resolvedDate;
		this.desc = desc;
		this.author = author;
		this.status = status;
		this.type = type;
		this.resolver = resolver;
	}
	private String resolver;
	
}
