package com.revature.models;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name="ers_reimbursements")
public class Reimb implements Serializable{
	
	//Reimbursement model
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="reimb_id")
	private int reimbID;	
	@Column(name="reimb_amount")
	private double amt;
	@CreationTimestamp
	@Column(name="reimb_submitted")
	private Timestamp submittedDate;
	@Column(name="reimb_resolved")
	private Timestamp resolvedDate;
	@Column(name="reimb_description")
	private String desc;
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL) //many to one relationship
	@JoinColumn(name="reimb_author")
	private User author;
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="reimb_status_id")
	private ReimbStatus status;
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="reimb_type_id")
	private ReimbType type;
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="reimb_resolver")
	private User resolver;
	
	public Reimb() {
		super();
	}

	public Reimb(int reimbID, double amt, Timestamp submittedDate, Timestamp resolvedDate, String desc,
			ReimbStatus status, ReimbType type) {
		super();
		this.reimbID = reimbID;
		this.amt = amt;
		this.submittedDate = submittedDate;
		this.resolvedDate = resolvedDate;
		this.desc = desc;
		this.status = status;
		this.type = type;
	}

	public Reimb(double amt, Timestamp submittedDate, Timestamp resolvedDate, String desc,
			ReimbStatus status, ReimbType type) {
		super();
		this.amt = amt;
		this.submittedDate = submittedDate;
		this.desc = desc;
		this.status = status;
		this.type = type;
	}

	public int getReimbID() {
		return reimbID;
	}

	public void setReimbID(int reimbID) {
		this.reimbID = reimbID;
	}

	public double getAmt() {
		return amt;
	}

	public void setAmt(double amt) {
		this.amt = amt;
	}

	public Timestamp getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(Timestamp submittedDate) {
		this.submittedDate = submittedDate;
	}

	public Timestamp getResolvedDate() {
		return resolvedDate;
	}

	public void setResolvedDate(Timestamp resolvedDate) {
		this.resolvedDate = resolvedDate;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public ReimbStatus getStatus() {
		return status;
	}

	public void setStatus(ReimbStatus status) {
		this.status = status;
	}

	public ReimbType getType() {
		return type;
	}

	public void setType(ReimbType type) {
		this.type = type;
	}

	public User getResolver() {
		return resolver;
	}

	public void setResolver(User resolver) {
		this.resolver = resolver;
	}

}
