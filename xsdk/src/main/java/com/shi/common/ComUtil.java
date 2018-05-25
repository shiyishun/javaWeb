package com.shi.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ComUtil {

	public static Map<String, HashMap<String, Object>> loginMap = new HashMap<String, HashMap<String, Object>>();

	public static String toHex(byte[] bytes) {

		final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();
		StringBuilder ret = new StringBuilder(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			ret.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
			ret.append(HEX_DIGITS[bytes[i] & 0x0f]);
		}
		return ret.toString();
	}

	public static String GetGUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	public static String toMd5Str(String str) throws NoSuchAlgorithmException {

		MessageDigest md = MessageDigest.getInstance("md5");
		byte[] md5 = md.digest(str.getBytes());
		return ComUtil.toHex(md5);

	}

	public static String genNo() {
		
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

		String No = df.format(new Date())+((int)(Math.random()*9+1)*1000);
		return No;
	
	}
	
}
