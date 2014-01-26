package com.keanbin.framework.http.frame;

import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;

import android.content.Context;
import android.content.Intent;

import com.keanbin.framework.base.BaseClass;
import com.keanbin.framework.http.Exception.BaseHttpConnectionException;
import com.keanbin.framework.http.Exception.BaseHttpException;
import com.keanbin.framework.http.Exception.BaseHttpParamErrorException;
import com.keanbin.framework.http.Exception.BaseHttpUserStopException;
import com.keanbin.framework.http.base.BaseHttpRequest.DownloadFileListener;
import com.keanbin.framework.http.entity.HttpCommonParams;
import com.keanbin.framework.http.entity.HttpResult;
import com.keanbin.framework.http.entity.UploadFileInfo;
import com.keanbin.framework.utils.CommonConfig;

/**
 * Http请求管理中心，该类采用单例模式。
 * 
 * @author keanbin
 * 
 */
public class HttpManager extends BaseClass {

	private static HttpManager mHttpManager;

	private Context mContext;
	/**
	 * HttpRequest Http请求池
	 */
	private HashMap<String, HttpRequest> mHttpRequestMap;

	private HttpManager(Context context) {
		mContext = context;
		mHttpRequestMap = new HashMap<String, HttpRequest>();
	}

	public static HttpManager getInstance(Context context) {
		if (mHttpManager == null) {
			mHttpManager = new HttpManager(context);
		}

		return mHttpManager;
	}

	public static HttpManager getInstance() {

		return mHttpManager;
	}

	/**
	 * @Description 发送 http 请求结果广播
	 * @param httpTag
	 *            http请求的标签
	 * @param time
	 *            进行http请求的时间
	 * @param extendParam
	 *            扩展参数，实现Parcelable的类型的对象，即可序列化的对象
	 * @param responseResult
	 *            http 请求结果
	 * @return
	 */
	private void sendBroadcast(HttpResult httpResult) {

		Intent intent = new Intent(
				CommonConfig.BROADCAST_ACTION_HTTP_REQUEST_RESULT);

		intent.putExtra(CommonConfig.INTENT_DATA_HTTP_REQUEST_RESULT,
				httpResult);

		mContext.sendBroadcast(intent);
	}

	private void setHttpRequestResultFlagForException(HttpResult httpResult,
			BaseHttpException e) {

		if (e instanceof BaseHttpUserStopException) {
			httpResult.setHttpRequestResult(HttpResult.HTTP_REQUEST_USER_STOP);

		} else if (e instanceof BaseHttpParamErrorException) {
			httpResult.setHttpRequestResult(HttpResult.HTTP_REQUEST_FAIL);

		} else if (e instanceof BaseHttpConnectionException) {
			httpResult.setHttpRequestResult(HttpResult.HTTP_REQUEST_FAIL);

		}
	}

	/**
	 * 把Http请求加入Http请求池中
	 * 
	 * @param time
	 * @param httpRequest
	 */
	private void addHttpRequestToPool(String httpRequestFlag,
			HttpRequest httpRequest) {
		if (mHttpRequestMap == null) {
			mHttpRequestMap = new HashMap<String, HttpRequest>();
		}

		mHttpRequestMap.put(httpRequestFlag, httpRequest);
	}

	/**
	 * 从Http请求池移除该Http请求
	 * 
	 * @param time
	 */
	private void removeHttpRequestFromPool(String httpRequestFlag) {

		if (mHttpRequestMap == null) {
			return;
		}

		mHttpRequestMap.remove(httpRequestFlag);
	}

	/**
	 * 从Http请求池获取Http请求
	 * 
	 * @param time
	 */
	private HttpRequest getHttpRequestFromPool(String httpRequestFlag) {

		if (mHttpRequestMap == null || mHttpRequestMap.size() == 0) {
			return null;
		}

		return mHttpRequestMap.get(httpRequestFlag);
	}

	// ------------------ Http 请求 ------------------

	/**
	 * http请求是否存在
	 * 
	 * @param httpRequestFlag
	 * @return
	 */
	public boolean existHttpRequest(String httpRequestFlag) {
		HttpRequest httpRequest = getHttpRequestFromPool(httpRequestFlag);

		return httpRequest != null;
	}

	/**
	 * 停止Http 请求
	 * 
	 * @param time
	 */
	public boolean stopHttpRequest(String httpRequestFlag) {
		HttpRequest httpRequest = getHttpRequestFromPool(httpRequestFlag);
		if (httpRequest != null) {
			httpRequest.stop();
		}

		return true;
	}

	/**
	 * 发送Http请求
	 * 
	 * @param commonParams
	 *            公共参数
	 * @param isPost
	 *            是否使用Post方式
	 * @param nameValuePairs
	 *            参数
	 */
	public boolean sendHttpRequest(final String httpRequestFlag,
			final HttpCommonParams commonParams, final boolean isPost,
			final NameValuePair... nameValuePairs) {

		new Thread(new Runnable() {

			@Override
			public void run() {

				// 启动http请求
				HttpRequest httpRequest = new HttpRequest(mContext, isPost);

				// 把Http请求加入Http请求池中
				addHttpRequestToPool(httpRequestFlag, httpRequest);

				// 设置Http请求结果对象参数
				HttpResult httpResult = new HttpResult();
				httpResult.setFlag(httpRequestFlag);
				httpResult.setExtendParam(commonParams.getExtendParam());
				httpResult.setHttpRequestResult(HttpResult.HTTP_PEQUEST_START);
				// 发送 开始 Http请求的广播
				sendBroadcast(httpResult);

				log("sendHttpRequest(): flag = " + httpRequestFlag + ", url = "
						+ commonParams.getUrl());

				String result = null;
				try {
					result = httpRequest.sendHttpRequest(commonParams.getUrl(),
							nameValuePairs);

					httpResult.setServerResult(result);
					httpResult
							.setHttpRequestResult(HttpResult.HTTP_REQUEST_SUCCESS);

				} catch (BaseHttpException e) {
					e.printStackTrace();

					setHttpRequestResultFlagForException(httpResult, e);
				}

				// service 处理某些http请求返回的结果
				HttpListener listener = commonParams.getHttpListener();
				if (listener != null) {
					listener.dealResultThread(
							httpRequestFlag,
							httpResult.getHttpRequestResult() == HttpResult.HTTP_REQUEST_SUCCESS,
							result, httpResult);
				}

				log("httpEnd: " + httpResult.toString());

				// 发送 http 请求结果广播
				sendBroadcast(httpResult);

				// 从Http请求池移除该Http请求
				removeHttpRequestFromPool(httpRequestFlag);

			}
		}).start();

		return true;
	}

