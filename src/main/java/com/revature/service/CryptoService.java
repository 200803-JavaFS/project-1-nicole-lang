package com.revature.service;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.util.Base64;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

public class CryptoService {
	//This class is used by LoginService to handle password encryption and decryption
	public static Cipher c;
	public static final String alg = "AES";
	public static final String keyHex = "1f6Ba8742ccc6ff94ddc3db0b90fC862";

	public String encrypt(String password) throws Exception {
		SecretKey key = loadKey();
		
		//convert password to byte array
		byte[] passwordBytes = password.getBytes();
		
		
		//initialize the Cipher object for encryption
		c = Cipher.getInstance(alg);
		c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = c.doFinal(passwordBytes);
        
        //encrypt the password and return it
        Base64.Encoder nCode = Base64.getEncoder();
        return nCode.encodeToString(encryptedBytes);
	}
	
	public String decrypt(String encPassword) throws Exception {
		SecretKey key = loadKey();
		//decrypt the password retrived from the database
		Base64.Decoder dCode = Base64.getDecoder();
        byte[] encPassByte = dCode.decode(encPassword);
       
        //initialize the Cipher object for decryption
      	c = Cipher.getInstance(alg);
        c.init(Cipher.DECRYPT_MODE, key);
        
        byte[] decryptedByte = c.doFinal(encPassByte);
        return new String(decryptedByte);
	}
	public static SecretKey loadKey() throws DecoderException {
		//load secret key
        byte[] keyBytes = Hex.decodeHex(keyHex.toCharArray());
		return new SecretKeySpec(keyBytes, alg);
	}
}
