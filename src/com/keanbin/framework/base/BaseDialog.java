package com.keanbin.framework.base;

import android.app.Dialog;
import android.content.Context;

import com.keanbin.framework.utils.LogUtil;

public class BaseDialog extends Dialog implements BaseLog {

	protected Context mContext;
	protected String mTag;

	public BaseDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		init(context);
	}

	public BaseDialog(Context context, int theme) {
		super(context, theme);
		init(context);
	}

	public BaseDialog(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context) {
		mTag = this.getClass().getName();
		mContext = context;
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
