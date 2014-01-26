package com.keanbin.framework.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.keanbin.app.R;

/**
 * 点击刷新控件
 * 
 * @author keanbin
 * 
 */
public class PointRefresh extends LinearLayout {

	private LinearLayout mRefreshingLl;
	private LinearLayout mPointRefreshLl;

	private PointRefreshListener mPointRefreshListener;

	public PointRefresh(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public PointRefresh(Context context) {
		super(context);
		initView(context);
	}

	public void initView(Context context) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View layout = (LinearLayout) inflater.inflate(
				R.layout.widget_point_refresh, this, true);

		mRefreshingLl = (LinearLayout) layout
				.findViewById(R.id.ll_widgetPointRefresh_refreshing);
		mPointRefreshLl = (LinearLayout) layout
				.findViewById(R.id.ll_widgetPointRefresh_pointRefresh);

		mPointRefreshLl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startRefresh();
				if (mPointRefreshListener != null) {
					mPointRefreshListener.onClick();
				}
			}
		});
	}

	/**
	 * 开始刷新
	 */
	public void startRefresh() {
		mRefreshingLl.setVisibility(View.VISIBLE);
		mPointRefreshLl.setVisibility(View.GONE);
		this.invalidate();
	}

	/**
	 * 停止刷新
	 */
	public void stopRefresh() {
		mRefreshingLl.setVisibility(View.GONE);
		mPointRefreshLl.setVisibility(View.VISIBLE);
		this.invalidate();
	}

	/**
	 * 设置监听器
	 * 
	 * @param l
	 */
	public void setListener(PointRefreshListener l) {
		mPointRefreshListener = l;
	}

	public interface PointRefreshListener {
		void onClick();
	}

}
