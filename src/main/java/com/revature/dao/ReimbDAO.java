package com.revature.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.models.Reimb;
import com.revature.utils.HibernateUtil;

public interface ReimbDAO {

	public static void insert(Reimb r) {
		Session ses = HibernateUtil.getSession();
		
		Transaction tx = ses.beginTransaction();
		
		ses.save(r);
		
		tx.commit();
	}
	
	public static void update(Reimb r) {
		Session ses = HibernateUtil.getSession();
		ses.merge(r);
	}
	
	public static Reimb selectById(int id) {
		Session ses = HibernateUtil.getSession();
		
		return ses.get(Reimb.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public static List<Reimb> findAll() {
		//return all reimbursement records, sorted by status (pending first)
		Session ses = HibernateUtil.getSession();
		
		return ses.createQuery("FROM Reimb ORDER BY status ASC").list();
	}

	@SuppressWarnings("unchecked")
	public static List<Reimb> selectByUserName(String userName) {
		//return all reimbursements submitted by the current user; for employees
		Session ses = HibernateUtil.getSession();
		return ses.createQuery("FROM Reimb WHERE reimb_author = " + UserDAO.selectByUsername(userName).getUserID()).list();
	}
}
