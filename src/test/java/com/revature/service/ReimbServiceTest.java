package com.revature.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.models.Reimb;

public class ReimbServiceTest {
	public static ReimbService rs;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("Initiating ReimbService tests");
		rs = new ReimbService();
	}

	@Test
	public void testGetByID() {
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
