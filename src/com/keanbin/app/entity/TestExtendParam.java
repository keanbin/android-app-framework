package com.keanbin.app.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.keanbin.framework.base.BaseEntity;

/**
 * @ClassName HttpResult
 * @Description http请求返回的结果
 * @author kevin
 */
public class TestExtendParam extends BaseEntity implements Parcelable {

	private long time; // Http请求的时间
	private String result; // 服务器返回的结果

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "TestExtendParam [time=" + time + ", result=" + result + "]";
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(time);
		dest.writeString(result);

	}

	public static final Parcelable.Creator<TestExtendParam> CREATOR = new Creator<TestExtendParam>() {

		@Override
		public TestExtendParam createFromParcel(Parcel source) {
			TestExtendParam httpResult = new TestExtendParam();

			httpResult.time = source.readLong();
			httpResult.result = source.readString();

			return httpResult;
		}

		@Override
		public TestExtendParam[] newArray(int size) {
			return new TestExtendParam[size];
		}

	};

}
