package com.keanbin.framework;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.keanbin.framework.base.BaseBroadcastReceiver;

/**
 * Application 广播接收器
 * 
 * @author keanbin
 * 
 */
public class ApplicationReceiver extends BaseBroadcastReceiver {

	private static final int PACKAGE_NAME_START_INDEX = 8;

	public ApplicationReceiver(Context context) {
		super(context);

	}

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent == null) {
			return;
		}

		if (intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED)) {
			// 接收广播：新安装一个应用程序包。
			String data = intent.getDataString();

			log("Add app: data = " + data);

			if (data == null || data.length() <= PACKAGE_NAME_START_INDEX) {
				return;
			}

			String packageName = data.substring(PACKAGE_NAME_START_INDEX);
			log("Add app: packageName = " + packageName);

			FrameworkApplication.initInstalledApkMap(context);

		} else if (intent.getAction().equals(Intent.ACTION_PACKAGE_REMOVED)) {
			// 接收广播：卸载了一个应用程序包。
			String data = intent.getDataString();

			log("Remove app: data = " + data);

			if (data == null || data.length() <= PACKAGE_NAME_START_INDEX) {
				return;
			}

			String packageName = data.substring(PACKAGE_NAME_START_INDEX);
			log("Remove app: packageName = " + packageName);

			FrameworkApplication.initInstalledApkMap(context);
		}

	}

	@Override
	public IntentFilter getIntentFilter() {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
		intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
		intentFilter.addDataScheme("package");

		return intentFilter;
	}
}
