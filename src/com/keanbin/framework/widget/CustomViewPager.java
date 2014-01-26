package com.keanbin.framework.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.keanbin.framework.utils.LogUtil;

/**
 * ViewPager
 * 
 * @author keanbin
 * 
 */
public class CustomViewPager extends ViewPager {

	private static final String TAG = CustomViewPager.class.getCanonicalName();

	/**
	 * 是否滑动换页
	 */
	private boolean mIsSlideSkip;

	public CustomViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		mIsSlideSkip = true;
	}

	public void setIsSlideSkip(boolean b) {
		mIsSlideSkip = b;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {

		if (mIsSlideSkip) {
			// 滑动换页
			boolean ret = super.onInterceptTouchEvent(arg0);
			LogUtil.log(TAG, "onInterceptTouchEvent ret = " + ret);
			return ret;
		} else {
			// 不滑动换页
			return false;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent arg0) {

		if (mIsSlideSkip) {
			// 滑动换页
			boolean ret = super.onTouchEvent(arg0);
			LogUtil.log(TAG, "onTouchEvent ret = " + ret);
			return ret;
		} else {
			// 不滑动换页
			return false;
		}
	}

}
