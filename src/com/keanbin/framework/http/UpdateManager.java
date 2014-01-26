package com.keanbin.framework.http;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.Toast;

import com.keanbin.app.R;
import com.keanbin.framework.base.BaseClass;
import com.keanbin.framework.http.entity.HttpResult;
import com.keanbin.framework.http.entity.UpdateApkInfo;
import com.keanbin.framework.http.frame.HttpResultBroadReceiver;
import com.keanbin.framework.http.frame.HttpResultBroadReceiver.HttpRequestBroadReceiverListener;
import com.keanbin.framework.utils.Utils;

/**
 * 更新检测管理
 * 
 * @author Administrator
 * 
 */
public class UpdateManager extends BaseClass {

	private Context mContext;
	private String mUpdateUrl;
	private CheckUpateHttpApi mCheckUpateHttpApi;
	private HttpResultBroadReceiver mHttpResultBroadReceiver;
	private UpdateApkInfo mUpdateApkInfo;
	private ProgressDialog mProgressDialog;
	private Toast mToast;
	private UpdateManagerListener mListener;

	public UpdateManager(Context context, String updateUrl) {
		this.mContext = context;
		mUpdateUrl = updateUrl;

		registerHttpResultBroadReceiver();
	}

	/**
	 * 设置监听器
	 * 
	 * @param l
	 */
	public void setListener(UpdateManagerListener l) {
		mListener = l;
	}

	/**
	 * 检查更新
	 */
	public void checkUpdate() {
		if (mUpdateUrl == null || mUpdateUrl.length() <= 0) {
			return;
		}

		mUpdateApkInfo = null;

		stopCheckUpateHttpApi();

		mCheckUpateHttpApi = new CheckUpateHttpApi(mContext);
		mCheckUpateHttpApi.sendHttpRequest(mUpdateUrl);
	}

	/**
	 * 停止版本检测Http请求
	 */
	private void stopCheckUpateHttpApi() {
		if (mCheckUpateHttpApi != null) {
			mCheckUpateHttpApi.stopHttpRequest();
			mCheckUpateHttpApi = null;
		}
	}

	/**
	 * 注册广播接收器，接收Http请求返回的结果
	 */
	private void registerHttpResultBroadReceiver() {
		log("UpdateManager registerHttpResultBroadReceiver()");
		if (mHttpResultBroadReceiver == null) {
			mHttpResultBroadReceiver = new HttpResultBroadReceiver(mContext,
					mHttpRequestBroadReceiverListener);
			mHttpResultBroadReceiver.registerHttpResultBroadReceiver();
		}
	}

	/**
	 * 注销广播接收器
	 */
	private void unregisterHttpResultBroadReceiver() {
		log("UpdateManager unregisterHttpResultBroadReceiver()");
		if (mHttpResultBroadReceiver != null) {
			mHttpResultBroadReceiver.unregisterHttpResultBroadReceiver();
			mHttpResultBroadReceiver = null;
		}
	}

	private HttpRequestBroadReceiverListener mHttpRequestBroadReceiverListener = new HttpRequestBroadReceiverListener() {

		@Override
		public void start(String flag, HttpResult httpResult) {
			if (HttpApi.isSelfFlag(mCheckUpateHttpApi, flag)) {
				if (mListener != null) {
					mListener.startCheckUpdate();
				}
			} else if (mUpdateApkInfo != null
					&& mUpdateApkInfo.getApkUrl().equals(flag)) {
				if (mProgressDialog != null) {
					mProgressDialog.setProgress(0);
				}
			}

		}

		@Override
		public void result(String flag, String result, HttpResult httpResult) {
			if (HttpApi.isSelfFlag(mCheckUpateHttpApi, flag)) {

				mUpdateApkInfo = CheckUpateHttpApi.jsonData(result);

				if (mUpdateApkInfo != null) {
					boolean isNeedUpdate = false;
					int currentVersion = Utils.getVersionCode(mContext);
					if (currentVersion < mUpdateApkInfo.getVersionId()) {
						isNeedUpdate = true;
						// 提示更新会话框
						showNoticeDialog();
					}

					if (mListener != null) {
						mListener.endCheckUpdate(isNeedUpdate);
					}
				} else {
					// Json解析出错！
					if (mListener != null) {
						mListener.serverConnectFail();
					}
				}

			} else if (mUpdateApkInfo != null
					&& mUpdateApkInfo.getApkUrl().equals(flag)) {

				closeDownloadDialog();
				// 安装
				Utils.installApk(mContext, mUpdateApkInfo.getDownloadFilePath());

			}

		}

		@Override
		public void process(String flag, long count, long current,
				HttpResult httpResult) {
			if (HttpApi.isSelfFlag(mCheckUpateHttpApi, flag)) {

			} else if (mUpdateApkInfo != null
					&& mUpdateApkInfo.getApkUrl().equals(flag)) {

				if (mProgressDialog != null) {
					int percent = (int) (current * 100 / count);
					mProgressDialog.setProgress(percent);
				}
			}

		}

		@Override
		public void userStop(String flag, HttpResult httpResult) {
			if (HttpApi.isSelfFlag(mCheckUpateHttpApi, flag)) {
				if (mListener != null) {
					mListener.userStopCheckUpdate();
				}
			} else if (mUpdateApkInfo != null
					&& mUpdateApkInfo.getApkUrl().equals(flag)) {
				closeDownloadDialog();

			}

		}

		@Override
		public void error(String flag, HttpResult httpResult) {
			if (HttpApi.isSelfFlag(mCheckUpateHttpApi, flag)) {
				if (mListener != null) {
					mListener.serverConnectFail();
				}
			} else if (mUpdateApkInfo != null
					&& mUpdateApkInfo.getApkUrl().equals(flag)) {
				closeDownloadDialog();
			}

		}
	};

