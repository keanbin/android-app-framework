package com.keanbin.framework.http.entity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

import com.keanbin.framework.base.BaseEntity;
import com.keanbin.framework.utils.CommonConfig;
import com.keanbin.framework.utils.FileUtil;
import com.keanbin.framework.utils.JsonUtil;
import com.keanbin.framework.utils.StringUtil;

/**
 * 更新的Apk信息
 * 
 * @author keanbin
 * 
 */
public class UpdateApkInfo extends BaseEntity implements Parcelable {

	/**
	 * Json格式的字段：版本号
	 */
	private static final String JSON_FIELD_VERSION_ID = "VersionId";
	/**
	 * Json格式的字段：apk下载地址
	 */
	private static final String JSON_FIELD_APK_URL = "ApkUrl";
	/**
	 * Json格式的字段：apk的文件名
	 */
	private static final String JSON_FIELD_APK_FILE_NAME = "ApkFileName";
	/**
	 * Json格式的字段：应用的名称
	 */
	private static final String JSON_FIELD_APP_NAME = "AppName";

	int versionId; // 版本号
	String apkUrl; // apk地址
	String apkFileName; // apkFileName
	String appName; // 应用名称

	public int getVersionId() {
		return versionId;
	}

	public void setVersionId(int versionId) {
		this.versionId = versionId;
	}

	public String getApkUrl() {
		return apkUrl;
	}

	public void setApkUrl(String apkUrl) {
		this.apkUrl = apkUrl;
	}

	public String getApkFileName() {
		return apkFileName;
	}

	public void setApkFileName(String apkFileName) {
		this.apkFileName = apkFileName;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	@Override
	public boolean isError() {

		if (versionId < 0 || StringUtil.isEmpty(apkUrl)) {
			return true;
		}

		return false;
	}

	@Override
	public String toString() {
		return "UpdateApkInfo [versionId=" + versionId + ", apkUrl=" + apkUrl
				+ ", apkFileName=" + apkFileName + ", appName=" + appName + "]";
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(versionId);
		dest.writeString(apkUrl);
		dest.writeString(apkFileName);
		dest.writeString(appName);
	}

	public static final Parcelable.Creator<UpdateApkInfo> CREATOR = new Creator<UpdateApkInfo>() {

		@Override
		public UpdateApkInfo createFromParcel(Parcel source) {
			UpdateApkInfo updateApkInfo = new UpdateApkInfo();
			updateApkInfo.versionId = source.readInt();
			updateApkInfo.apkUrl = source.readString();
			updateApkInfo.apkFileName = source.readString();
			updateApkInfo.appName = source.readString();

			return updateApkInfo;
		}

		@Override
		public UpdateApkInfo[] newArray(int size) {
			return new UpdateApkInfo[size];
		}

	};

	/**
	 * 解析一个apk的JSON格式的数据
	 * 
	 * @param obj
	 *            一个apk的JSON格式的数据
	 * @return apk信息对象
	 * @throws JSONException
	 */
	public static UpdateApkInfo jsonApkInfo(JSONObject obj)
			throws JSONException {
		if (obj == null) {
			return null;
		}

		UpdateApkInfo updateApkInfo = new UpdateApkInfo();
		updateApkInfo.setApkFileName(JsonUtil.getString(obj,
				JSON_FIELD_APK_FILE_NAME));
		updateApkInfo.setApkUrl(JsonUtil.getString(obj, JSON_FIELD_APK_URL));
		updateApkInfo.setVersionId(JsonUtil.getInt(obj, JSON_FIELD_VERSION_ID));
		updateApkInfo.setAppName(JsonUtil.getString(obj, JSON_FIELD_APP_NAME));

		if (updateApkInfo.isError()) {
			return null;
		}

		return updateApkInfo;
	}

	/**
	 * 解析多个apk的JSON格式的数据
	 * 
	 * @param obj
	 *            多个apk的JSON格式的数据
	 * @return 多个apk数据对象的列表
	 * @throws JSONException
	 */
	public static List<UpdateApkInfo> jsonApkInfoList(JSONArray array)
			throws JSONException {

		if (array == null || array.length() == 0) {
			return null;
		}

		List<UpdateApkInfo> updateApkInfoList = new ArrayList<UpdateApkInfo>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject obj = array.getJSONObject(i);

			UpdateApkInfo updateApkInfo = jsonApkInfo(obj);

			if (updateApkInfo != null) {
				updateApkInfoList.add(updateApkInfo);
			}
		}

		return updateApkInfoList;
	}

	/**
	 * 获取apk的保存路径
	 * 
	 * @return
	 */
	public String getDownloadFilePath() {
		if (apkUrl == null) {
			return null;
		}

		String dirPath = FileUtil.getExtendedStorePath()
				+ CommonConfig.APK_DOWNLOAD_PATH;
		File file = new File(dirPath);
		if (!file.exists() || !file.isDirectory()) {
			file.mkdirs();
		}

		if (!file.exists() || !file.isDirectory()) {
			return null;
		}

		String fileName = null;
		if (StringUtil.isEmpty(apkFileName)) {
			fileName = apkUrl.substring(apkUrl.lastIndexOf(File.separator) + 1);
		} else {
			fileName = apkFileName;
		}
		return dirPath + fileName;
	}
}
