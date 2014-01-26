package com.keanbin.app.SharedPreferences;

import android.content.Context;

import com.keanbin.framework.base.BaseSharedPreferences;
import com.keanbin.framework.utils.Utils;

/**
 * 杂乱零星的信息的配置文件操作类
 * 
 * @author keanbin
 * 
 */
public class SundriesInfoSharePre extends BaseSharedPreferences {

	private static final String SHARE_PREFERENCES_NAME = "_SundriesInfo";

	private static final String KEY_NAME_LAST_OPEN_VERSION_CODE = "LastOpenVersionCode"; // 上一次打开的版本号

	private static SundriesInfoSharePre mInstance;

	private SundriesInfoSharePre(Context context) {
		super(context);
		mSPName = SHARE_PREFERENCES_NAME;
	}

	public static SundriesInfoSharePre getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new SundriesInfoSharePre(context);
		}

		return mInstance;
	}

	/**
	 * 获取上一次打开的版本号
	 * 
	 * @return
	 */
	public int getLastOpenVersionCode() {
		return getPreferenceInt(KEY_NAME_LAST_OPEN_VERSION_CODE, -1);
	}

	/**
	 * 设置这一次打开的版本号
	 * 
	 * @param unreadMsgNumber
	 */
	public void setLastOpenVersionCode() {
		int lastOpenVersionCode = Utils.getVersionCode(mContext);

		setPreferenceInt(KEY_NAME_LAST_OPEN_VERSION_CODE, lastOpenVersionCode);
	}

	/**
	 * APP是否是第一次启动
	 * 
	 * @return 第一次启动返回true，否则返回false。
	 */
	public boolean isAppFirstStart() {
		int currentVirsionCode = Utils.getVersionCode(mContext);
		int lastOpenVersionCode = getLastOpenVersionCode();

		if (currentVirsionCode == lastOpenVersionCode) {
			return false;
		}

		return true;

	}
}
