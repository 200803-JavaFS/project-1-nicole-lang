package com.revature.dao;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.models.LoginDTO;
import com.revature.models.User;
import com.revature.utils.HibernateUtil;

public interface UserDAO {
	public static void insert(User u) {
		Session ses = HibernateUtil.getSession();
		
		Transaction tx = ses.beginTransaction();
		
		ses.save(u);
		
		tx.commit();
	}
	
	public static void update(User u) {
		Session ses = HibernateUtil.getSession();
		ses.merge(u);
	}
	
	public static User selectById(int id) {
		Session ses = HibernateUtil.getSession();
		
		return ses.get(User.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public static User selectByUsername(String username) {
		Session ses = HibernateUtil.getSession();
		List<User> u = ses.createQuery("FROM User WHERE userName = " + username).list();		
		return u.get(0);
	}
	@SuppressWarnings("unchecked")
	public static List<User> findAll() {
		Session ses = HibernateUtil.getSession();	
		
		return ses.createQuery("FROM User").list();
	}
}
