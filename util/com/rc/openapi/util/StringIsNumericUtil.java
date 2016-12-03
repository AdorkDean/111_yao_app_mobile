package com.rc.openapi.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringIsNumericUtil {

	/**
	 * 数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 数字及金额
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumericOrDouble(String str) {
		Pattern pattern = Pattern.compile("[-+]?\\d+(\\.\\d+)?");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
	
	public static boolean isPhone(String str){
		Pattern pattern = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
	public static boolean isZipCode(String zipCode) {
		Pattern pattern = Pattern.compile("^[1-9]\\d{5}(?!\\d)$");
		Matcher isNum = pattern.matcher(zipCode);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
	

	public static void main(String[] args) {
		System.out.println(isZipCode("099990"));
	}
}
