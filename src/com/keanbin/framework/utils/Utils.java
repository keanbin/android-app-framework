package com.keanbin.framework.utils;

import java.io.File;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.List;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Vibrator;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;

import com.keanbin.framework.entity.InstalledApkInfo;

public class Utils {
	public static final String TAG = Utils.class.getCanonicalName();

	/**
	 * 根据Apk的路径获取包名
	 * 
	 * @param context
	 * @param apkPath
	 * @return
	 */
	public static String getApkPackageName(Context context, String apkPath) {
		PackageManager pm = context.getPackageManager();
		PackageInfo info = pm.getPackageArchiveInfo(apkPath,
				PackageManager.GET_ACTIVITIES);
		if (info == null) {
			return null;
		}

		ApplicationInfo appInfo = info.applicationInfo;
		String appName = pm.getApplicationLabel(appInfo).toString();
		String packageName = appInfo.packageName; // 得到安装包名称
		String version = info.versionName; // 得到版本信息
		// Drawable icon = pm.getApplicationIcon(appInfo);//得到图标信息
		LogUtil.log(TAG, "getApkPackageName(): appName = " + appName
				+ ", packageName = " + packageName + ", version = " + version);

		return packageName;
	}

	/**
	 * 获取本应用的包名
	 * 
	 * @param context
	 * @return
	 */
	public static String getPackageName(Context context) {
		return context.getPackageName();
	}

	/**
	 * 得到当前版本号
	 * 
	 * @param context
	 *            -- 上下文
	 * @return 当前版本号
	 */
	public static int getVersionCode(Context context) {
		PackageManager packageManager = context.getPackageManager();

		try {
			// 根据包名获取版本信息，0表示获取版本信息。
			PackageInfo packageInfo = packageManager.getPackageInfo(
					context.getPackageName(), 0);

			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			LogUtil.errorLog(TAG, e);
		}

		return 0;
	}

	/**
	 * 安装apk
	 * 
	 * @param context
	 * @param apkPath
	 */
	public static void installApk(Context context, String apkPath) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(new File(apkPath)),
				"application/vnd.android.package-archive");
		context.startActivity(intent);

	}

	/**
	 * 判读apk是否已经安装过了
	 * 
	 * @param context
	 * @param apkPackageName
	 * @return
	 */
	public static boolean isInstalledApk(Context context, String apkPackageName) {

		PackageManager packageManager = context.getPackageManager();
		List<PackageInfo> appList = packageManager.getInstalledPackages(0);

		for (int i = 0; i < appList.size(); i++) {
			PackageInfo pinfo = appList.get(i);

			if (pinfo.applicationInfo.packageName.equals(apkPackageName)) {
				return true;
			}

		}

		return false;
	}

	/**
	 * 获取已安装的apk列表
	 * 
	 * @param context
	 * @return
	 */
	public static HashMap<String, InstalledApkInfo> getInstalledApkMap(
			Context context) {

		HashMap<String, InstalledApkInfo> installedApkMap = new HashMap<String, InstalledApkInfo>();

		PackageManager packageManager = context.getPackageManager();
		List<PackageInfo> appList = packageManager.getInstalledPackages(0);

		for (int i = 0; i < appList.size(); i++) {
			PackageInfo pinfo = appList.get(i);
			if (pinfo != null) {
				ApplicationInfo appInfo = pinfo.applicationInfo;

				InstalledApkInfo installedApkInfo = new InstalledApkInfo();
				installedApkInfo.setAppName(packageManager.getApplicationLabel(
						appInfo).toString());
				installedApkInfo.setPackageName(appInfo.packageName);
				installedApkInfo.setVersionName(pinfo.versionName);
				installedApkInfo.setVersionCode(pinfo.versionCode);

				//LogUtil.log(TAG, installedApkInfo.toString());
				
				installedApkMap.put(appInfo.packageName, installedApkInfo);
			}

		}

		return installedApkMap;
	}

	/**
	 * 一次震动
	 * 
	 * @param activity
	 * @param milliseconds
	 *            震动多长时间，单位：毫秒。
	 * @return
	 */
	public static void vibrate(final Context context, long milliseconds) {
		Vibrator vib = (Vibrator) context
				.getSystemService(Service.VIBRATOR_SERVICE);
		vib.vibrate(milliseconds);
	}

	/**
	 * 重复震动
	 * 
	 * @param activity
	 * @param pattern
	 *            自定义震动模式 。数组中数字的含义依次是[静止时长，震动时长]时长的单位是毫秒
	 * @param isRepeat
	 *            是否反复震动
	 * @return
	 */
	public static void vibrate(final Context context, long[] pattern,
			boolean isRepeat) {
		Vibrator vib = (Vibrator) context
				.getSystemService(Service.VIBRATOR_SERVICE);
		vib.vibrate(pattern, isRepeat ? 1 : -1);
	}

	/**
	 * 获取定义的尺寸，并转为int类型
	 * 
	 * @return
	 */
	public static int getDimensionToInt(Context context, int resId) {
		float dimen = context.getResources().getDimension(resId);

		return (int) dimen;
	}

	/**
	 * 获取mac地址
	 * 
	 * @param context
	 * @return
	 */
	public static String getMacAddress(Context context) {
		WifiManager wifi = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);

		WifiInfo info = wifi.getConnectionInfo();

		return info.getMacAddress();
	}

	/**
	 * 获取IMEI号
	 * 
	 * @param context
	 * @return
	 */
	public static String getIMEI(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getDeviceId();
	}

	/**
	 * 获取AndroidID
	 * 
	 * @param context
	 * @return
	 */
	public static String getAndroidID(Context context) {
		String androidId = Secure.getString(context.getContentResolver(),
				Secure.ANDROID_ID);
		return androidId;
	}

	/**
	 * 判断指定的类名是否是当前顶部Activity。
	 * 
	 * @return 是，返回ture；否，返回false。
	 */
	public static boolean isTopActivity(Context context,
			String classCanonicalName) {

		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		ComponentName cn = activityManager.getRunningTasks(1).get(0).topActivity;
		String className = cn.getClassName();

		return className.equals(classCanonicalName);
	}

	/**
	 * MD5加密，32位
	 * 
	 * @param str
	 * @return
	 */
	public static String toMD5(String str) {
		if (str == null || str.equals("")) {
			return null;
		}

		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			return null;
		}
		char[] charArray = str.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++) {
			byteArray[i] = (byte) charArray[i];
		}
		byte[] md5Bytes = md5.digest(byteArray);

		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}
}
