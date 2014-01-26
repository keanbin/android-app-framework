package com.keanbin.app.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.keanbin.framework.base.BaseEntity;

/**
 * 分页获取列表的http请求扩展参数
 * 
 * @author kevin
 */
public class PagingGetListHttpExtendParam extends BaseEntity implements
		Parcelable {

	private int pageNumber; // 页码

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(pageNumber);

	}

	public static final Parcelable.Creator<PagingGetListHttpExtendParam> CREATOR = new Creator<PagingGetListHttpExtendParam>() {

		@Override
		public PagingGetListHttpExtendParam createFromParcel(Parcel source) {
			PagingGetListHttpExtendParam httpResult = new PagingGetListHttpExtendParam();

			httpResult.pageNumber = source.readInt();

			return httpResult;
		}

		@Override
		public PagingGetListHttpExtendParam[] newArray(int size) {
			return new PagingGetListHttpExtendParam[size];
		}

	};

}
