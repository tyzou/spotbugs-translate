package com.spotbugs.translate.utils;

import java.security.MessageDigest;

public class Md5Util {
	private static final char[] hexDigits = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	public static final String MD5(String s) {
		try {
			byte[] strTemp = s != null ? s.getBytes() : new byte[0];
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			return _md5str(mdTemp.digest());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static final String _md5str(byte[] md) {
		int j = md.length;
		char[] str = new char[j * 2];
		int k = 0;
		for (int i = 0; i < j; i++) {
			byte byte0 = md[i];
			str[(k++)] = hexDigits[(byte0 >>> 4 & 0xF)];
			str[(k++)] = hexDigits[(byte0 & 0xF)];
		}
		return new String(str);
	}
	  public static void main(String[] args) throws Exception {
	      
	    	 System.err.println(Md5Util.MD5("userName=ncc&pwd=ncc12345678").toUpperCase());
	    }
}
