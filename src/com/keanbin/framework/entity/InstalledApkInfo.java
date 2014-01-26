package com.keanbin.framework.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.keanbin.framework.base.BaseEntity;

/**
 * 已经安装的apk信息
 * 
 * @author keanbin
 * 
 */
public class InstalledApkInfo extends BaseEntity implements Parcelable {

	private String appName;
	private String packageName;
	private String versionName;
	private int versionCode;

	
	
	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public int getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}
	
	@Override
	public String toString() {
		return "InstalledApkInfo [appName=" + appName + ", packageName="
				+ packageName + ", versionName=" + versionName
				+ ", versionCode=" + versionCode + "]";
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(appName);
		dest.writeString(packageName);
		dest.writeString(versionName);
		dest.writeInt(versionCode);

	}

	public static final Parcelable.Creator<InstalledApkInfo> CREATOR = new Creator<InstalledApkInfo>() {

		@Override
		public InstalledApkInfo createFromParcel(Parcel source) {
			InstalledApkInfo installedApkInfo = new InstalledApkInfo();

			installedApkInfo.appName = source.readString();
			installedApkInfo.packageName = source.readString();
			installedApkInfo.versionName = source.readString();
			installedApkInfo.versionCode = source.readInt();

			return installedApkInfo;
		}

		@Override
		public InstalledApkInfo[] newArray(int size) {
			return new InstalledApkInfo[size];
		}

	};
}
