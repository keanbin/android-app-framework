package com.keanbin.framework.utils;

import java.io.UnsupportedEncodingException;

public class StringUtil {

	private static final String TAG = StringUtil.class.getCanonicalName();

	/**
	 * 字符串转整数
	 * 
	 * @param obj
	 * @return 转换异常返回 0
	 */
	public static int toInt(String obj) {

		try {
			return Integer.parseInt(obj);
		} catch (Exception e) {
		}

		return 0;
	}

	/**
	 * 字符串转长整数
	 * 
	 * @param obj
	 * @return 转换异常返回 0
	 */
	public static long toLong(String obj) {
		try {
			return Long.parseLong(obj);
		} catch (Exception e) {
		}
		return 0;
	}

	/**
	 * 字符串转布尔值
	 * 
	 * @param b
	 * @return 转换异常返回 false
	 */
	public static boolean toBool(String b) {
		try {
			return Boolean.parseBoolean(b);
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * 字节转为字符串
	 * 
	 * @param date
	 * @param charsetName
	 *            指定字符集
	 * @return
	 */
	public static String byteToString(byte[] date, String charsetName) {

		if (date == null) {
			return null;
		}

		if (charsetName == null) {
			return new String(date);
		} else {
			try {
				return new String(date, charsetName);
			} catch (UnsupportedEncodingException e) {
				LogUtil.errorLog(TAG, e);

				return new String(date);
			}
		}
	}

	/**
	 * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串。 若输入字符串为null或空白串，返回true。
	 * 
	 * @param input
	 * @return boolean
	 */
	public static boolean isEmpty(String input) {
		if (input == null || "".equals(input))
			return true;

		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
				return false;
			}
		}
		return true;
	}

	/**
	 * @Description 在字符串的指位置插入一个字符串
	 * @param sourceStr
	 *            被插入的字符串
	 * @param position
	 *            插入的位置
	 * @param insertStr
	 *            要插入的字符串
	 * @return 插入后的字符串
	 */
	public static String insertString(String sourceStr, int position,
			String insertStr) {
		int i = position - 1;
		String newString = sourceStr.substring(0, i) + insertStr
				+ sourceStr.substring(i, sourceStr.length());

		return newString;
	}

	/**
	 * @Description 从字符串中截取指定字符串之前的子字符串
	 * @param sourceStr
	 *            被插入的字符串
	 * @param endString
	 *            指定的字符串
	 * @return 如果字符串中没有指定的字符串，那么返回原字符串，如果有，就返回截取指定字符串之前的子字符串。
	 */
	public static String subStringEndString(String sourceStr, String endString) {

		int position = sourceStr.indexOf(endString);
		if (position == -1) {
			return sourceStr;
		}

		String newString = sourceStr.substring(0, position);
		return newString;
	}
}
