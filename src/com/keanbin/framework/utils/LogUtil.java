﻿package com.keanbin.framework.utils;

import android.util.Log;

public class LogUtil {

	private static boolean DEBUG = CommonConfig.DEBUG;

	public static void log(String tag, String msg) {
		log(tag, msg, null);
	}

	public static void log(String tag, String msg, Throwable e) {
		if (DEBUG) {
			if (e != null) {
				Log.e(tag, msg, e);
			} else {
				Log.i(tag, msg);
			}
		}
	}

	public static void errorLog(String tag, String msg) {
		errorLog(tag, msg, null);
	}

	public static void errorLog(String tag, String msg, Throwable e) {
		if (DEBUG) {
			if (e != null) {
				Log.e(tag, msg, e);
			} else {
				Log.e(tag, msg);
			}
		}
	}

	public static void errorLog(String tag, Throwable e) {
		if (DEBUG) {
			if (e != null) {
				Log.e(tag, tag, e);
			}
		}
	}
}
