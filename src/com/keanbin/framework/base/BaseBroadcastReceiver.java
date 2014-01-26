package com.keanbin.framework.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;

import com.keanbin.framework.utils.LogUtil;

public abstract class BaseBroadcastReceiver extends BroadcastReceiver implements
		BaseLog {

	private String TAG;
	private Context mContext;
	private boolean isRegisterBroadReceiver;

	public BaseBroadcastReceiver(Context context) {
		super();

		TAG = this.getClass().getName();
		mContext = context;
		isRegisterBroadReceiver = false;
	}

	@Override
	public void log(String msg) {
		LogUtil.log(TAG, msg);
	}

	@Override
	public void log(String msg, Throwable e) {
		LogUtil.log(TAG, msg, e);
	}

	@Override
	public void logError(String msg) {
		LogUtil.errorLog(TAG, msg);
	}

	@Override
	public void logError(String msg, Throwable e) {
		LogUtil.errorLog(TAG, msg, e);
	}

	@Override
	public void logError(Throwable e) {
		LogUtil.errorLog(TAG, e);
	}

	/**
	 * 注册广播
	 * 
	 * @return
	 */
	public void register() {
		IntentFilter intentFilter = getIntentFilter();
		mContext.registerReceiver(this, intentFilter);
		isRegisterBroadReceiver = true;
	}

	/**
	 * 注销广播
	 * 
	 * @return
	 */
	public void unregister() {
		if (isRegisterBroadReceiver) {
			mContext.unregisterReceiver(this);
			isRegisterBroadReceiver = false;
		}
	}

	public abstract IntentFilter getIntentFilter();
}
