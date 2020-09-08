package com.revature.service;

import static org.junit.Assert.*;

import org.junit.BeforeClass;

import com.revature.models.LoginDTO;
import com.revature.models.User;

import org.junit.Test;

public class LoginServiceTest {
	public static final String goodUN = "nlang";
	public static final String badUN = "i dont exist";
	public static LoginService ls;
	public static LoginDTO login;
	
	@BeforeClass
	public static void init() {
		System.out.println("Initiating LoginService tests");
		ls = new LoginService();
		login = new LoginDTO();
	}
	@Test
	public void testGetUser() {
		System.out.println("Testing getUser");
		User u = ls.getUser(goodUN);
		assertEquals(goodUN, u.getUserName());
		System.out.println("Valid username test successful");
		assertEquals(ls.getUser(badUN), null);
		System.out.println("Invalid username test successful");
		System.out.println("getUser tests successful");
	}
	@Test
	public void loginTest() throws Exception {
		System.out.println("Testing login");
		login.username = goodUN;
		login.password = "82798";
		assertTrue(ls.login(login));
		System.out.println("Valid username check successful");
		login.username = badUN;
		assertFalse(ls.login(login));
		System.out.println("Invalid username check successful");
		System.out.println("login tests successful");
	}
}
