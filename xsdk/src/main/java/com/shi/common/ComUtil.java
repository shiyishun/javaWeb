package com.shi.common;

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
}
