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
		
		String decPass = cs.decrypt("ECitg4YwLuqAsmAeUGOZhA==");
		assertEquals(decPass, "password");
		System.out.println("Decryption test successful");
	}
}
