package com.keanbin.framework.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtil {

	private static final String TAG = JsonUtil.class.getCanonicalName();

	/**
	 * 从JSONObject获取指定key的json数组对象
	 * 
	 * @param obj
	 *            Bundle对象
	 * @param name
	 *            要获取的key的名称
	 * @return json数组对象
	 * @throws JSONException
	 */
	public static JSONArray getJSONArray(JSONObject obj, String name)
			throws JSONException {
		if (obj == null || name == null) {
			return null;
		}

		if (obj.isNull(name)) {
			LogUtil.log(TAG, "getStringFromJSONObject : obj.isNull(" + name
					+ ") = true");
			return null;
		}

		JSONArray valueJSONArray = obj.getJSONArray(name);
		return valueJSONArray;

	}

	/**
	 * 从JSONObject获取指定key的json对象
	 * 
	 * @param obj
	 *            Bundle对象
	 * @param name
	 *            要获取的key的名称
	 * @return json对象
	 * @throws JSONException
	 */
	public static JSONObject getJSONObject(JSONObject obj, String name)
			throws JSONException {
		if (obj == null || name == null) {
			return null;
		}

		if (obj.isNull(name)) {
			LogUtil.log(TAG, "getStringFromJSONObject : obj.isNull(" + name
					+ ") = true");
			return null;
		}

		JSONObject valueJSONObject = obj.getJSONObject(name);
		return valueJSONObject;

	}

	/**
	 * 从JSONObject获取指定key的值
	 * 
	 * @param obj
	 *            Bundle对象
	 * @param name
	 *            要获取的key的名称
	 * @return 从JSONObject获取的值
	 * @throws JSONException
	 */
	public static String getString(JSONObject obj, String name)
			throws JSONException {
		if (obj == null || name == null) {
			return null;
		}

		if (obj.isNull(name)) {
			LogUtil.log(TAG, "getStringFromJSONObject : obj.isNull(" + name
					+ ") = true");
			return null;
		}

		String value = obj.getString(name);
		return value;

	}

	/**
	 * 从JSONObject获取指定key的值
	 * 
	 * @param obj
	 *            Bundle对象
	 * @param name
	 *            要获取的key的名称
	 * @return 从JSONObject获取的值
	 * @throws JSONException
	 */
	public static int getInt(JSONObject obj, String name) throws JSONException {
		return getInt(obj, name, 0);

	}

	/**
	 * 从JSONObject获取指定key的值
	 * 
	 * @param obj
	 *            Bundle对象
	 * @param name
	 *            要获取的key的名称
	 * @param defaultValue
	 *            默认值
	 * @return 从JSONObject获取的值
	 * @throws JSONException
	 */
	public static int getInt(JSONObject obj, String name, int defaultValue)
			throws JSONException {
		if (obj == null || name == null) {
			return defaultValue;
		}

		if (obj.isNull(name)) {
			LogUtil.log(TAG, "getStringFromJSONObject : obj.isNull(" + name
					+ ") = true");
			return defaultValue;
		}

		int value = obj.getInt(name);
		return value;

	}

	/**
	 * 从JSONObject获取指定key的值
	 * 
	 * @param obj
	 *            Bundle对象
	 * @param name
	 *            要获取的key的名称
	 * @return 从JSONObject获取的值
	 * @throws JSONException
	 */
	public static long getLong(JSONObject obj, String name)
			throws JSONException {
		return getLong(obj, name, 0);
	}

	/**
	 * 从JSONObject获取指定key的值
	 * 
	 * @param obj
	 *            Bundle对象
	 * @param name
	 *            要获取的key的名称
	 * 
	 * @param defaultValue
	 *            默认值
	 * @return 从JSONObject获取的值
	 * @throws JSONException
	 */
	public static long getLong(JSONObject obj, String name, long defaultValue)
			throws JSONException {
		if (obj == null || name == null) {
			return defaultValue;
		}

		if (obj.isNull(name)) {
			LogUtil.log(TAG, "getStringFromJSONObject : obj.isNull(" + name
					+ ") = true");
			return defaultValue;
		}

		long value = obj.getLong(name);
		return value;

	}
}
