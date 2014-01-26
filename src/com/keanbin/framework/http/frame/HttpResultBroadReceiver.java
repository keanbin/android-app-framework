package com.keanbin.framework.http.frame;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.keanbin.framework.http.entity.HttpResult;
import com.keanbin.framework.utils.CommonConfig;

/**
 * @ClassName HttpResultBroadReceiver
 * @Description http请求结果的广播
 * @author kevin
 */
public class HttpResultBroadReceiver extends BroadcastReceiver {

	private Context mContext;
	private boolean isRegisterBroadReceiver;
	private HttpRequestBroadReceiverListener mListener;

	public HttpResultBroadReceiver(Context mContext,
			HttpRequestBroadReceiverListener l) {
		super();
		this.mContext = mContext;
		this.mListener = l;

		isRegisterBroadReceiver = false;
	}

	/**
	 * @Description 注册http请求结果的广播
	 * @return
	 */
	public void registerHttpResultBroadReceiver() {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter
				.addAction(CommonConfig.BROADCAST_ACTION_HTTP_REQUEST_RESULT);
		mContext.registerReceiver(this, intentFilter);
		isRegisterBroadReceiver = true;
	}

	/**
	 * @Description 注销http请求结果的广播
	 * @return
	 */
	public void unregisterHttpResultBroadReceiver() {
		if (isRegisterBroadReceiver) {
			mContext.unregisterReceiver(this);
			isRegisterBroadReceiver = false;
		}
	}

	@Override
	public void onReceive(Context context, Intent intent) {

		if (intent == null) {
			return;
		}

		HttpResult httpResult = intent
				.getParcelableExtra(CommonConfig.INTENT_DATA_HTTP_REQUEST_RESULT);

		if (mListener != null) {
			int httpRequestResult = httpResult.getHttpRequestResult();

			switch (httpRequestResult) {
			case HttpResult.HTTP_PEQUEST_START:
				mListener.start(httpResult.getFlag(), httpResult);
				break;

			case HttpResult.HTTP_PEQUEST_PROCESS:
				long count = httpResult.getCount();
				long current = httpResult.getCurrent();

				mListener.process(httpResult.getFlag(), count, current,
						httpResult);
				break;

			case HttpResult.HTTP_REQUEST_SUCCESS:

				mListener.result(httpResult.getFlag(),
						httpResult.getServerResult(), httpResult);
				break;

			case HttpResult.HTTP_REQUEST_USER_STOP:
				mListener.userStop(httpResult.getFlag(), httpResult);
				break;

			case HttpResult.HTTP_REQUEST_FAIL:
				mListener.error(httpResult.getFlag(), httpResult);
				break;

			default:
				break;
			}

		}
	}

	public interface HttpRequestBroadReceiverListener {

		/**
		 * 开始Http请求
		 * 
		 * @param httpResult
		 */
		public void start(String flag, HttpResult httpResult);

		/**
		 * Http请求执行过程中，目前只在下载文件中使用。
		 * 
		 * @param httpResult
		 */
		public void process(String flag, long count, long current,
				HttpResult httpResult);

		/**
		 * 收到 Http请求结果
		 * 
		 * @param httpResult
		 *            http请求结果对象
		 */
		public void result(String flag, String result, HttpResult httpResult);

		/**
		 * 用户主动停止
		 * 
		 * @param httpResult
		 */
		public void userStop(String flag, HttpResult httpResult);

		/**
		 * Http请求出错
		 * 
		 * @param httpResult
		 */
		public void error(String flag, HttpResult httpResult);
	}

}
