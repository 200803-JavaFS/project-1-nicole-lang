package com.revature.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.models.Reimb;
import com.revature.utils.HibernateUtil;

public class ReimbDAO {

	public ReimbDAO()
	{
		super();
	}
	public void insert(Reimb r) {
		Session ses = HibernateUtil.getSession();
		
		Transaction tx = ses.beginTransaction();
		
		ses.save(r);
		
		tx.commit();
	}
	
	public void update(Reimb r) {
		Session ses = HibernateUtil.getSession();
		ses.merge(r);
	}
	
	public Reimb selectById(int id) {
		Session ses = HibernateUtil.getSession();
		
		return ses.get(Reimb.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<Reimb> findAll() {
		Session ses = HibernateUtil.getSession();
		
		return ses.createQuery("FROM Reimb ORDER BY type").list();
	}
}
