package com.keanbin.framework.http.frame;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;

import android.content.Context;

import com.keanbin.framework.base.BaseClass;
import com.keanbin.framework.http.Exception.BaseHttpConnectionException;
import com.keanbin.framework.http.Exception.BaseHttpParamErrorException;
import com.keanbin.framework.http.Exception.BaseHttpUserStopException;
import com.keanbin.framework.http.base.BaseHttpRequest;
import com.keanbin.framework.http.base.BaseHttpRequest.DownloadFileListener;
import com.keanbin.framework.http.base.BaseHttpURLConnection;
import com.keanbin.framework.http.entity.UploadFileInfo;

/**
 * Http 请求
 * 
 * @author keanbin
 * 
 */
public class HttpRequest extends BaseClass {

	/**
	 * http请求操作对象
	 */
	private BaseHttpRequest mBaseHttpRequest;
	private Context mContext;

	/**
	 * 是否使用Post方式，默认是false。
	 */
	private boolean isPost;

	public HttpRequest(Context context) {
		this(context, false);
	}

	/**
	 * @param context
	 * @param isPost
	 *            是否使用post方式
	 */
	public HttpRequest(Context context, boolean isPost) {
		this(context, isPost, false);
	}

	/**
	 * @param context
	 * @param isPost
	 *            是否使用post方式
	 * @param isUseHttpClient
	 *            是否使用HttpClient类
	 */
	public HttpRequest(Context context, boolean isPost, boolean isUseHttpClient) {
		mContext = context;

		// if (isUseHttpClient) {
		//
		// } else {
		mBaseHttpRequest = new BaseHttpURLConnection();
		// }

		this.isPost = isPost;
	}

	/**
	 * 封装Http请求参数
	 * 
	 * @param nameValuePairs
	 *            参数数组
	 * @return
	 */
	private List<NameValuePair> packagingParams(NameValuePair[] nameValuePairs) {

		if (nameValuePairs == null) {
			return null;
		}

		List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();

		for (int i = 0; i < nameValuePairs.length; i++) {
			NameValuePair param = nameValuePairs[i];
			if (param.getValue() != null) {
				log("Param: " + param.getName() + " = " + param.getValue());
				nameValuePairList.add(param);
			}
		}

		return nameValuePairList;
	}

	/**
	 * 字节转为字符串
	 * 
	 * @param date
	 *            字节数组
	 * @param charsetName
	 *            指定字符集
	 * @return
	 */
	private String byteToString(byte[] date, String charsetName) {

		if (date == null) {
			return null;
		}

		if (charsetName == null) {
			return new String(date);
		} else {
			try {
				return new String(date, charsetName);
			} catch (UnsupportedEncodingException e) {
				logError(e);

				return new String(date);
			}
		}
	}

	/**
	 * 设置是否使用POST方式
	 * 
	 * @param b
	 */
	public void setIsPost(boolean b) {
		isPost = b;
	}

	/**
	 * 停止 Http 请求
	 */
	public void stop() {
		log("HttpRequest stop!");
		mBaseHttpRequest.stop();
	}

	/**
	 * 默认的Http请求方式
	 * 
	 * @param urlStr
	 *            请求的网址
	 * @param nameValuePairs
	 *            不定个数参数
	 * @return
	 * @throws BaseHttpConnectionException
	 * @throws BaseHttpParamErrorException
	 * @throws BaseHttpUserStopException
	 */
	public String sendHttpRequest(String urlStr,
			NameValuePair... nameValuePairs)
			throws BaseHttpConnectionException, BaseHttpParamErrorException,
			BaseHttpUserStopException {
		String charsetName = null;
		return sendHttpRequest(urlStr, charsetName, nameValuePairs);
	}

	/**
	 * 默认的Http请求方式
	 * 
	 * @param urlStr
	 *            请求的网址
	 * @param charsetName
	 *            返回结果指定使用的字符集(编码)
	 * @param nameValuePairs
	 *            不定个数参数
	 * @return
	 * @throws BaseHttpConnectionException
	 * @throws BaseHttpParamErrorException
	 * @throws BaseHttpUserStopException
	 */
	public String sendHttpRequest(String urlStr, String charsetName,
			NameValuePair... nameValuePairs)
			throws BaseHttpConnectionException, BaseHttpParamErrorException,
			BaseHttpUserStopException {

		if (isPost) {
			return postMethodHttpRequest(urlStr, charsetName, nameValuePairs);
		} else {
			return getMethodHttpRequest(urlStr, charsetName, nameValuePairs);
		}

	}

