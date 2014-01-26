package com.keanbin.framework.base;

import com.keanbin.framework.utils.LogUtil;

/**
 * 本框架中基本的适配器类
 * 
 * @author keanbin
 * 
 */
public abstract class BaseAdapter extends android.widget.BaseAdapter implements
		BaseLog {

	private String mTag;

	public BaseAdapter() {
		super();

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
