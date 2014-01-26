package com.keanbin.framework.http;

import android.content.Context;

import com.keanbin.framework.http.entity.HttpCommonParams;

public class HttpApiDownloadFile extends HttpApi {

	public HttpApiDownloadFile(Context context) {
		super(context);

	}

	/**
	 * 下载文件
	 * 
	 * @param fileUrl
	 *            网址
	 * @param filePath
	 *            保存的文件路径
	 * @return
	 */
	public boolean downLoadFile(String fileUrl, String filePath) {
		return downLoadFile(fileUrl, filePath, false);
	}

	/**
	 * 下载文件
	 * 
	 * @param fileUrl
	 *            网址
	 * @param filePath
	 *            保存的文件路径
	 * @param isResume
	 *            是否断点下载
	 * @return
	 */
	public boolean downLoadFile(String fileUrl, String filePath,
			boolean isResume) {
		HttpCommonParams commonParams = new HttpCommonParams(fileUrl);

		return downLoadFile(commonParams, filePath, isResume);
	}

	/**
	 * 下载文件
	 * 
	 * 
	 * @param commonParams
	 *            参数
	 * @param filePath
	 *            保存的文件路径
	 * @return
	 */
	public boolean downLoadFile(HttpCommonParams commonParams, String filePath) {
		return downLoadFile(commonParams, filePath, false);
	}

	/**
	 * 下载文件
	 * 
	 * @param commonParams
	 *            公共参数
	 * @param filePath
	 *            保存文件的路径
	 * @param isResume
	 *            是否断点下载
	 * @return
	 */
	public boolean downLoadFile(HttpCommonParams commonParams, String filePath,
			boolean isResume) {

		return super.downLoadFile(commonParams, filePath, isResume);
	}
}
