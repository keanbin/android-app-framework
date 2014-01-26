package com.keanbin.framework.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * FormatCheck 格式验证
 * 
 * @author kevin
 */
public class FormatCheck {

	/**
	 * 手机号码格式验证
	 * 
	 * @param mobiles
	 *            手机号码
	 * @return 格式正确返回 true，否则返回 false
	 */
	public static boolean isPhoneNumber(String mobiles) {
		Pattern p = Pattern.compile("^1[0-9]{10}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	/**
	 * 格式化手机号码，格式：前三个数字后加“-”，前七个数字后加“-”。
	 * 
	 * @param mobiles
	 *            手机号码
	 * @return 格式化后的字符串
	 */
	public static String formatPhoneNumber(String mobiles) {
		Pattern p = Pattern.compile("^[0-9]{3}-$");
		Matcher m = p.matcher(mobiles);

		if (m.matches()) {
			mobiles = mobiles.substring(0, mobiles.length() - 1);
			return mobiles;
		}

		p = Pattern.compile("^[0-9]{3}-[0-9]{4}-$");
		m = p.matcher(mobiles);

		if (m.matches()) {
			mobiles = mobiles.substring(0, mobiles.length() - 1);
			return mobiles;
		}

		p = Pattern.compile("^[0-9]{4}$");
		m = p.matcher(mobiles);

		if (m.matches()) {
			mobiles = StringUtil.insertString(mobiles, 4, "-");
			return mobiles;
		}

		p = Pattern.compile("^[0-9]{3}-[0-9]{5}$");
		m = p.matcher(mobiles);

		if (m.matches()) {
			mobiles = StringUtil.insertString(mobiles, 9, "-");
			return mobiles;
		}

		p = Pattern.compile("^[0-9]{11}$");
		m = p.matcher(mobiles);

		if (m.matches()) {
			mobiles = StringUtil.insertString(mobiles, 4, "-");
			mobiles = StringUtil.insertString(mobiles, 9, "-");
			return mobiles;
		}

		return mobiles;
	}

	/**
	 * 过滤非法字符
	 * 
	 * @param str
	 * @return
	 */
	public static String StringFilter(String str) {
		// 只允许字母和数字
		// String regEx = "[^a-zA-Z0-9]";
		// 清除掉所有特殊字符
		// String regEx =
		// "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……& amp;*（）——+|{}【】‘；：”“’。，、？]";
		String regEx = "^[^[-;/*]]+$";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

	/**
	 * 是否全是数字
	 * 
	 * @param str
	 *            字符串
	 * @return 格式正确返回 true，否则返回 false
	 */
	public static boolean isNumber(String str) {
		Pattern p = Pattern.compile("^[0-9]+$");
		Matcher m = p.matcher(str);
		return m.matches();
	}

	/**
	 * 判断是否是位数为参数的数字
	 * 
	 * @param str
	 *            检验的字符串
	 * @param byteNumber
	 *            数字的个数
	 * @return 格式正确返回 true，否则返回 false
	 */
	public static boolean isNumber(String str, int byteNumber) {
		String formatStr = "[0-9]{" + byteNumber + "}$";
		Pattern p = Pattern.compile(formatStr);
		Matcher m = p.matcher(str);
		return m.matches();
	}
}
