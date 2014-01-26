package com.keanbin.app.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.keanbin.app.R;
import com.keanbin.framework.widget.TitleBar;

/**
 * 赚金币分页
 * 
 * @author kevin
 */
public class TwoTabPage extends LinearLayout {

	public static final String TAG = OneTabPage.class.getCanonicalName();

	private Toast mToast = null;
	private TitleBar mTitleBar;

	private Context mContext;
	private boolean isInitContentData; // 是否初始化内容数据
	private boolean mIsDispTitleAnim;// 是否显示标题栏的动画

	public TwoTabPage(Context context) {
		super(context);

		init(context);
	}

	/**
	 * 初始化
	 * 
	 * @param context
	 */
	private void init(Context context) {
		initData(context);
		initView();

	}

	/**
	 * @Description 初始化数据
	 * @return
	 */
	private void initData(Context context) {
		mContext = context;

		isInitContentData = false;
		mIsDispTitleAnim = false;
	}

	/**
	 * @Description 初始化页面
	 * @return
	 */
	private void initView() {

		LayoutInflater inflater = LayoutInflater.from(mContext);
		View viewPage = inflater.inflate(R.layout.widget_tab_02, null);

		mTitleBar = (TitleBar) viewPage
				.findViewById(R.id.titleBar_widgetTab02_title);
		mTitleBar.setTitleText("第二页");

		ViewGroup.LayoutParams la = new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		addView(viewPage, la);
	}

	/**
	 * 页面恢复时调用
	 */
	public void onResume() {

		if (mIsDispTitleAnim) {
			showTitleRotateAnim();
		}
	}

	/**
	 * 页面销毁时调用
	 */
	public void onDestroy() {
		if (mToast != null) {
			mToast.cancel();
			mToast = null;
		}

	}

	/**
	 * 初始化内容数据
	 */
	public void initContentData() {
		if (!isInitContentData) {
			isInitContentData = true;

		}
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

	/**
	 * 显示标题栏的旋转动画
	 */
	public void showTitleRotateAnim() {
		if (mTitleBar == null) {
			return;
		}

		// mTitleBar.showLeftRotateAnim(true);
		mIsDispTitleAnim = true;
	}

	/**
	 * 隐藏标题栏的旋转动画
	 */
	public void hideTitleRotateAnim() {
		if (mTitleBar == null) {
			return;
		}

		// mTitleBar.showLeftRotateAnim(false);
		mIsDispTitleAnim = false;
	}

}
