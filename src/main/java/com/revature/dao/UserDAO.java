package com.revature.dao;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.models.LoginDTO;
import com.revature.models.User;
import com.revature.utils.HibernateUtil;

public class UserDAO {
	public void insert(User u) {
		Session ses = HibernateUtil.getSession();
		
		Transaction tx = ses.beginTransaction();
		
		ses.save(u);
		
		tx.commit();
	}
	
	public void update(User u) {
		Session ses = HibernateUtil.getSession();
		ses.merge(u);
	}
	
	public User selectById(int id) {
		Session ses = HibernateUtil.getSession();
		
		return ses.get(User.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public User selectByUsername(String username) {
		Session ses = HibernateUtil.getSession();
		List<User> u = ses.createQuery("FROM User WHERE userName = " + username).list();		
		return u.get(0);
	}
	@SuppressWarnings("unchecked")
	public List<User> findAll() {
		Session ses = HibernateUtil.getSession();	
		
		return ses.createQuery("FROM User").list();
	}
}