	// -------------------- GET 方式请求 -----------------------
	/**
	 * Get方式Http请求
	 * 
	 * @param urlStr
	 *            请求的网址
	 * @param nameValuePairs
	 *            不定个数参数
	 * @return
	 * @throws BaseHttpConnectionException
	 * @throws BaseHttpParamErrorException
	 * @throws BaseHttpUserStopException
	 */
	public String getMethodHttpRequest(String urlStr,
			NameValuePair... nameValuePairs)
			throws BaseHttpConnectionException, BaseHttpParamErrorException,
			BaseHttpUserStopException {
		String charsetName = null;
		return getMethodHttpRequest(urlStr, charsetName, nameValuePairs);
	}

	/**
	 * Get方式Http请求
	 * 
	 * @param urlStr
	 *            请求的网址
	 * @param charsetName
	 *            返回结果指定使用的字符集(编码)
	 * @param nameValuePairs
	 *            不定个数参数
	 * @return
	 * @throws BaseHttpConnectionException
	 * @throws BaseHttpParamErrorException
	 * @throws BaseHttpUserStopException
	 */
	public String getMethodHttpRequest(String urlStr, String charsetName,
			NameValuePair... nameValuePairs)
			throws BaseHttpConnectionException, BaseHttpParamErrorException,
			BaseHttpUserStopException {

		log("getMethodHttpRequest urlStr: " + urlStr);

		List<NameValuePair> nameValuePairList = packagingParams(nameValuePairs);

		byte[] respon = mBaseHttpRequest.getMethod(urlStr, nameValuePairList);

		return byteToString(respon, charsetName);

	}

	// -------------------- POST 方式请求 -----------------------

	/**
	 * Post方式Http请求
	 * 
	 * @param urlStr
	 *            请求的网址
	 * @param nameValuePairs
	 *            不定个数参数
	 * @return
	 * @throws BaseHttpConnectionException
	 * @throws BaseHttpParamErrorException
	 * @throws BaseHttpUserStopException
	 */
	public String postMethodHttpRequest(String urlStr,
			NameValuePair... nameValuePairs)
			throws BaseHttpConnectionException, BaseHttpParamErrorException,
			BaseHttpUserStopException {
		String charsetName = null;
		return postMethodHttpRequest(urlStr, charsetName, nameValuePairs);
	}

	/**
	 * Post方式Http请求
	 * 
	 * @param urlStr
	 *            请求的网址
	 * @param charsetName
	 *            返回结果指定使用的字符集(编码)
	 * @param nameValuePairs
	 *            不定个数参数
	 * @return
	 * @throws BaseHttpConnectionException
	 * @throws BaseHttpParamErrorException
	 * @throws BaseHttpUserStopException
	 */
	public String postMethodHttpRequest(String urlStr, String charsetName,
			NameValuePair... nameValuePairs)
			throws BaseHttpConnectionException, BaseHttpParamErrorException,
			BaseHttpUserStopException {

		log("postMethodHttpRequest  urlStr: " + urlStr);

		List<NameValuePair> nameValuePairList = packagingParams(nameValuePairs);

		byte[] result = mBaseHttpRequest.postMethod(urlStr, nameValuePairList);

		return byteToString(result, charsetName);
	}

	// -------------------- 上传文件 -----------------------

	/**
	 * 带参数上传单个文件
	 * 
	 * @param uploadUrl
	 *            上传的网址
	 * @param uploadFileInfo
	 *            上传的文件
	 * @param nameValuePairs
	 *            不定个数参数
	 * @return
	 * @throws BaseHttpConnectionException
	 * @throws BaseHttpParamErrorException
	 * @throws BaseHttpUserStopException
	 */
	public String uploadFile(String uploadUrl, UploadFileInfo uploadFileInfo,
			NameValuePair... nameValuePairs)
			throws BaseHttpConnectionException, BaseHttpParamErrorException,
			BaseHttpUserStopException {
		String charsetName = null;
		return uploadFile(uploadUrl, charsetName, uploadFileInfo,
				nameValuePairs);
	}

	/**
	 * 带参数上传多个文件
	 * 
	 * @param uploadUrl
	 *            上传的网址
	 * @param uploadFileInfoList
	 *            上传的文件列表
	 * @param nameValuePairs
	 *            不定个数参数
	 * @return
	 * @throws BaseHttpConnectionException
	 * @throws BaseHttpParamErrorException
	 * @throws BaseHttpUserStopException
	 */
	public String uploadFile(String uploadUrl,
			List<UploadFileInfo> uploadFileInfoList,
			NameValuePair... nameValuePairs)
			throws BaseHttpConnectionException, BaseHttpParamErrorException,
			BaseHttpUserStopException {

		String charsetName = null;
		return uploadFile(uploadUrl, charsetName, uploadFileInfoList,
				nameValuePairs);
	}