	/**
	 * 上传多个文件
	 * 
	 * @param commonParams
	 *            公共参数
	 * @param uploadFileInfoList
	 *            文件列表
	 * @param nameValuePairs
	 *            参数
	 */
	public boolean uploadFile(final String httpRequestFlag,
			final HttpCommonParams commonParams,
			final List<UploadFileInfo> uploadFileInfoList,
			final NameValuePair... nameValuePairs) {
		new Thread(new Runnable() {

			@Override
			public void run() {

				// 启动http请求
				HttpRequest httpRequest = new HttpRequest(mContext);

				// 把Http请求加入Http请求池中
				addHttpRequestToPool(httpRequestFlag, httpRequest);

				// 设置Http请求结果对象参数
				HttpResult httpResult = new HttpResult();
				httpResult.setFlag(httpRequestFlag);
				httpResult.setExtendParam(commonParams.getExtendParam());
				httpResult.setHttpRequestResult(HttpResult.HTTP_PEQUEST_START);
				// 发送 开始 Http请求的广播
				sendBroadcast(httpResult);

				log("uploadFile(): flag = " + httpRequestFlag + ", url = "
						+ commonParams.getUrl());

				String result = null;
				try {
					result = httpRequest.uploadFile(commonParams.getUrl(),
							uploadFileInfoList, nameValuePairs);

					httpResult.setServerResult(result);
					httpResult
							.setHttpRequestResult(HttpResult.HTTP_REQUEST_SUCCESS);

				} catch (BaseHttpException e) {
					e.printStackTrace();

					setHttpRequestResultFlagForException(httpResult, e);
				}

				// service 处理某些http请求返回的结果
				HttpListener listener = commonParams.getHttpListener();
				if (listener != null) {
					listener.dealResultThread(
							httpRequestFlag,
							httpResult.getHttpRequestResult() == HttpResult.HTTP_REQUEST_SUCCESS,
							result, httpResult);
				}

				log("httpEnd: " + httpResult.toString());
				// 发送 http 请求结果广播
				sendBroadcast(httpResult);

				// 从Http请求池移除该Http请求
				removeHttpRequestFromPool(httpRequestFlag);

			}
		}).start();

		return true;
	}

	/**
	 * 下载文件
	 * 
	 * @param commonParams
	 *            公共参数
	 * @param filePath
	 *            文件保存路径
	 * @param isResume
	 *            是否断点下载
	 */
	public boolean downLoadFile(final String httpRequestFlag,
			final HttpCommonParams commonParams, final String filePath,
			final boolean isResume) {
		new Thread(new Runnable() {

			@Override
			public void run() {

				// 启动http请求
				HttpRequest httpRequest = new HttpRequest(mContext);

				// 把Http请求加入Http请求池中
				addHttpRequestToPool(httpRequestFlag, httpRequest);

				// 设置Http请求结果对象参数
				final HttpResult httpResult = new HttpResult();
				httpResult.setFlag(httpRequestFlag);
				httpResult.setExtendParam(commonParams.getExtendParam());
				httpResult.setHttpRequestResult(HttpResult.HTTP_PEQUEST_START);
				// 发送 开始 Http请求的广播
				sendBroadcast(httpResult);

				log("downLoadFile(): flag = " + httpRequestFlag + ", url = "
						+ commonParams.getUrl() + ", filePath = " + filePath
						+ ", isResume = " + isResume);

				try {
					String result = httpRequest.downLoadFile(
							commonParams.getUrl(), filePath, isResume,
							new DownloadFileListener() {

								@Override
								public void downloading(long count,
										long current, boolean refreshUI) {

									if (count > 0 && refreshUI) {
										httpResult.setCount(count);
										httpResult.setCurrent(current);
										httpResult
												.setHttpRequestResult(HttpResult.HTTP_PEQUEST_PROCESS);
										// 发送 http 请求执行过程的广播
										sendBroadcast(httpResult);
									}
								}
							});

					httpResult.setServerResult(result);
					httpResult
							.setHttpRequestResult(HttpResult.HTTP_REQUEST_SUCCESS);

				} catch (BaseHttpException e) {
					e.printStackTrace();

					setHttpRequestResultFlagForException(httpResult, e);
				}

				log("httpEnd: " + httpResult.toString());
				// 发送处理 http 请求结果广播
				sendBroadcast(httpResult);

				// 从Http请求池移除该Http请求
				removeHttpRequestFromPool(httpRequestFlag);

			}
		}).start();

		return true;
	}

	public interface HttpListener {
		void dealResultThread(String flag, boolean isSuccess, String result,
				HttpResult httpResult);
	}
}
