package com.keanbin.framework.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.keanbin.app.R;

/**
 * 询问对话框
 * 
 * @author keanbin
 * 
 */
public class AskAboutDialog extends Dialog {
	private TextView mTitleTv; // 标题
	private TextView mDescribeTv; // 描述
	private Button mNoBtn; // 不按钮
	private Button mYesBtn; // 是按钮

	private AskAboutDialogOnClickListener mNoBtnOnClickListener;
	private AskAboutDialogOnClickListener mYesBtnOnClickListener;

	public static final int VIEW_FLAG_NO_BTN = 1;
	public static final int VIEW_FLAG_YES_BTN = 2;

	public AskAboutDialog(Context context, int style) {
		super(context, style);
		setContentView(R.layout.widget_ask_about_dialog);

		mTitleTv = (TextView) findViewById(R.id.tv_askAboutDialog_title);
		mDescribeTv = (TextView) findViewById(R.id.tv_askAboutDialog_describe);
		mNoBtn = (Button) findViewById(R.id.btn_askAboutDialog_no);
		mNoBtn.setOnClickListener(mViewOnClickListener);
		mYesBtn = (Button) findViewById(R.id.btn_askAboutDialog_yes);
		mYesBtn.setOnClickListener(mViewOnClickListener);
	}

	public void setTitle(String title) {
		mTitleTv.setText(title);
		mTitleTv.setVisibility(View.VISIBLE);
	}

	public void setDescribe(String describe) {
		mDescribeTv.setText(describe);
		mDescribeTv.setVisibility(View.VISIBLE);
	}

	/**
	 * @Description 设置“否”按钮
	 * @param str
	 *            按钮显示的文本
	 * @param l
	 *            按钮的点击监听器
	 * @return
	 */
	public void setNoBtn(String str, AskAboutDialogOnClickListener l) {
		mNoBtn.setText(str);
		mNoBtnOnClickListener = l;
	}

	/**
	 * @Description 设置“否”按钮
	 * @param str
	 *            按钮显示的文本
	 * @return
	 */
	public void setNoBtn(String str) {
		mNoBtn.setText(str);
	}

	/**
	 * @Description 设置“否”按钮的点击监听器
	 * @param l
	 *            按钮的点击监听器
	 * @return
	 */
	public void setNoBtn(AskAboutDialogOnClickListener l) {
		mNoBtnOnClickListener = l;
	}

	/**
	 * @Description 设置“是”按钮
	 * @param str
	 *            按钮显示的文本
	 * @param l
	 *            按钮的点击监听器
	 * @return
	 */
	public void setYesBtn(String str, AskAboutDialogOnClickListener l) {
		mYesBtn.setText(str);
		mYesBtnOnClickListener = l;
	}

	/**
	 * @Description 设置“是”按钮的点击监听器
	 * @param l
	 *            按钮的点击监听器
	 * @return
	 */
	public void setYesBtn(AskAboutDialogOnClickListener l) {
		mYesBtnOnClickListener = l;
	}

	private android.view.View.OnClickListener mViewOnClickListener = new android.view.View.OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_askAboutDialog_no:
				if (mNoBtnOnClickListener != null) {
					mNoBtnOnClickListener.onClick(VIEW_FLAG_NO_BTN);
				} else {
					cancel();
				}
				break;

			case R.id.btn_askAboutDialog_yes: {
				if (mYesBtnOnClickListener != null) {
					mYesBtnOnClickListener.onClick(VIEW_FLAG_YES_BTN);
				} else {
					cancel();
				}
			}
				break;

			default:
				break;
			}

		}
	};

	public interface AskAboutDialogOnClickListener {
		public void onClick(int viewFlag);
	}

}
