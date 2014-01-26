package com.keanbin.framework.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.keanbin.app.R;
import com.keanbin.framework.FrameworkApplication;
import com.keanbin.framework.http.entity.HttpResult;
import com.keanbin.framework.http.frame.HttpResultBroadReceiver;
import com.keanbin.framework.http.frame.HttpResultBroadReceiver.HttpRequestBroadReceiverListener;
import com.keanbin.framework.utils.CommonConfig;
import com.keanbin.framework.utils.LogUtil;
import com.keanbin.framework.widget.TitleBar;
import com.keanbin.framework.widget.TitleBar.LeftBtnOnClickListener;
import com.keanbin.framework.widget.TitleBar.LeftImgBtnOnClickListener;
import com.keanbin.framework.widget.TitleBar.RightBtnOnClickListener;
import com.keanbin.framework.widget.TitleBar.RightImgBtnOnClickListener;

/**
 * @ClassName BaseActivity 基本的Activity
 * @author kevin
 */
public class BaseActivity extends Activity implements BaseLog {

	protected static final boolean DEBUG = CommonConfig.DEBUG;

	private String TAG;

	/**
	 * Toast弹出框
	 */
	private Toast mToast = null;

	/**
	 * 旋转对话框ProgressDialog
	 */
	private ProgressDialog mProgressDialog = null;

	protected Context mContext;

	/**
	 * http请求结果的广播接收器
	 */
	private HttpResultBroadReceiver mHttpResultBroadReceiver;

	/**
	 * 是否自动添加标题栏
	 */
	protected boolean mIsAutoAddTitle;

	/**
	 * 标题栏
	 */
	protected TitleBar mTitleBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		TAG = this.getClass().getName();

		FrameworkApplication application = (FrameworkApplication) getApplicationContext();
		application.onActivityCreate(this);

		initData();

