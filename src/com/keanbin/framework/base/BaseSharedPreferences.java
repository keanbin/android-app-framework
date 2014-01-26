package com.keanbin.framework.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 基本的SharedPreferences操作类
 * 
 * @author keanbin
 * 
 */
public class BaseSharedPreferences extends BaseClass {

	protected Context mContext;
	protected String mSPName;

	public BaseSharedPreferences(Context context) {
		mContext = context;
	}

	/**
	 * 从SharedPreferences文件spName中取得键keyName对应的值
	 * 
	 * @param mContext
	 * @param mSPName
	 *            SharedPreferences文件名
	 * @param keyName
	 *            键名
	 * @return 返回键keyName对应的值，如果没有取到值，就返回null
	 */
	public String getPreferenceString(String keyName) {

		if (mContext != null) {
			SharedPreferences sp = mContext.getSharedPreferences(mSPName,
					Context.MODE_PRIVATE);
			return sp.getString(keyName, null);
		} else {
			return null;
		}
	}

	/**
	 * 从SharedPreferences文件spName中取得键keyName对应的值
	 * 
	 * @param mContext
	 * @param mSPName
	 *            SharedPreferences文件名
	 * @param keyName
	 *            键名
	 * @return 返回键keyName对应的值，如果没有取到值，就返回false
	 */
	public boolean getPreferenceBoolean(String keyName) {
		if (mContext != null) {
			SharedPreferences sp = mContext.getSharedPreferences(mSPName,
					Context.MODE_PRIVATE);
			return sp.getBoolean(keyName, false);
		} else {
			return false;
		}
	}

	/**
	 * 从SharedPreferences文件spName中取得键keyName对应的值
	 * 
	 * @param mContext
	 * @param mSPName
	 *            SharedPreferences文件名
	 * @param keyName
	 *            键名
	 * @return 返回键keyName对应的值，如果没有取到值，就返回0.0F
	 */
	public Float getPreferenceFloat(String keyName) {
		if (mContext != null) {
			SharedPreferences sp = mContext.getSharedPreferences(mSPName,
					Context.MODE_PRIVATE);
			return sp.getFloat(keyName, 0.0F);
		} else {
			return 0.0F;
		}
	}

	/**
	 * 从SharedPreferences文件spName中取得键keyName对应的值
	 * 
	 * @param mContext
	 * @param mSPName
	 *            SharedPreferences文件名
	 * @param keyName
	 *            键名
	 * @return 返回键keyName对应的值，如果没有取到值，就返回0
	 */
	public int getPreferenceInt(String keyName) {
		if (mContext != null) {
			SharedPreferences sp = mContext.getSharedPreferences(mSPName,
					Context.MODE_PRIVATE);
			return sp.getInt(keyName, 0);
		} else {
			return 0;
		}
	}

	/**
	 * 从SharedPreferences文件spName中取得键keyName对应的值
	 * 
	 * @param mContext
	 * @param mSPName
	 *            SharedPreferences文件名
	 * @param keyName
	 *            键名
	 * @return 返回键keyName对应的值，如果没有取到值，就返回0
	 */
	public Long getPreferenceLong(String keyName) {
		if (mContext != null) {
			SharedPreferences sp = mContext.getSharedPreferences(mSPName,
					Context.MODE_PRIVATE);
			return sp.getLong(keyName, 0);
		} else {
			return (long) 0;
		}
	}

	/**
	 * 从SharedPreferences文件spName中取得键keyName对应的值
	 * 
	 * @param mContext
	 * @param mSPName
	 *            SharedPreferences文件名
	 * @param keyName
	 *            键名
	 * @param defValue
	 *            默认值，没有取到值时，返回它
	 * @return 返回键keyName对应的值，如果没有取到值，就返回defValue
	 */
	public String getPreferenceString(String keyName, String defValue) {
		if (mContext != null) {
			SharedPreferences sp = mContext.getSharedPreferences(mSPName,
					Context.MODE_PRIVATE);
			return sp.getString(keyName, defValue);
		} else {
			return defValue;
		}
	}

	/**
	 * 从SharedPreferences文件spName中取得键keyName对应的值
	 * 
	 * @param mContext
	 * @param mSPName
	 *            SharedPreferences文件名
	 * @param keyName
	 *            键名
	 * @param defValue
	 *            默认值，没有取到值时，返回它
	 * @return 返回键keyName对应的值，如果没有取到值，就返回defValue
	 */
	public boolean getPreferenceBoolean(String keyName, boolean defValue) {
		if (mContext != null) {
			SharedPreferences sp = mContext.getSharedPreferences(mSPName,
					Context.MODE_PRIVATE);
			return sp.getBoolean(keyName, defValue);
		} else {
			return defValue;
		}
	}

