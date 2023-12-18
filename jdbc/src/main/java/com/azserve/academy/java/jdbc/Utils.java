package com.azserve.academy.java.jdbc;

public class Utils {

	public static String padLeft(final String s, final int len, final char pad) {
		final int sLen = s.length();
		if (len == sLen) {
			return s;
		}
		if (sLen > len) {
			return s.substring(sLen - len);
		}
		final int diff = len - sLen;
		String result = s;
		for (int i = 0; i < diff; i++) {
			result = pad + result;
		}
		return result;
	}
}