		// 注册广播接收器，接收Http请求返回的结果
		registerHttpResultBroadReceiver();
	}

	@Override
	public void setContentView(int layoutResID) {
		LinearLayout li = new LinearLayout(this);
		li.setOrientation(LinearLayout.VERTICAL);
		LinearLayout.LayoutParams titleLayoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		if (mIsAutoAddTitle) {
			mTitleBar = new TitleBar(this);
			li.addView(mTitleBar, titleLayoutParams);
		}

		LinearLayout.LayoutParams contentLayoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		li.addView(LayoutInflater.from(this).inflate(layoutResID, null),
				contentLayoutParams);
		super.setContentView(li);
	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		if (mToast != null) {
			mToast.cancel();
			mToast = null;
		}

		closeProgressDialog();

		// 注销广播接收器
		unregisterHttpResultBroadReceiver();

		FrameworkApplication application = (FrameworkApplication) getApplicationContext();
		application.onActivityDestroy(this);
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		mIsAutoAddTitle = true;
	}

	/**
	 * 设置是否自动添加标题栏，默认是true。该函数必须在setContentView()之前调用，否则无效。
	 * 
	 * @param b
	 */
	protected void setIsAutoAddTitle(boolean b) {
		mIsAutoAddTitle = b;
	}

	/**
	 * 从本activity启动toActivity
	 * 
	 * @param toActivity
	 *            启动的activity类名
	 * @return void
	 */
	protected void startActivity(Class<? extends Activity> toActivity) {

		if (toActivity == null) {
			log("transfer : toActivity == null");
			return;
		}

		Intent intent = new Intent(mContext, toActivity);

		startActivity(intent);
	}

	/**
	 * 从本activity启动toActivity
	 * 
	 * @param toActivity
	 *            启动的activity类名
	 * @param requestCode
	 *            当toActivity退出时，本activity收到返回标记
	 * @return void
	 */
	protected void startActivityForResult(Class<? extends Activity> toActivity,
			int requestCode) {

		if (requestCode < 0) {
			log("transferForResult : requestCode < 0  is error!");
			return;
		}

		if (toActivity == null) {
			log("transferForResult : toActivity == null");
			return;
		}

		Intent intent = new Intent(mContext, toActivity);

		startActivityForResult(intent, requestCode);
	}

	/**
	 * 以Toast方式提示信息
	 * 
	 * @param msg
	 *            提示的信息
	 * @return void
	 */
	public void showToast(String msg) {
		if (null == mToast) {
			mToast = Toast.makeText(mContext, msg, Toast.LENGTH_SHORT);
		} else {
			mToast.setText(msg);
		}

		mToast.show();
	}

	@Override
	public void log(String msg) {
		LogUtil.log(TAG, msg);
	}

	@Override
	public void log(String msg, Throwable e) {
		LogUtil.log(TAG, msg, e);
	}

	@Override
	public void logError(String msg) {
		LogUtil.errorLog(TAG, msg);
	}

	@Override
	public void logError(String msg, Throwable e) {
		LogUtil.errorLog(TAG, msg, e);
	}

	@Override
	public void logError(Throwable e) {
		LogUtil.errorLog(TAG, e);
	}

	/**
	 * 显示等待对话框ProgressDialog
	 * 
	 * @param msg
	 *            提示信息
	 * @param OnKeyListener
	 *            按键监听器
	 * @return void
	 */
	protected void showProgressDialog(String msg, OnKeyListener l) {
		if (mProgressDialog == null) {
			mProgressDialog = new ProgressDialog(this);
		}
		mProgressDialog.setMessage(msg);
		if (l != null) {
			mProgressDialog.setOnKeyListener(l);
		}
		mProgressDialog.show();
	}

	/**
	 * 显示等待对话框ProgressDialog
	 * 
	 * @param OnKeyListener
	 *            按键监听器
	 * @return void
	 */
	protected void showProgressDialog(OnKeyListener l) {

		showProgressDialog(
				getString(R.string.defaultActivityProgressDialogPrompt), l);
	}

	/**
	 * 显示等待对话框ProgressDialog
	 * 
	 * @param msg
	 *            提示信息
	 * @return void
	 */
	protected void showProgressDialog(String msg) {
		showProgressDialog(msg, null);
	}

	/**
	 * 显示等待对话框ProgressDialog
	 * 
	 * @return void
	 */
	protected void showProgressDialog() {
		showProgressDialog(
				getString(R.string.defaultActivityProgressDialogPrompt), null);
	}

	/**
	 * 关闭等待对话框ProgressDialog
	 * 
	 * @return void
	 */
	protected void closeProgressDialog() {
		if (mProgressDialog != null && mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
			mProgressDialog = null;
		}
	}

	/**
	 * 获取屏幕密度
	 * 
	 * @return
	 */
	public int getScreenDp() {
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		return metric.densityDpi;
	}

	// ------------------ HttpResultBroadReceiver ------------------
	/**
	 * 注册广播接收器，接收Http请求返回的结果
	 */
	private void registerHttpResultBroadReceiver() {
		mHttpResultBroadReceiver = new HttpResultBroadReceiver(mContext,
				mHttpRequestBroadReceiverListener);
		mHttpResultBroadReceiver.registerHttpResultBroadReceiver();
	}

	/**
	 * 注销广播接收器
	 */
	private void unregisterHttpResultBroadReceiver() {
		if (mHttpResultBroadReceiver != null) {
			mHttpResultBroadReceiver.unregisterHttpResultBroadReceiver();
			mHttpResultBroadReceiver = null;
		}
	}

	private HttpRequestBroadReceiverListener mHttpRequestBroadReceiverListener = new HttpRequestBroadReceiverListener() {

		@Override
		public void start(String flag, HttpResult httpResult) {
			onHttpRequestStart(flag, httpResult);
		}

		@Override
		public void process(String flag, long count, long current,
				HttpResult httpResult) {

			onHttpRequestProcess(flag, count, current, httpResult);
		}

		@Override
		public void result(String flag, String result, HttpResult httpResult) {
			onHttpRequestReceiveResult(flag, result, httpResult);
		}

		@Override
		public void userStop(String flag, HttpResult httpResult) {
			onHttpRequestUserStop(flag, httpResult);

		}

		@Override
		public void error(String flag, HttpResult httpResult) {
			onHttpRequestError(flag, httpResult);
		}

	};

	/**
	 * 开始Http请求
	 * 
	 * @param flag
	 *            Http请求的标记，是唯一的，不存在两个Http请求的标记是相同的。
	 * @param httpResult
	 */
	protected void onHttpRequestStart(String flag, HttpResult httpResult) {

	}

	/**
	 * Http请求执行过程中，目前只在下载文件中使用。
	 * 
	 * @param flag
	 *            Http请求的标记，是唯一的，不存在两个Http请求的标记是相同的。
	 * @param count
	 *            文件总大小
	 * @param current
	 *            文件当前大小
	 * @param httpResult
	 *            http请求返回的结果
	 */
	protected void onHttpRequestProcess(String flag, long count, long current,
			HttpResult httpResult) {

	}

	/**
	 * 收到服务器返回的答复
	 * 
	 * @param flag
	 *            Http请求的标记，是唯一的，不存在两个Http请求的标记是相同的。
	 * @param result
	 *            服务器返回的答复
	 * @param httpResult
	 */
	protected void onHttpRequestReceiveResult(String flag, String result,
			HttpResult httpResult) {

	}

	/**
	 * 用户主动停止
	 * 
	 * @param flag
	 *            Http请求的标记，是唯一的，不存在两个Http请求的标记是相同的。
	 * @param httpResult
	 * 
	 */
	protected void onHttpRequestUserStop(String flag, HttpResult httpResult) {

	}

	/**
	 * http请求出错
	 * 
	 * @param flag
	 *            Http请求的标记，是唯一的，不存在两个Http请求的标记是相同的。
	 * @param httpResult
	 * 
	 */
	protected void onHttpRequestError(String flag, HttpResult httpResult) {

	}

	// ------------------ 标题栏操作 ------------------
	/**
	 * 重置标题栏
	 */
	protected void resetTitleBar() {
		if (mTitleBar == null) {
			return;
		}

		mTitleBar.reset();

	}

	/**
	 * 设置标题栏背景
	 * 
	 * @param bgRest
	 */
	protected void setTitleBarBg(int bgRest) {
		if (mTitleBar == null) {
			return;
		}

		mTitleBar.setTitleBarBg(bgRest);
	}

	/**
	 * 设置标题栏标题
	 * 
	 * @param text
	 */
	protected void setTitleBarText(String text) {
		if (mTitleBar == null) {
			return;
		}

		mTitleBar.setTitleText(text);
	}

	/**
	 * 
	 * 显示标题栏左边的箭头图片
	 * 
	 */
	protected void showTitleBarLeftImage() {
		showTitleBarLeftImage(R.drawable.title_left_arrows_bg,
				new LeftImgBtnOnClickListener() {

					@Override
					public void onClick() {
						finish();
					}
				});
	}

	/**
	 * 显示标题栏左边的箭头图片
	 * 
	 * @param l
	 *            点击监听器
	 */
	protected void showTitleBarLeftImage(LeftImgBtnOnClickListener l) {
		showTitleBarLeftImage(R.drawable.title_left_arrows_bg, l);
	}

	/**
	 * 显示标题栏左边的图片
	 * 
	 * @param imgResid
	 * @param l
	 *            点击监听器
	 */
	protected void showTitleBarLeftImage(int imgResid,
			LeftImgBtnOnClickListener l) {
		if (mTitleBar == null) {
			return;
		}

		mTitleBar.setLeftImgBtn(imgResid, l);
		mTitleBar.showLeftImgBtn(true);
	}

	/**
	 * 显示标题栏左边的按钮
	 * 
	 * @param text
	 *            按钮的文本
	 * @param l
	 *            点击监听器
	 */
	protected void showTitleBarLeftBtn(String text, LeftBtnOnClickListener l) {
		showTitleBarLeftBtn(text, R.drawable.title_btn_bg, l);
	}

	/**
	 * 显示标题栏左边的按钮
	 * 
	 * @param text
	 *            按钮的文本
	 * @param bgResid
	 *            按钮的背景
	 * @param l
	 *            点击监听器
	 */
	protected void showTitleBarLeftBtn(String text, int bgResid,
			LeftBtnOnClickListener l) {
		if (mTitleBar == null) {
			return;
		}

		mTitleBar.setLeftBtn(text, bgResid, l);
		mTitleBar.showLeftBtn(true);
	}

	/**
	 * 显示标题栏右边的图片
	 * 
	 * @param imgResid
	 *            图片
	 * @param l
	 *            点击监听器
	 */
	protected void showTitleBarRightImage(int imgResid,
			RightImgBtnOnClickListener l) {
		if (mTitleBar == null) {
			return;
		}

		mTitleBar.setRightImgBtn(imgResid, l);
		mTitleBar.showRightImgBtn(true);
	}

	/**
	 * 显示标题栏右边的按钮
	 * 
	 * @param text
	 *            按钮的文本
	 * @param l
	 *            点击监听器
	 */
	protected void showTitleBarRightBtn(String text, RightBtnOnClickListener l) {
		showTitleBarRightBtn(text, R.drawable.title_btn_bg, l);
	}

	/**
	 * 显示标题栏右边的按钮
	 * 
	 * @param text
	 *            按钮的文本
	 * @param bgResid
	 *            按钮的背景
	 * @param l
	 *            点击监听器
	 */
	protected void showTitleBarRightBtn(String text, int bgResid,
			RightBtnOnClickListener l) {
		if (mTitleBar == null) {
			return;
		}

		mTitleBar.setRightBtn(text, bgResid, l);
		mTitleBar.showRightBtn(true);
	}

	/**
	 * 设置标题栏左边旋转动画是否显示
	 * 
	 * @param b
	 */
	protected void showTitleBarLeftRotateAnim(boolean b) {
		if (mTitleBar == null) {
			return;
		}

		mTitleBar.showLeftRotateAnim(b);
	}

	/**
	 * 显示标题栏左边旋转动画
	 * 
	 * @param text
	 *            旋转动画的文本
	 */
	protected void showTitleBarLeftRotateAnim(String text) {
		if (mTitleBar == null) {
			return;
		}

		mTitleBar.setLeftRotateAnimText(text);
		mTitleBar.showLeftRotateAnim(true);
	}

	/**
	 * 设置标题栏右边旋转动画是否显示
	 * 
	 * @param b
	 */
	protected void showTitleBarRightRotateAnim(boolean b) {
		if (mTitleBar == null) {
			return;
		}

		mTitleBar.showRightRotateAnim(b);
	}

	/**
	 * 显示标题栏右边旋转动画
	 * 
	 * @param text
	 *            旋转动画的文本
	 */
	protected void showTitleBarRightRotateAnim(String text) {
		if (mTitleBar == null) {
			return;
		}

		mTitleBar.setRightRotateAnimText(text);
		mTitleBar.showRightRotateAnim(true);
	}

}
