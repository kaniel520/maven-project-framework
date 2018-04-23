package com.ynk.tool;
import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;


public class Cryptor {

	 private  Cipher cipher;
	    private  String secretKey = "CM/TTu+yCr+7Ug8e";
	    private  String iv = "ad/BSYVNLR+ak56m";
	    private  final String CIPHER_MODE = "AES/CFB8/NoPadding";

	    private  SecretKey keySpec;
	    private  IvParameterSpec ivSpec;
	    private  Charset CHARSET = Charset.forName("UTF-8");

	    public Cryptor() {
	        keySpec = new SecretKeySpec(secretKey.getBytes(CHARSET), "AES");
	        ivSpec = new IvParameterSpec(iv.getBytes(CHARSET));
	        try {
	            cipher = Cipher.getInstance(CIPHER_MODE);
	        } catch (NoSuchAlgorithmException e) {
	            throw new SecurityException(e);
	        } catch (NoSuchPaddingException e) {
	            throw new SecurityException(e);
	        }
	    }

	    /**
	     * @param input A "AES/CFB8/NoPadding" encrypted String
	     * @return The decrypted String
	     * @throws CryptingException
	     */
	    public  String decrypt(String input) {

	        try {
	            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
	            return  new String(cipher.doFinal(DatatypeConverter.parseBase64Binary(input))); 
//	            return new String(cipher.doFinal(Base64.decodeBase64(input)));
	        } catch (IllegalBlockSizeException e) {
	            throw new SecurityException(e);
	        } catch (BadPaddingException e) {
	            throw new SecurityException(e);
	        } catch (InvalidKeyException e) {
	            throw new SecurityException(e);
	        } catch (InvalidAlgorithmParameterException e) {
	            throw new SecurityException(e);
	        }
	    }

	    /**
	     * @param input Any String to be encrypted
	     * @return An "AES/CFB8/NoPadding" encrypted String
	     * @throws CryptingException
	     */
	    public  String encrypt(String input)  {
	        try {
	            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
	            return DatatypeConverter.printBase64Binary(cipher.doFinal(input.getBytes(CHARSET)));
//	            return Base64.encodeBase64String(cipher.doFinal(input.getBytes(CHARSET)));
	        } catch (InvalidKeyException e) {
	            throw new SecurityException(e);
	        } catch (InvalidAlgorithmParameterException e) {
	            throw new SecurityException(e);
	        } catch (IllegalBlockSizeException e) {
	            throw new SecurityException(e);
	        } catch (BadPaddingException e) {
	            throw new SecurityException(e);
	        }
	    }

	  
}
