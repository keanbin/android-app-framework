package com.keanbin.framework.bitmap.util;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

public class Utils {

	// private static final String TAG = Utils.class.getCanonicalName();
	private static final long INITIALCRC = 0xFFFFFFFFFFFFFFFFL;

	private static long[] sCrcTable = new long[256];

	public static byte[] makeKey(String httpUrl) {
		return getBytes(httpUrl);
	}

	public static byte[] getBytes(String in) {
		byte[] result = new byte[in.length() * 2];
		int output = 0;
		for (char ch : in.toCharArray()) {
			result[output++] = (byte) (ch & 0xFF);
			result[output++] = (byte) (ch >> 8);
		}
		return result;
	}

	public static boolean isSameKey(byte[] key, byte[] buffer) {
		int n = key.length;
		if (buffer.length < n) {
			return false;
		}
		for (int i = 0; i < n; ++i) {
			if (key[i] != buffer[i]) {
				return false;
			}
		}
		return true;
	}

	public static final long crc64Long(String in) {
		if (in == null || in.length() == 0) {
			return 0;
		}
		return crc64Long(getBytes(in));
	}

	public static final long crc64Long(byte[] buffer) {
		long crc = INITIALCRC;
		for (int k = 0, n = buffer.length; k < n; ++k) {
			crc = sCrcTable[(((int) crc) ^ buffer[k]) & 0xff] ^ (crc >> 8);
		}
		return crc;
	}

	/**
	 * 获取bitmap的字节大小
	 * 
	 * @param bitmap
	 * @return
	 */
	public static int getBitmapSize(Bitmap bitmap) {
		return bitmap.getRowBytes() * bitmap.getHeight();
	}

	/**
	 * 获取可以使用的缓存目录
	 * 
	 * @param context
	 * @param uniqueName
	 *            目录名称
	 * @return
	 */
	public static File getDiskCacheDir(Context context, String uniqueName) {
		final String cachePath = Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState()) ? getExternalCacheDir(context)
				.getPath() : context.getCacheDir().getPath();

		return new File(cachePath + File.separator + uniqueName);
	}

	/**
	 * 获取程序外部的缓存目录
	 * 
	 * @param context
	 * @return
	 */
	public static File getExternalCacheDir(Context context) {
		final String cacheDir = "/Android/data/" + context.getPackageName()
				+ "/cache/";
		return new File(Environment.getExternalStorageDirectory().getPath()
				+ cacheDir);
	}
}
