package com.revature.service;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.*;

public class CryptoService {
	public static Cipher c;
	public static final String alg = "AES";
	public String encrypt(String password) throws Exception {
		//convert password to byte array
		byte[] passwordBytes = password.getBytes();
		//create a KeyGenerator object to generate an encryption key
		KeyGenerator gen = KeyGenerator.getInstance(alg);
		//initialize for a key size of 128 bytes and generate a random key
		gen.init(128);
		SecretKey key = gen.generateKey();
		
		//initialize the Cipher object for encryption
		c = Cipher.getInstance(alg);
		c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = c.doFinal(passwordBytes);
        Base64.Encoder nCode = Base64.getEncoder();
        String result = nCode.encodeToString(encryptedBytes);
        return result;
	}
	public static void main(String[] args) throws NoSuchAlgorithmException {
		SecretKey key = KeyGenerator.getInstance(alg).generateKey();
		System.out.println(key);
	}
}
