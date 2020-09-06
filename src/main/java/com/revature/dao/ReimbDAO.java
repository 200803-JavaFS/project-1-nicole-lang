package com.revature.dao;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.query.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.models.Reimb;
import com.revature.models.ReimbDTO;
import com.revature.models.ReimbStatus;
import com.revature.models.ReimbType;
import com.revature.models.User;
import com.revature.utils.HibernateUtil;

public interface ReimbDAO {

	public static Logger log = LogManager.getLogger();
	public static boolean insert(ReimbDTO request) {
		//create new reimbursement request
		Boolean forTest = false;
		Transaction tx;
		Reimb r = new Reimb();
		r.setAmt(request.amt);
		r.setAuthor(UserDAO.selectByUsername(request.author));
		r.setDesc(request.desc);
		r.setType(getType(request.typeId));
		r.setStatus(getStatus(request.statusId));
		
		Session ses = HibernateUtil.getSession();
		if(ses.getTransaction()!=null)
		{//If accessed by ReimbServiceTests, there will already be a transaction in progress.
			forTest = true;
			tx = ses.getTransaction();
		}
		else
			tx = ses.beginTransaction();
		//add new request to database
		try {
			ses.save(r);
		}catch(HibernateException e) {
			e.printStackTrace();
			tx.rollback();
			return false;
		}		
		String logMessage = "New reimbursement request added by user " + request.author;
		log.info(logMessage);
		if(!forTest)
			//only commit if not unit testing
			tx.commit();
		else
			tx.rollback();
		return true;
	}
	
	public static boolean update(ReimbDTO request) {
		//update status and resolver of a reimbursement
		Session ses = HibernateUtil.getSession();
		Reimb r = selectById(request.reimbId);
		r.setStatus(getStatus(request.statusId));
		r.setResolver(UserDAO.selectByUsername(request.resolver));
		r.setResolvedDate(new Timestamp(System.currentTimeMillis()));
		Transaction tx = ses.beginTransaction();
		
		//add new request to database
		try {
			ses.merge(r);
		}catch(HibernateException e) {
			e.printStackTrace();
			tx.rollback();
			return false;
		}
		String logMessage = "Reimbursement request " + request.reimbId + " " + r.getStatus().getName() + " by " + r.getResolver().getUserName();
		log.info(logMessage);
		tx.commit();
		return true;
		
	}
	
	public static ReimbStatus getStatus(int id) {
		Session ses = HibernateUtil.getSession();
		return ses.get(ReimbStatus.class, id);
	}

	public static Reimb selectById(int id) {
		Session ses = HibernateUtil.getSession();	
		return ses.get(Reimb.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public static List<Reimb> findAll() {
		System.out.println("In findAll");
		//return all reimbursement records, sorted by status (pending first)
		Session ses = HibernateUtil.getSession();		
		return ses.createQuery("FROM Reimb ORDER BY status ASC").list();
	}

	@SuppressWarnings("unchecked")
	public static List<Reimb> selectByUserName(String author) {
		System.out.println("In selectByUserName");
		//return all reimbursements submitted by the current user; for employees
		Session ses = HibernateUtil.getSession();
		User u = UserDAO.selectByUsername(author);
		Query<Reimb> q = ses.createQuery("FROM Reimb WHERE author.userID = :id");
		q.setParameter("id", u.getUserID());
		return q.list();
	}
	public static ReimbType getType(int id) {
		//return type object based on id received from json
		Session ses = HibernateUtil.getSession();
		return ses.get(ReimbType.class, id);
	}

	public static boolean delete(ReimbDTO r) {
		Session ses = HibernateUtil.getSession();
		Reimb toDelete = selectById(r.reimbId);
		Transaction tx = ses.beginTransaction();
		
		//add new request to database
		try {
			ses.delete(toDelete);
		}catch(HibernateException e) {
			e.printStackTrace();
			tx.rollback();
			return false;
		}
		String logMessage = "Test reimbursement removed successfully.";
		log.debug(logMessage);
		tx.commit();
		return true;
	}
}
