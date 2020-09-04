package com.revature.dao;

import java.util.List;

import org.hibernate.Session;
import com.revature.models.User;
import com.revature.utils.HibernateUtil;

public interface UserDAO {
	public static User selectById(int id) {
		Session ses = HibernateUtil.getSession();		
		return ses.get(User.class, id);
	}	
	@SuppressWarnings("unchecked")
	public static User selectByUsername(String username) {
		Session ses = HibernateUtil.getSession();
		List<User> u = ses.createQuery("FROM User WHERE userName = '" + username+ "'").list();	
		try {
			User inputUser = u.get(0);
			return inputUser;
		}catch(IndexOutOfBoundsException e) {
			System.out.println("User not found");
			return null;
		}
	}
	@SuppressWarnings("unchecked")
	public static List<User> findAll() {
		Session ses = HibernateUtil.getSession();			
		return ses.createQuery("FROM User").list();
	}
}
