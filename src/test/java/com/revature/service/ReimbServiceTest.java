package com.revature.service;

import static org.junit.Assert.*;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.models.Reimb;
import com.revature.models.ReimbDTO;
import com.revature.utils.HibernateUtil;

public class ReimbServiceTest {
	public static ReimbService rs;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("Initiating ReimbService tests");
		rs = new ReimbService();
	}
	
	@Test
	public void testGetByID() {
		System.out.println("Testing get reimb by ID");
		Reimb r = rs.getByID(1);
		assertTrue(r.getAuthor().getUserName().equals("hkendrick"));
		System.out.println("getByID test successful");
	}

	@Test
	public void testGetAll() {
		List<Reimb> reimbs;
		System.out.println("Testing get all reimbs");
		try{
			reimbs = rs.getAll();
			assertFalse(reimbs.isEmpty());
			System.out.println("getAll test successful");
		}catch(Exception e) {
			fail();
		}
		
	}

	@Test
	public void testGetByUser() {
		List<Reimb> reimbs;
		System.out.println("Testing get reimbs by username");
		try{
			reimbs = rs.getByUser("hkendrick");
			assertFalse(reimbs.isEmpty());
			System.out.println("getByUser test successful");
		}catch(Exception e) {
			fail();
		}
		
	}

	@Test
	public void testAddReimb() {
		System.out.println("Testing add reimbursement");
		Session ses = HibernateUtil.getSession();
		ReimbDTO rDTO = new ReimbDTO();
		rDTO.amt = 100;
		rDTO.author = "gattietime";
		rDTO.desc = "sample request";
		rDTO.statusId = 1;
		rDTO.typeId = 4;
		Transaction tx = ses.beginTransaction();
		assertTrue(rs.addReimb(rDTO));
		System.out.println("Add reimbursement successful");
	}

}
