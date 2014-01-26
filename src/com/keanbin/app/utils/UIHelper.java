package com.keanbin.app.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class UIHelper {

	/**
	 * 启动toActivity
	 * 
	 * @param toActivity
	 *            启动的activity类名
	 * @return void
	 */
	public static void startActivity(Context context,
			Class<? extends Activity> toActivity) {

		if (toActivity == null) {
			return;
		}

		Intent intent = new Intent(context, toActivity);

		context.startActivity(intent);
	}

}
