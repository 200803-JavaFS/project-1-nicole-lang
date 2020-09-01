package com.revature.dao;

import java.util.Date;
import java.sql.Timestamp;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.models.Reimb;
import com.revature.models.ReimbDTO;
import com.revature.models.ReimbStatus;
import com.revature.utils.HibernateUtil;

public interface ReimbDAO {

	public static boolean insert(ReimbDTO request) {
		Reimb r = new Reimb();
		r.setAmt(request.amt);
		r.setAuthor(request.author);
		r.setDesc(request.desc);
		r.setType(request.type);
		r.setStatus(new ReimbStatus(1, "Pending"));
		
		Session ses = HibernateUtil.getSession();
		
		Transaction tx = ses.beginTransaction();
		//set timestamp for request submission
		r.setSubmittedDate(new Timestamp(new Date().getTime())); ;
		
		//add new request to database
		try {
			ses.save(r);
		}catch(HibernateException e) {
			e.printStackTrace();
			tx.rollback();
			return false;
		}		
		tx.commit();
		return true;
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
