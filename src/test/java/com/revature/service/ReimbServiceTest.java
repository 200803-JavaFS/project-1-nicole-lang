package com.revature.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.models.Reimb;
import com.revature.models.ReimbDTO;

public class ReimbServiceTest {
	public static ReimbService rs;
	public static ReimbDTO data;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("Initiating ReimbService tests");
		rs = new ReimbService();
		data.amt = 100.00;
		data.author = "hkendrick";
		data.desc = "test reimbursement";
		data.statusId = 1;
		data.typeId = 4;
	}
	
	@Test
	public void testGetByID() {
		System.out.println("Testing get by ID");
		fail("Not yet implemented");
	}

	@Test
	public void testGetAll() {
		List<Reimb> reimbs = rs.getAll();
		fail("Not yet implemented");
	}

	@Test
	public void testGetByUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddReimb() {
		fail("Not yet implemented");
	}

}
