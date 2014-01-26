package com.keanbin.framework.http.entity;

import android.os.Parcelable;

import com.keanbin.framework.base.BaseEntity;
import com.keanbin.framework.http.frame.HttpManager.HttpListener;

/**
 * http请求公共参数
 * 
 * @author keanbin
 */
public class HttpCommonParams extends BaseEntity {

	private String url; // 网址
	private Parcelable extendParam; // 扩展参数
	private HttpListener listener; // Http请求监听器

	/**
	 * @param flag
	 *            Http请求的标记，必须是唯一的，没有两个Http请求的标记是相同的。
	 * @param url
	 *            网址
	 */
	public HttpCommonParams(String url) {
		this.url = url;
	}

	/**
	 * 获取额外参数
	 * 
	 * @return
	 */
	public Parcelable getExtendParam() {
		return extendParam;
	}

	/**
	 * 设置额外参数
	 * 
	 * @param extendParam
	 */
	public void setExtendParam(Parcelable extendParam) {
		this.extendParam = extendParam;
	}

	/**
	 * 设置网址
	 * 
	 * @return
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 返回网址
	 * 
	 * @param url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	public HttpListener getHttpListener() {
		return listener;
	}

	public void setHttpListener(HttpListener listener) {
		this.listener = listener;
	}

	@Override
	public boolean isError() {
		return url == null;
	}

	@Override
	public String toString() {
		return "HttpCommonParams [url=" + url + ", extendParam=" + extendParam
				+ ", listener=" + listener + "]";
	}

}
