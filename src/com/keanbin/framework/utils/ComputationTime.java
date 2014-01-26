package com.keanbin.framework.utils;

import android.content.Context;

import com.keanbin.framework.base.BaseClass;

/**
 * 计算时间
 * 
 * @author keanbin
 * 
 */
public class ComputationTime extends BaseClass {

	private Context mContext;
	private long mStartTime;
	private long mEndTime;

	public ComputationTime(Context context) {
		mStartTime = 0;
		mEndTime = 0;
		mContext = context;
	}

	public void start() {
		mStartTime = System.currentTimeMillis();
		log("start time = "
				+ DateUtil.formatTimeTwo(mContext, mStartTime, true));
	}

	public void end() {
		mEndTime = System.currentTimeMillis();
		log("end time = " + DateUtil.formatTimeTwo(mContext, mEndTime, true));
	}

	public String getDuringTime() {

		if (mStartTime == 0 || mEndTime == 0) {
			log("mStartTime = " + mStartTime + ", mEndTime = " + mEndTime);
			return null;
		}

		long duringTime = mEndTime - mStartTime;
		String duringTimeStr = DateUtil.formateDuringTime(duringTime / 1000);

		log("duringTime = " + duringTimeStr);

		return duringTimeStr;
	}
}