	/**
	 * 带参数上传单个文件
	 * 
	 * @param uploadUrl
	 *            上传的网址
	 * @param charsetName
	 *            返回结果的字符集（编码）
	 * @param uploadFileInfo
	 *            上传的文件
	 * @param nameValuePairs
	 *            不定个数参数
	 * @return
	 * @throws BaseHttpConnectionException
	 * @throws BaseHttpParamErrorException
	 * @throws BaseHttpUserStopException
	 */
	public String uploadFile(String uploadUrl, String charsetName,
			UploadFileInfo uploadFileInfo, NameValuePair... nameValuePairs)
			throws BaseHttpConnectionException, BaseHttpParamErrorException,
			BaseHttpUserStopException {

		if (uploadFileInfo == null || uploadFileInfo.isError()) {
			throw new BaseHttpParamErrorException(
					"uploadFileInfo is null or error!");
		}

		List<UploadFileInfo> uploadFileInfoList = new ArrayList<UploadFileInfo>();
		uploadFileInfoList.add(uploadFileInfo);

		return uploadFile(uploadUrl, charsetName, uploadFileInfoList,
				nameValuePairs);
	}

	/**
	 * 带参数上传多个文件
	 * 
	 * @param uploadUrl
	 *            上传的网址
	 * @param charsetName
	 *            返回结果的字符集（编码）
	 * @param uploadFileInfoList
	 *            上传的文件列表
	 * @param nameValuePairs
	 *            不定个数参数
	 * @return
	 * @throws BaseHttpConnectionException
	 * @throws BaseHttpParamErrorException
	 * @throws BaseHttpUserStopException
	 */
	public String uploadFile(String uploadUrl, String charsetName,
			List<UploadFileInfo> uploadFileInfoList,
			NameValuePair... nameValuePairs)
			throws BaseHttpConnectionException, BaseHttpParamErrorException,
			BaseHttpUserStopException {

		log("uploadFile uploadUrl: " + uploadUrl);

		List<NameValuePair> nameValuePairList = packagingParams(nameValuePairs);

		byte[] result = mBaseHttpRequest.uploadFile(uploadUrl,
				uploadFileInfoList, nameValuePairList);

		return byteToString(result, charsetName);
	}

	// -------------------- 下载文件 -----------------------
	/**
	 * 下载文件
	 * 
	 * @param fileUrl
	 *            文件的网址
	 * @param file
	 *            存放到本地的文件的操作对象
	 * @param isResume
	 *            是否断点续传
	 * @param listener
	 *            下载文件监听器
	 * @return 文件的绝对路径
	 * @throws BaseHttpParamErrorException
	 * @throws BaseHttpConnectionException
	 * @throws BaseHttpUserStopException
	 */
	public String downLoadFile(String fileUrl, File file, boolean isResume,
			DownloadFileListener listener) throws BaseHttpParamErrorException,
			BaseHttpConnectionException, BaseHttpUserStopException {

		if (fileUrl == null) {
			throw new BaseHttpParamErrorException(
					"downLoadFile fileUrl is null!");
		}

		if (file == null) {
			throw new BaseHttpParamErrorException("downLoadFile file is null!");
		}

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				logError(e);
				throw new BaseHttpParamErrorException(
						"downLoadFile file is error!");
			}
		}

		if (!file.exists() || !file.isFile()) {
			throw new BaseHttpParamErrorException("downLoadFile file is error!");
		}

		return mBaseHttpRequest.downLoadFile(fileUrl, file, isResume, listener);
	}

	/**
	 * 下载文件
	 * 
	 * @param fileUrl
	 *            文件的网址
	 * @param filePath
	 *            存放到本地的路径
	 * @param isResume
	 *            是否断点续传
	 * @param listener
	 *            下载文件监听器
	 * @return 文件的绝对路径
	 * @throws BaseHttpParamErrorException
	 * @throws BaseHttpConnectionException
	 * @throws BaseHttpUserStopException
	 */
	public String downLoadFile(String fileUrl, String filePath,
			boolean isResume, DownloadFileListener listener)
			throws BaseHttpParamErrorException, BaseHttpConnectionException,
			BaseHttpUserStopException {
		if (filePath == null) {
			throw new BaseHttpParamErrorException(
					"downLoadFile filePath is null!");
		}

		log("downLoadFile fileUrl: " + fileUrl + ", filePath = " + filePath
				+ ", isResume = " + isResume);

		File file = new File(filePath);

		return downLoadFile(fileUrl, file, isResume, listener);
	}
}
