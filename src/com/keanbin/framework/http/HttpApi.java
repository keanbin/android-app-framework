package com.keanbin.framework.http;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;

import android.content.Context;

import com.keanbin.framework.base.BaseClass;
import com.keanbin.framework.http.entity.HttpCommonParams;
import com.keanbin.framework.http.entity.UploadFileInfo;
import com.keanbin.framework.http.frame.HttpManager;
import com.keanbin.framework.utils.StringUtil;

/**
 * Http请求操作对象
 * 
 * @发起Http请求的基本流程：使用HttpApi的子类发起Http请求 -> HttpServiceOperate类 -> HttpService
 *                                     Http请求服务 -> HttpRequest类 ->
 *                                     BaseHttpRequest的子类。
 * @收到服务器反馈的基本流程：服务器答复 -> BaseHttpRequest的子类 -> HttpRequest类 ->HttpService
 *                     Http请求服务根据情况发出广播 -> HttpResultBroadReceiver类 -->
 *                     BaseActivity（在onCreate()的时候就注册接收该广播），并根据定义了处理各种情况的方法，
 *                     BaseActivity的子类只需要复写这些方法
 *                     ：onHttpRequestStart()、onHttpRequestProcess
 *                     ()、onHttpRequestReceiveResult()、onHttpRequestError()。
 * 
 * @author keanbin
 * 
 */
public class HttpApi extends BaseClass {

	/**
	 * 默认http请求是否是Post方式
	 */
	private static final boolean DEFAULT_IS_POST = false;

	private static long mCount = 0L;

	private String mClassName;

	/**
	 * Http请求的标记，必须是唯一的，不存在两个Http请求的标记是相同的。
	 */
	protected String mHttpRequestFlag;
	protected Context mContext;

	public HttpApi(Context context) {
		mClassName = this.getClass().getName();
		mContext = context;

		mHttpRequestFlag = createNewHttpRequestFlag();
	}

	/**
	 * 获取HttpServiceOperate Http请求服务的操作对象
	 * 
	 * @return
	 */
	private static HttpManager getHttpManager() {
		return HttpManager.getInstance();
	}

	/**
	 * 生成新的Http请求的标记，必须是唯一的，没有两个Http请求的标记是相同的。如果mHttpRequestFlag !=
	 * null，就调用stopHttpRequest()关闭已经存在的Http请求
	 * 
	 * @return
	 */
	private String createNewHttpRequestFlag() {
		if (mHttpRequestFlag != null) {
			stopHttpRequest();
		}

		if (mCount < Long.MAX_VALUE) {
			mCount++;
		} else {
			mCount = 0;
		}

		mHttpRequestFlag = mClassName + mCount + System.currentTimeMillis();

		return mHttpRequestFlag;
	}

	/**
	 * 设置Http请求的标记，必须是唯一的，没有两个Http请求的标记是相同的。
	 * 
	 * @param flag
	 */
	public void setHttpRequestFlag(String flag) {

		if (StringUtil.isEmpty(flag)) {
			return;
		}

		if (mHttpRequestFlag != null) {
			stopHttpRequest();
		}

		mHttpRequestFlag = flag;
	}

	/**
	 * 获取Http请求的标记，必须是唯一的，不存在两个Http请求的标记是相同的。
	 * 
	 * @return
	 */
	public String getHttpRequestFlag() {
		return mHttpRequestFlag;
	}

	// ------------------ HttpRequest 操作 ------------------

	/**
	 * 判断http请求是否存在
	 * 
	 * @param httpRequestFlag
	 * @return
	 */
	public boolean existHttpRequest() {
		HttpManager httpManager = getHttpManager();

		if (httpManager == null || mHttpRequestFlag == null) {
			return false;
		}

		return httpManager.existHttpRequest(mHttpRequestFlag);
	}

	/**
	 * 停止Http请求
	 * 
	 * @return
	 */
	public boolean stopHttpRequest() {

		HttpManager httpManager = getHttpManager();

		if (httpManager == null || mHttpRequestFlag == null) {
			return false;
		}

		httpManager.stopHttpRequest(mHttpRequestFlag);
		return true;
	}

	/**
	 * 停止Http请求
	 * 
	 * @param httpRequestFlag
	 *            Http请求的标记
	 * @return
	 */
	public static boolean stopHttpRequest(String httpRequestFlag) {
		HttpManager httpManager = getHttpManager();

		if (httpManager == null || httpRequestFlag == null) {
			return false;
		}

		return httpManager.stopHttpRequest(httpRequestFlag);
	}

	/**
	 * 判断Http请求的标记是否是指定的Http请求的。
	 * 
	 * @param httpApi
	 *            Http请求
	 * @param httpRequestFlag
	 *            Http请求的标记
	 * @return
	 */
	public static boolean isSelfFlag(HttpApi httpApi, String httpRequestFlag) {
		if (httpApi == null || httpRequestFlag == null) {
			return false;
		}

		return httpRequestFlag.equals(httpApi.getHttpRequestFlag());
	}