	/**
	 * 显示更新会话框
	 */
	private void showNoticeDialog() {

		// 构造对话框
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle(mContext.getString(R.string.softUpdate));
		builder.setMessage(mContext.getString(R.string.update_prompt));

		// 更新
		builder.setPositiveButton(mContext.getString(R.string.update),
				new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						// 显示下载对话框
						showDownloadDialog();
					}
				});

		// 稍后更新
		builder.setNegativeButton(mContext.getString(R.string.lateUpdate),
				new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

		Dialog noticeDialog = builder.create();

		// 设置 Dialog 的窗口为系统类型，可以用于service中显示。
		noticeDialog.getWindow().setType(
				WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);

		noticeDialog.show();
	}

	/**
	 * 显示软件下载对话框
	 */
	private void showDownloadDialog() {

		if (mUpdateApkInfo == null) {
			return;
		}

		closeDownloadDialog();

		// 构造软件下载对话框
		mProgressDialog = new ProgressDialog(mContext);
		mProgressDialog.setTitle(mContext.getString(R.string.Updating));
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

		// 取消更新
		mProgressDialog.setButton(mContext.getString(R.string.cancel),
				new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();

						stopDownloadApk();
					}
				});
		mProgressDialog.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				boolean click = false;
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					click = true;
					showToast(mContext
							.getString(R.string.clickDialogCancelBtn_prompt));
				}
				return click;

			}
		});

		// 设置 Dialog 的窗口为系统类型，可以用于service中显示。
		mProgressDialog.getWindow().setType(
				WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		mProgressDialog.show();

		// 下载文件
		downloadApk();
	}

	/**
	 * 关闭软件下载对话框
	 */
	private void closeDownloadDialog() {
		if (mProgressDialog != null) {
			mProgressDialog.cancel();
			mProgressDialog = null;
		}
	}

	/**
	 * 以Toast方式提示信息
	 * 
	 * @param msg
	 *            提示的信息
	 * @return void
	 */
	private void showToast(String msg) {
		if (null == mToast) {
			mToast = Toast.makeText(mContext, msg, Toast.LENGTH_SHORT);
		} else {
			mToast.setText(msg);
		}

		mToast.show();
	}

	/**
	 * 下载APK安装文件
	 */
	private void downloadApk() {
		if (mUpdateApkInfo == null) {
			return;
		}

		String url = mUpdateApkInfo.getApkUrl();
		HttpApiDownloadFile httpApiDownloadFile = new HttpApiDownloadFile(
				mContext);
		httpApiDownloadFile.setHttpRequestFlag(url);

		// 判断是否已经有在下载该文件的线程
		if (!httpApiDownloadFile.existHttpRequest()) {
			httpApiDownloadFile.downLoadFile(url,
					mUpdateApkInfo.getDownloadFilePath());
		}
	}

	private void stopDownloadApk() {
		if (mUpdateApkInfo != null) {
			HttpApi.stopHttpRequest(mUpdateApkInfo.getApkUrl());
		}
	}

	public void stop() {

		stopCheckUpateHttpApi();
		stopDownloadApk();
		unregisterHttpResultBroadReceiver();

		if (mToast != null) {
			mToast.cancel();
			mToast = null;
		}
		closeDownloadDialog();
	}

	public interface UpdateManagerListener {

		/**
		 * 开始版本检查监听器
		 */
		void startCheckUpdate();

		/**
		 * 版本检查结束监听器
		 * 
		 * @param isNeedUpdate
		 *            是否需要更新
		 */
		void endCheckUpdate(boolean isNeedUpdate);

		/**
		 * 用户停止版本检测
		 */
		void userStopCheckUpdate();

		/**
		 * 连接服务器出错
		 */
		void serverConnectFail();
	}
}
