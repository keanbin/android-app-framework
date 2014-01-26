package com.keanbin.framework.http;

import org.apache.http.message.BasicNameValuePair;

import android.content.Context;

import com.keanbin.framework.http.HttpApi;
import com.keanbin.framework.utils.CommonConfig;

/**
 * 使用HttpApi的例子
 * 
 * @author keanbin
 * 
 */
public class HttpApiExample extends HttpApi {

	private static final String URL = CommonConfig.URL_HEAD + "";

	public HttpApiExample(Context context) {
		super(context);

	}

	public boolean sendHttpRequest() {
		return sendHttpRequest(URL, new BasicNameValuePair("get", "use get"),
				new BasicNameValuePair("name", "keanbin get"));
	}
}
