package com.keanbin.framework.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.keanbin.app.R;

/**
 * 未读消息的提示图标控件
 * 
 * @author keanbin
 * 
 */
public class UnreadIcon extends LinearLayout {

	private LinearLayout mRootLl;
	private TextView mNumberTv;

	private Context mContext;

	public UnreadIcon(Context context) {
		super(context);

		initView(context);
	}

	public UnreadIcon(Context context, AttributeSet attrs) {
		super(context, attrs);

		initView(context);
	}

	/**
	 * 初始化布局
	 * 
	 * @return
	 */
	private void initView(Context context) {
		mContext = context;

		View v = LayoutInflater.from(mContext).inflate(
				R.layout.widget_unread_icon, this, true);

		mRootLl = (LinearLayout) v.findViewById(R.id.ll_widgetUnreadIcon_root);
		mNumberTv = (TextView) v.findViewById(R.id.tv_widgetUnreadIcon_number);
	}

	/**
	 * 设置未读数量
	 * 
	 * @param number
	 */
	public void setNumber(long number) {
		if (number > 0) {
			mNumberTv.setText("" + number);
			mNumberTv.setVisibility(View.VISIBLE);
			mRootLl.setBackgroundResource(R.drawable.icon_unread_bg);
		} else {
			mNumberTv.setVisibility(View.GONE);
			mRootLl.setBackgroundResource(R.drawable.icon_unread_bg02);
		}
	}

}