	// ------------------ Http请求 ------------------
	/**
	 * 发送Http请求
	 * 
	 * @param urlStr
	 *            网址
	 * @param nameValuePairs
	 *            参数
	 * @return
	 */
	protected boolean sendHttpRequest(String urlStr,
			NameValuePair... nameValuePairs) {

		return sendHttpRequest(urlStr, DEFAULT_IS_POST, nameValuePairs);
	}

	/**
	 * 发送Http请求
	 * 
	 * @param urlStr
	 *            网址
	 * @param isPost
	 *            是否使用post方式
	 * @param nameValuePairs
	 *            参数
	 * @return
	 */
	protected boolean sendHttpRequest(String urlStr, boolean isPost,
			NameValuePair... nameValuePairs) {
		HttpCommonParams commonParams = new HttpCommonParams(urlStr);

		return sendHttpRequest(commonParams, isPost, nameValuePairs);
	}

	/**
	 * 发送Http请求
	 * 
	 * @param commonParams
	 *            Http请求的公共参数
	 * @param nameValuePairs
	 *            给服务器的参数
	 * @return
	 */
	protected boolean sendHttpRequest(HttpCommonParams commonParams,
			NameValuePair... nameValuePairs) {
		return sendHttpRequest(commonParams, DEFAULT_IS_POST, nameValuePairs);
	}

	/**
	 * 发送Http请求
	 * 
	 * @param commonParams
	 *            Http请求的公共参数
	 * @param isPost
	 *            是否使用post方式
	 * @param nameValuePairs
	 *            给服务器的参数
	 * @return
	 */
	protected boolean sendHttpRequest(HttpCommonParams commonParams,
			boolean isPost, NameValuePair... nameValuePairs) {

		HttpManager httpManager = getHttpManager();

		if (httpManager == null) {
			return false;
		}

		return httpManager.sendHttpRequest(mHttpRequestFlag, commonParams,
				isPost, nameValuePairs);
	}

	// ------------------ 上传文件 ------------------
	/**
	 * 上传文件
	 * 
	 * @param uploadUrl
	 *            网址
	 * @param uploadFileInfo
	 *            文件信息
	 * @param nameValuePairs
	 *            参数
	 * @return
	 */
	protected boolean uploadFile(String uploadUrl,
			UploadFileInfo uploadFileInfo, NameValuePair... nameValuePairs) {
		HttpCommonParams commonParams = new HttpCommonParams(uploadUrl);

		return uploadFile(commonParams, uploadFileInfo, nameValuePairs);
	}

	/**
	 * 上传文件
	 * 
	 * @param commonParams
	 *            公共参数
	 * @param uploadFileInfo
	 *            文件信息
	 * @param nameValuePairs
	 *            参数
	 * @return
	 */
	protected boolean uploadFile(HttpCommonParams commonParams,
			UploadFileInfo uploadFileInfo, NameValuePair... nameValuePairs) {

		if (uploadFileInfo == null || uploadFileInfo.isError()) {
			return false;
		}

		List<UploadFileInfo> uploadFileInfoList = new ArrayList<UploadFileInfo>();
		uploadFileInfoList.add(uploadFileInfo);
		return uploadFile(commonParams, uploadFileInfoList, nameValuePairs);
	}

	/**
	 * 上传文件
	 * 
	 * @param uploadUrl
	 *            网址
	 * @param uploadFileInfoList
	 *            文件列表
	 * @param nameValuePairs
	 *            参数
	 * @return
	 */
	protected boolean uploadFile(String uploadUrl,
			List<UploadFileInfo> uploadFileInfoList,
			NameValuePair... nameValuePairs) {
		HttpCommonParams commonParams = new HttpCommonParams(uploadUrl);

		return uploadFile(commonParams, uploadFileInfoList, nameValuePairs);
	}

	/**
	 * 上传文件
	 * 
	 * @param commonParams
	 *            公共参数
	 * @param uploadFileInfoList
	 *            文件列表
	 * @param nameValuePairs
	 *            参数
	 * @return
	 */
	protected boolean uploadFile(HttpCommonParams commonParams,
			List<UploadFileInfo> uploadFileInfoList,
			NameValuePair... nameValuePairs) {
		HttpManager httpManager = getHttpManager();

		if (httpManager == null) {
			return false;
		}

		return httpManager.uploadFile(mHttpRequestFlag, commonParams,
				uploadFileInfoList, nameValuePairs);
	}

	// ------------------ 下载文件 ------------------

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
	protected boolean downLoadFile(HttpCommonParams commonParams,
			String filePath, boolean isResume) {
		HttpManager httpManager = getHttpManager();

		if (httpManager == null) {
			return false;
		}

		return httpManager.downLoadFile(mHttpRequestFlag, commonParams,
				filePath, isResume);
	}
}
