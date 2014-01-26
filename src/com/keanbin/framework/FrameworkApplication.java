package com.keanbin.framework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.keanbin.framework.entity.InstalledApkInfo;
import com.keanbin.framework.http.frame.HttpManager;
import com.keanbin.framework.utils.LogUtil;
import com.keanbin.framework.utils.StringUtil;
import com.keanbin.framework.utils.Utils;

/**
 * @ClassName FrameworkApplication
 * @Description 本应用的Application，主要用来定义本应用的全局变量。
 * @author kevin
 */
public class FrameworkApplication extends Application {

	private static final String TAG = FrameworkApplication.class
			.getCanonicalName();

	private Context mContext;
	private HttpManager mHttpManager;
	private List<Activity> mActivityList; // 在栈中的Activity列表
	private HashMap<String, InstalledApkInfo> mInstalledApkMap; // 已经安装的apkMap列表
	private ApplicationReceiver mApplicationReceiver; // 广播接收器

	@Override
	public void onCreate() {
		super.onCreate();
		log("onCreate() ...");

		mContext = this;
		mHttpManager = HttpManager.getInstance(mContext);
		mActivityList = new ArrayList<Activity>();

		initInstalledApkMap();
		registerApplicationReceiver();
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		log("onTerminate() ...");

		mInstalledApkMap = null;
		
		unregisterApplicationReceiver();
	}

	public HttpManager getHttpManager() {
		if (mHttpManager == null) {
			mHttpManager = HttpManager.getInstance(mContext);
		}

		return mHttpManager;
	}

	private void log(String msg) {
		LogUtil.log(TAG, msg);
	}

	/**
	 * 注册广播接收器，接收Http请求返回的结果
	 */
	private void registerApplicationReceiver() {
		unregisterApplicationReceiver();

		mApplicationReceiver = new ApplicationReceiver(mContext);
		mApplicationReceiver.register();
	}

	/**
	 * 注销广播接收器
	 */
	private void unregisterApplicationReceiver() {
		if (mApplicationReceiver != null) {
			mApplicationReceiver.unregister();
			mApplicationReceiver = null;
		}
	}

	/**
	 * 页面创建
	 * 
	 * @param activity
	 */
	public void onActivityCreate(Activity activity) {
		mActivityList.add(activity);
	}

	/**
	 * 页面销毁
	 * 
	 * @param activity
	 */
	public void onActivityDestroy(Activity activity) {
		mActivityList.remove(activity);
	}

	/**
	 * 判断页面是否在页面列表中
	 * 
	 * @param classCanonicalName
	 * @return
	 */
	public boolean isInActivityList(String classCanonicalName) {
		if (StringUtil.isEmpty(classCanonicalName)) {
			return false;
		}

		for (int i = 0; i < mActivityList.size(); i++) {
			Activity activity = mActivityList.get(i);
			if (classCanonicalName.equals(activity.getClass()
					.getCanonicalName())) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 初始化已安装APK MAP列表
	 */
	public void initInstalledApkMap() {
		mInstalledApkMap = Utils.getInstalledApkMap(mContext);
	}

	/**
	 * 判断指定应用是否已经安装
	 * 
	 * @param apkPackageName
	 * @return
	 */
	public boolean isInstalledApk(String apkPackageName) {
		if (StringUtil.isEmpty(apkPackageName)) {
			return false;
		}

		if (mInstalledApkMap == null) {
			initInstalledApkMap();
		}

		InstalledApkInfo installedApkInfo = mInstalledApkMap
				.get(apkPackageName);
		if (installedApkInfo != null
				&& apkPackageName.equals(installedApkInfo.getPackageName())) {
			return true;
		}

		return false;
	}

	/**
	 * 判断指定的类名是否是已经存在的Activity。
	 * 
	 * @return 是，返回ture；否，返回false。
	 */
	public static boolean isExistActivity(Context context,
			String classCanonicalName) {
		if (context == null) {
			return false;
		}

		FrameworkApplication application = (FrameworkApplication) context
				.getApplicationContext();
		return application.isInActivityList(classCanonicalName);
	}

	/**
	 * 判读apk是否已经安装过了
	 * 
	 * @param context
	 * @param apkPackageName
	 * @return
	 */
	public static boolean isInstalledApk(Context context, String apkPackageName) {
		if (context == null) {
			return false;
		}

		FrameworkApplication application = (FrameworkApplication) context
				.getApplicationContext();

		return application.isInstalledApk(apkPackageName);
	}

	/**
	 * 初始化已安装APK MAP列表
	 * 
	 * @param context
	 */
	public static void initInstalledApkMap(Context context) {
		if (context == null) {
			return;
		}

		FrameworkApplication application = (FrameworkApplication) context
				.getApplicationContext();
		application.initInstalledApkMap();
	}
}
