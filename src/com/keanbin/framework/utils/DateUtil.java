package com.keanbin.framework.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.text.format.DateUtils;
import android.text.format.Time;

import com.keanbin.app.R;

public class DateUtil {

	/**
	 * 格式化时间
	 * 
	 * @param context
	 * @param when
	 * @param fullFormat
	 * @return
	 */
	public static String formatTime(Context context, long time,
			boolean fullFormat) {
		Time then = new Time();
		then.set(time);
		Time now = new Time();
		now.setToNow();

		int format_flags = DateUtils.FORMAT_NO_NOON_MIDNIGHT
				| DateUtils.FORMAT_ABBREV_ALL | DateUtils.FORMAT_CAP_AMPM;

		if (then.year != now.year) {
			format_flags |= DateUtils.FORMAT_SHOW_YEAR
					| DateUtils.FORMAT_SHOW_DATE;
		} else if (then.month != now.month || then.yearDay != now.yearDay) {
			format_flags |= DateUtils.FORMAT_SHOW_DATE;
		} else {
			format_flags |= DateUtils.FORMAT_SHOW_TIME;
		}

		if (fullFormat) {
			format_flags |= (DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_TIME);
		}

		return DateUtils.formatDateTime(context, time, format_flags);
	}

	/**
	 * 格式化时间。格式：日期和时间都显示“年/月/日 小时:分钟”,只显示日期“年/月/日”或者“月/日”或者“小时:分钟”。
	 * 
	 * @param context
	 * @param time
	 * @param isDateAndTime
	 *            日期和时间是否都显示
	 * @return
	 */
	public static String formatTimeTwo(Context context, long time,
			boolean isDateAndTime) {
		Time then = new Time();
		then.set(time);
		Time now = new Time();
		now.setToNow();

		int month = then.month + 1;

		String str = null;

		if (isDateAndTime) {
			String format = context.getString(R.string.format_DateAndTime);
			str = String.format(format, then.year, month, then.monthDay,
					then.hour, then.minute);

			return str;
		}

		if (then.year != now.year) {
			String format = context.getString(R.string.format_Date);
			str = String.format(format, then.year, month, then.monthDay);

		} else if (then.month != now.month || then.yearDay != now.yearDay) {
			String format = context.getString(R.string.format_DateNoYear);
			str = String.format(format, month, then.monthDay);
		} else {
			String format = context.getString(R.string.format_TIME);
			str = String.format(format, then.hour, then.minute);
		}

		return str;
	}

	/**
	 * 毫秒级的长整型时间转换成格式为“yyyy-MM-dd HH:mm:ss”的时间字符串
	 * 
	 * @param millisecond
	 * @return
	 */
	public static String formateMillisecond(long millisecond) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.getDefault());
		return sdf.format(new Date(millisecond));
	}

	/**
	 * 将长整型的时间转换成"时:分:秒"格式的字符串的时间
	 * 
	 * @param duringTime
	 *            长整型的时间，单位：秒
	 * @return "时:分:秒"格式的字符串
	 */
	public static String formateDuringTime(long duringTime) {
		int hh = 0;
		int mm = 0;
		int ss = (int) (duringTime % 60);

		duringTime = duringTime / 60;
		mm = (int) (duringTime % 60);
		hh = (int) (duringTime / 60);

		String result = intToString(hh) + ":" + intToString(mm) + ":"
				+ intToString(ss);
		return result;
	}

	/**
	 * 将整形的时间转换成字符串格式的时间
	 * 
	 * @param i
	 *            整形的时间
	 * @return 字符串格式的时间
	 */
	private static String intToString(int i) {
		String result = "";
		if (i < Integer.valueOf("10")) {
			result += "0" + i;
		} else {
			result += "" + i;
		}
		return result;
	}

	/**
	 * 传入的日期是否早于当前日期
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月 ， 范围 0~11。比如1月，是0。
	 * @param date
	 *            日
	 * @return 早于当前日期返回 true，否则返回false。
	 */
	public static boolean isBeforeDate(int year, int month, int date) {
		final Calendar c = Calendar.getInstance();

		int currentYear = c.get(Calendar.YEAR);
		int currentmMonth = c.get(Calendar.MONTH);
		int currentmDay = c.get(Calendar.DAY_OF_MONTH);

		if (year < currentYear) {
			return true;
		} else if (year > currentYear) {
			return false;
		}

		if (month < currentmMonth) {
			return true;
		} else if (month > currentmMonth) {
			return false;
		}

		if (date < currentmDay) {
			return true;
		}

		return false;
	}
}
