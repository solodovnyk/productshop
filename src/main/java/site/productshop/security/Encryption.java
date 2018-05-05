package site.productshop.security;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryption {

	public static String hash(String hashedString) throws SecurityException {
	    MessageDigest messageDigest = null;
	    byte[] digest = new byte[0];

	    try {
	        messageDigest = MessageDigest.getInstance("MD5");
	        messageDigest.reset();
	        messageDigest.update(hashedString.getBytes());
	        digest = messageDigest.digest();
	    } catch (NoSuchAlgorithmException e) {
	    	throw new SecurityException(e.getMessage(), e);
	    }

	    BigInteger bigInt = new BigInteger(1, digest);
	    String md5Hex = bigInt.toString(16);

	    while(md5Hex.length() < 32 ){
	        md5Hex = "0" + md5Hex;
	    }

	    return md5Hex;
	}
}
