package com.revature.models;

import java.io.Serializable;
import java.sql.Timestamp;

public class Reimb implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int reimbID;
	private double amt;
	private Timestamp submittedDate;
	private Timestamp resolvedDate;
	private String desc;
	private String author;
	private String status;
	private String type;
	private String resolver;
	
}
