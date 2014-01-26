package com.keanbin.framework.base;

import com.keanbin.framework.utils.LogUtil;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * 基本的服务类
 * 
 * @author keanbin
 * 
 */
public class BaseService extends Service implements BaseLog {

	private String mTag;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	public BaseService() {
		mTag = this.getClass().getName();
	}

	@Override
	public void log(String msg) {
		LogUtil.log(mTag, msg);
	}

	@Override
	public void log(String msg, Throwable e) {
		LogUtil.log(mTag, msg, e);
	}

	@Override
	public void logError(String msg) {
		LogUtil.errorLog(mTag, msg);
	}

	@Override
	public void logError(String msg, Throwable e) {
		LogUtil.errorLog(mTag, msg, e);
	}

	@Override
	public void logError(Throwable e) {
		LogUtil.errorLog(mTag, e);
	}

}
