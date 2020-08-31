package com.revature.service;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class CryptoTest {
	private static CryptoService cs;
	@BeforeClass
	public static void setUp() {
		cs = new CryptoService();
	}
	
	@Test
	public void testEncryptDecrypt() throws Exception {
		System.out.println("Testing CryptoService...");
		String password = "82798";
		String encPass = cs.encrypt(password);
		assertEquals(password, encPass);
		System.out.println("Encryption test successful");
		
		String decPass = cs.decrypt(encPass);
		assertEquals(password, decPass);
		System.out.println("Decryption test successful");
	}
}
