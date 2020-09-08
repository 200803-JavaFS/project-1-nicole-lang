package com.revature.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	//utility class for Hibernate; creates and handles the session factory
	private static Session ses;
	private static SessionFactory sf = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
	
	public static Session getSession() {
		if(ses==null) {
			ses=sf.openSession();
		}
		return ses;
	}
	
	public static void closeSes() {
		ses.close();
		ses = null;
	}

}
