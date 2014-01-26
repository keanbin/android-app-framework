package com.keanbin.framework.http.entity;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

import com.keanbin.framework.base.BaseEntity;
import com.keanbin.framework.utils.FileUtil;
import com.keanbin.framework.utils.JsonUtil;
import com.keanbin.framework.utils.LogUtil;
import com.keanbin.framework.utils.StringUtil;

/**
 * 下载的文件的信息
 * 
 * @author keanbin
 */
public class DownloadFileInfo extends BaseEntity implements Parcelable {

	private static final String TAG = DownloadFileInfo.class.getCanonicalName();

	/**
	 * 下载信息文件的后缀
	 */
	private static final String DOWNLOAD_INFO_FILE_POSTFIX = ".dif";

	/**
	 * Json格式的字段：文件总大小
	 */
	private static final String JSON_FIELD_TOTAL_SIZE = "totalsize";

	private long totalSize; // 总大小

	public long getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(long totalSize) {
		this.totalSize = totalSize;
	}

	@Override
	public boolean isError() {
		return totalSize <= 0;
	}

	@Override
	public String toString() {
		return "DownloadFileInfo [totalSize=" + totalSize + "]";
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(totalSize);

	}

	public static final Parcelable.Creator<DownloadFileInfo> CREATOR = new Creator<DownloadFileInfo>() {

		@Override
		public DownloadFileInfo createFromParcel(Parcel source) {
			DownloadFileInfo downloadFileInfo = new DownloadFileInfo();

			downloadFileInfo.totalSize = source.readLong();

			return downloadFileInfo;
		}

		@Override
		public DownloadFileInfo[] newArray(int size) {
			return new DownloadFileInfo[size];
		}

	};

	/**
	 * 生成json格式字符串
	 * 
	 * @return
	 */
	public String toJson() {

		if (isError()) {
			return null;
		}

		JSONObject obj = new JSONObject();

		try {
			obj.put(JSON_FIELD_TOTAL_SIZE, totalSize);

			return obj.toString();
		} catch (JSONException e) {
			logError(e);
		}

		return null;
	}

	/**
	 * 生成Json格式的信息文件
	 * 
	 * @param downloadFilePath
	 *            下载的文件的路径
	 * @return
	 */
	public boolean toJsonFile(String downloadFilePath) {
		String filePath = getDownloadInfoFilePath(downloadFilePath);
		if (StringUtil.isEmpty(filePath)) {
			return false;
		}

		String jsonStr = toJson();
		if (StringUtil.isEmpty(jsonStr)) {
			return false;
		}

		return FileUtil.write(filePath, jsonStr.getBytes());
	}

	/**
	 * 获取下载信息文件的路径
	 * 
	 * @param downloadFilePath
	 *            下载文件的路径
	 * @return
	 */
	public static String getDownloadInfoFilePath(String downloadFilePath) {
		if (StringUtil.isEmpty(downloadFilePath)) {
			return null;
		}

		return downloadFilePath + DOWNLOAD_INFO_FILE_POSTFIX;
	}

	/**
	 * 解析DownloadFileInfo的json格式的数据。json的格式：{"totalsize":"1024"}
	 * 
	 * @param obj
	 * @return DownloadFileInfo对象
	 * @throws JSONException
	 */
	public static DownloadFileInfo jsonParse(JSONObject obj)
			throws JSONException {
		if (obj == null) {
			return null;
		}

		DownloadFileInfo downloadFileInfo = new DownloadFileInfo();
		downloadFileInfo.setTotalSize(JsonUtil.getLong(obj,
				JSON_FIELD_TOTAL_SIZE));

		if (downloadFileInfo.isError()) {
			return null;
		}

		return downloadFileInfo;
	}

	/**
	 * 从配置文件中创建DownloadFileInfo对象
	 * 
	 * @param downloadFilePath
	 *            下载的文件的路径
	 * @return DownloadFileInfo对象
	 */
	public static DownloadFileInfo fromJsonFile(String downloadFilePath) {
		if (StringUtil.isEmpty(downloadFilePath)) {
			return null;
		}

		String filePath = getDownloadInfoFilePath(downloadFilePath);
		if (StringUtil.isEmpty(filePath)) {
			return null;
		}

		String jsonStr = FileUtil.readToString(filePath);
		if (StringUtil.isEmpty(jsonStr)) {
			return null;
		}

		try {
			JSONObject obj = new JSONObject(jsonStr);
			return jsonParse(obj);
		} catch (JSONException e) {
			LogUtil.errorLog(TAG, e);
		}

		return null;
	}

	/**
	 * 删除下载信息的信息文件
	 * 
	 * @param downloadFilePath
	 *            下载文件的路径
	 * @return
	 */
	public static boolean deleteDownloadInfoFile(String downloadFilePath) {
		return FileUtil.deleteFile(getDownloadInfoFilePath(downloadFilePath));
	}

	/**
	 * 从下载信息文件中获取文件的总大小
	 * 
	 * @param downloadFilePath
	 * @return
	 */
	public static long getTotalSize(String downloadFilePath) {
		DownloadFileInfo downloadFileInfo = fromJsonFile(downloadFilePath);
		if (downloadFileInfo == null) {
			return 0;
		}

		return downloadFileInfo.getTotalSize();

	}

}
