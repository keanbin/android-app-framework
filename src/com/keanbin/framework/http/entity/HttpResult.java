package com.keanbin.framework.http.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.keanbin.framework.base.BaseEntity;

/**
 * 
 * http请求返回的结果
 * 
 * @author keanbin
 */
public class HttpResult extends BaseEntity implements Parcelable {

	/**
	 * 标记没有扩展参数
	 */
	private static final String EXTEND_PARAM_NULL = "ExtendParamNull";

	/**
	 * http请求结果标记:无效
	 */
	public static final int HTTP_REQUEST_INVAILD = 0;

	/**
	 * http请求结果标记:开始
	 */
	public static final int HTTP_PEQUEST_START = 1;

	/**
	 * http请求结果标记:执行过程
	 */
	public static final int HTTP_PEQUEST_PROCESS = 2;

	/**
	 * http请求结果标记:成功
	 */
	public static final int HTTP_REQUEST_SUCCESS = 3;
	/**
	 * http请求结果标记:失败
	 */
	public static final int HTTP_REQUEST_FAIL = 4;
	/**
	 * http请求结果标记:用户主动停止
	 */
	public static final int HTTP_REQUEST_USER_STOP = 5;

	private String flag; // Http请求的标记，必须是唯一的，不存在两个Http请求的标记是相同的。
	private int httpRequestResult; // http请求结果标记
	private String serverResult; // 服务器返回的结果

	private long count; // 文件的总大小，目前只在下载文件中使用。
	private long current; // 当前文件的大小，目前只在下载文件中使用。

	private Parcelable extendParam; // 扩展参数
	private String extendParamClassName; // 扩展参数的类型名称，这个参数不可以让外部设置，是根据extendParam自动生成。

	public HttpResult() {
		this.extendParamClassName = EXTEND_PARAM_NULL;
		this.httpRequestResult = HTTP_REQUEST_INVAILD;
	}

	/**
	 * 获取额外参数
	 * 
	 * @return
	 */
	public Parcelable getExtendParam() {
		return extendParam;
	}

	/**
	 * 设置额外参数
	 * 
	 * @param extendParam
	 */
	public void setExtendParam(Parcelable extendParam) {
		if (extendParam != null) {
			this.extendParam = extendParam;
			this.extendParamClassName = this.extendParam.getClass().getName();
		} else {
			this.extendParamClassName = EXTEND_PARAM_NULL;
		}
	}

	/**
	 * 获取Http请求的结果标记
	 * 
	 * @return
	 */
	public int getHttpRequestResult() {
		return httpRequestResult;
	}

	/**
	 * 设置Http请求的结果标记
	 * 
	 * @param httpRequestResult
	 */
	public void setHttpRequestResult(int httpRequestResult) {
		this.httpRequestResult = httpRequestResult;
	}

	/**
	 * 获取服务器返回的答复
	 * 
	 * @return
	 */
	public String getServerResult() {
		return serverResult;
	}

	/**
	 * 设置服务器返回的答复
	 * 
	 * @param serverResult
	 */
	public void setServerResult(String serverResult) {
		this.serverResult = serverResult;
	}

	/**
	 * 获取文件的总大小
	 * 
	 * @return
	 */
	public long getCount() {
		return count;
	}

	/**
	 * 设置文件的总大小
	 * 
	 * @param count
	 */
	public void setCount(long count) {
		this.count = count;
	}

	/**
	 * 获取文件的当前大小
	 * 
	 * @return
	 */
	public long getCurrent() {
		return current;
	}

	/**
	 * 设置文件的当前大小
	 * 
	 * @param current
	 */
	public void setCurrent(long current) {
		this.current = current;
	}

	/**
	 * 获取Http请求的标记
	 * 
	 * @return
	 */
	public String getFlag() {
		return flag;
	}

	/**
	 * 设置Http请求的标记，必须是唯一的，没有两个Http请求的标记是相同的。
	 * 
	 * @param flag
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	@Override
	public String toString() {
		return "HttpResult [flag=" + flag + ", httpRequestResult="
				+ httpRequestResult + ", serverResult=" + serverResult
				+ ", count=" + count + ", current=" + current
				+ ", extendParam=" + extendParam + ", extendParamClassName="
				+ extendParamClassName + "]";
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(flag);
		dest.writeInt(httpRequestResult);
		dest.writeString(serverResult);
		dest.writeLong(count);
		dest.writeLong(current);

		dest.writeString(extendParamClassName);
		if (extendParamClassName != EXTEND_PARAM_NULL) {
			dest.writeParcelable(extendParam, flags);
		}

	}

	public static final Parcelable.Creator<HttpResult> CREATOR = new Creator<HttpResult>() {

		@Override
		public HttpResult createFromParcel(Parcel source) {
			HttpResult httpResult = new HttpResult();

			httpResult.flag = source.readString();
			httpResult.httpRequestResult = source.readInt();
			httpResult.serverResult = source.readString();
			httpResult.count = source.readLong();
			httpResult.current = source.readLong();

			// 获取扩展参数
			httpResult.extendParamClassName = source.readString();
			if (!httpResult.extendParamClassName.equals(EXTEND_PARAM_NULL)) {
				try {
					Class c = Class.forName(httpResult.extendParamClassName);
					httpResult.extendParam = source.readParcelable(c
							.getClassLoader());
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}

			return httpResult;
		}

		@Override
		public HttpResult[] newArray(int size) {
			return new HttpResult[size];
		}

	};

}