	/**
	 * 从SharedPreferences文件spName中取得键keyName对应的值
	 * 
	 * @param mContext
	 * @param mSPName
	 *            SharedPreferences文件名
	 * @param keyName
	 *            键名
	 * @param defValue
	 *            默认值，没有取到值时，返回它
	 * @return 返回键keyName对应的值，如果没有取到值，就返回defValue
	 */
	public Float getPreferenceFloat(String keyName, float defValue) {
		if (mContext != null) {
			SharedPreferences sp = mContext.getSharedPreferences(mSPName,
					Context.MODE_PRIVATE);
			return sp.getFloat(keyName, defValue);
		} else {
			return defValue;
		}
	}

	/**
	 * 从SharedPreferences文件spName中取得键keyName对应的值
	 * 
	 * @param mContext
	 * @param mSPName
	 *            SharedPreferences文件名
	 * @param keyName
	 *            键名
	 * @param defValue
	 *            默认值，没有取到值时，返回它
	 * @return 返回键keyName对应的值，如果没有取到值，就返回defValue
	 */
	public int getPreferenceInt(String keyName, int defValue) {
		if (mContext != null) {
			SharedPreferences sp = mContext.getSharedPreferences(mSPName,
					Context.MODE_PRIVATE);
			return sp.getInt(keyName, defValue);
		} else {
			return defValue;
		}
	}

	/**
	 * 从SharedPreferences文件spName中取得键keyName对应的值
	 * 
	 * @param mContext
	 * @param mSPName
	 *            SharedPreferences文件名
	 * @param keyName
	 *            键名
	 * @param defValue
	 *            默认值，没有取到值时，返回它
	 * @return 返回键keyName对应的值，如果没有取到值，就返回defValue
	 */
	public Long getPreferenceLong(String keyName, long defValue) {
		if (mContext != null) {
			SharedPreferences sp = mContext.getSharedPreferences(mSPName,
					Context.MODE_PRIVATE);
			return sp.getLong(keyName, defValue);
		} else {
			return defValue;
		}
	}

	/**
	 * 把值value设置到SharedPreferences文件spName中的键keyName中
	 * 
	 * @param mContext
	 * @param mSPName
	 *            SharedPreferences文件名
	 * @param keyName
	 *            键名
	 * @param value
	 *            设置的值
	 * @return void
	 */
	public void setPreferenceBoolean(String keyName, Boolean value) {
		if (mContext != null) {
			SharedPreferences sp = mContext.getSharedPreferences(mSPName,
					Context.MODE_PRIVATE);
			Editor editor = sp.edit();
			editor.putBoolean(keyName, value);
			editor.commit();
		}
	}

	/**
	 * 把值value设置到SharedPreferences文件spName中的键keyName中
	 * 
	 * @param mContext
	 * @param mSPName
	 *            SharedPreferences文件名
	 * @param keyName
	 *            键名
	 * @param value
	 *            设置的值
	 * @return void
	 */
	public void setPreferenceFloat(String keyName, float value) {
		if (mContext != null) {
			SharedPreferences sp = mContext.getSharedPreferences(mSPName,
					Context.MODE_PRIVATE);
			Editor editor = sp.edit();
			editor.putFloat(keyName, value);
			editor.commit();
		}
	}

	/**
	 * 把值value设置到SharedPreferences文件spName中的键keyName中
	 * 
	 * @param mContext
	 * @param mSPName
	 *            SharedPreferences文件名
	 * @param keyName
	 *            键名
	 * @param value
	 *            设置的值
	 * @return void
	 */
	public void setPreferenceInt(String keyName, int value) {
		if (mContext != null) {
			SharedPreferences sp = mContext.getSharedPreferences(mSPName,
					Context.MODE_PRIVATE);
			Editor editor = sp.edit();
			editor.putInt(keyName, value);
			editor.commit();
		}
	}

	/**
	 * 把值value设置到SharedPreferences文件spName中的键keyName中
	 * 
	 * @param mContext
	 * @param mSPName
	 *            SharedPreferences文件名
	 * @param keyName
	 *            键名
	 * @param value
	 *            设置的值
	 * @return void
	 */
	public void setPreferenceLong(String keyName, long value) {
		if (mContext != null) {
			SharedPreferences sp = mContext.getSharedPreferences(mSPName,
					Context.MODE_PRIVATE);
			Editor editor = sp.edit();
			editor.putLong(keyName, value);
			editor.commit();
		}
	}

	/**
	 * 把值value设置到SharedPreferences文件spName中的键keyName中
	 * 
	 * @param mContext
	 * @param mSPName
	 *            SharedPreferences文件名
	 * @param keyName
	 *            键名
	 * @param value
	 *            设置的值
	 * @return void
	 */
	public void setPreferenceString(String keyName, String value) {
		if (mContext != null) {
			SharedPreferences sp = mContext.getSharedPreferences(mSPName,
					Context.MODE_PRIVATE);
			Editor editor = sp.edit();
			editor.putString(keyName, value);
			editor.commit();
		}
	}
}