package com.keanbin.framework.http;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.keanbin.framework.http.entity.UpdateApkInfo;
import com.keanbin.framework.utils.LogUtil;
import com.keanbin.framework.utils.StringUtil;

/**
 * 检查版本更新的Api
 * 
 * @author keanbin
 * 
 */
public class CheckUpateHttpApi extends HttpApi {

	private static final String TAG = CheckUpateHttpApi.class
			.getCanonicalName();

	public CheckUpateHttpApi(Context context) {
		super(context);

	}

	public boolean sendHttpRequest(String url) {
		return super.sendHttpRequest(url);
	}

	/**
	 * 解析最新的版本的信息
	 * 
	 * @param result
	 * @return 有可更新的版本返回UpdateApkInfo对象，否则，返回null。
	 */
	public static UpdateApkInfo jsonData(String result) {
		if (StringUtil.isEmpty(result)) {
			return null;
		}

		try {
			JSONObject resultObj = new JSONObject(result);
			UpdateApkInfo updateApkInfo = UpdateApkInfo.jsonApkInfo(resultObj);

			return updateApkInfo;

		} catch (JSONException e) {
			LogUtil.errorLog(TAG, e);
		}

		return null;
	}
}
