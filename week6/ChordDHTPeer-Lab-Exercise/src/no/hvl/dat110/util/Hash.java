package no.hvl.dat110.util;

/**
 * exercise/demo purpose in dat110
 * @author tdoy
 *
 */

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash { 
	
	public static BigInteger hashOf(String entity) throws NoSuchAlgorithmException{		
		
		BigInteger hashint = null;
		
		// Task: Hash a given string using MD5 and return the result as a BigInteger.
		
		// we use MD5 with 128 bits digest
		MessageDigest m = MessageDigest.getInstance("MD5");
		// compute the hash of the input 'entity'
		byte[] hash = m.digest(entity.getBytes(StandardCharsets.UTF_8));
		// convert the hash into hex format
		String hex = toHex(hash);
		// convert the hex into BigInteger
		hashint = new BigInteger(hex, 16);
		// return the BigInteger
		return hashint;
	}
	
	public static BigInteger addressSize() throws NoSuchAlgorithmException {
		
		// Task: compute the address size of MD5
		
		// get the digest length (Note: make this method independent of the class variables)
		MessageDigest m = MessageDigest.getInstance("MD5");
		int size = m.getDigestLength();
		// compute the number of bits = digest length * 8
		int bits = size*8;
		// compute the address size = 2 ^ number of bits
		BigInteger mod = new BigInteger("2");
		mod = mod.pow(bits);
		
		return mod;
	}
	
	public static String toHex(byte[] digest) {
		StringBuilder strbuilder = new StringBuilder();
		for(byte b : digest) {
			strbuilder.append(String.format("%02x", b&0xff));
		}
		return strbuilder.toString();
	}

}
