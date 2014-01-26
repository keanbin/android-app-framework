package com.keanbin.app.entity;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.keanbin.framework.base.BaseEntity;
import com.keanbin.framework.utils.JsonUtil;

/**
 * 收益记录
 * 
 * @author keanbin
 * 
 */
public class ListTemplateInfo extends BaseEntity implements Parcelable {

	/**
	 * Json格式的字段：记录ID
	 */
	private static final String JSON_FIELD_ID = "id";
	/**
	 * Json格式的字段：图片网址
	 */
	private static final String JSON_FIELD_ICON = "icon";
	/**
	 * Json格式的字段：名称
	 */
	private static final String JSON_FIELD_NAME = "name";
	/**
	 * Json格式的字段：金币
	 */
	private static final String JSON_FIELD_GOLD = "gold";
	/**
	 * Json格式的字段：时间
	 */
	private static final String JSON_FIELD_TIME = "time";

	private long id;
	private String icon;
	private String name;
	private long gold;
	private long time;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getGold() {
		return gold;
	}

	public void setGold(long gold) {
		this.gold = gold;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "IncomeRecord [id=" + id + ", icon=" + icon + ", name=" + name
				+ ", gold=" + gold + ", time=" + time + "]";
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(id);
		dest.writeString(icon);
		dest.writeString(name);
		dest.writeLong(gold);
		dest.writeLong(time);

	}

	public static final Parcelable.Creator<ListTemplateInfo> CREATOR = new Creator<ListTemplateInfo>() {

		@Override
		public ListTemplateInfo createFromParcel(Parcel source) {
			ListTemplateInfo incomeRecord = new ListTemplateInfo();

			incomeRecord.id = source.readLong();
			incomeRecord.icon = source.readString();
			incomeRecord.name = source.readString();
			incomeRecord.gold = source.readLong();
			incomeRecord.time = source.readLong();

			return incomeRecord;
		}

		@Override
		public ListTemplateInfo[] newArray(int size) {
			return new ListTemplateInfo[size];
		}

	};

	/**
	 * 解析一条信息
	 * 
	 * @param context
	 * @param obj
	 * @return
	 * @throws JSONException
	 */
	public static ListTemplateInfo jsonParse(JSONObject obj) throws JSONException {
		if (obj == null) {
			return null;
		}

		ListTemplateInfo incomeRecord = new ListTemplateInfo();
		incomeRecord.setId(JsonUtil.getLong(obj, JSON_FIELD_ID));
		incomeRecord.setIcon(JsonUtil.getString(obj, JSON_FIELD_ICON));
		incomeRecord.setName(JsonUtil.getString(obj, JSON_FIELD_NAME));
		incomeRecord.setGold(JsonUtil.getLong(obj, JSON_FIELD_GOLD));
		incomeRecord.setTime(JsonUtil.getLong(obj, JSON_FIELD_TIME));

		if (incomeRecord.isError()) {
			return null;
		}

		return incomeRecord;
	}

	/**
	 * 解析多条信息
	 * 
	 * @param context
	 * @param objArray
	 * @param incomeRecordList
	 * @return
	 * @throws JSONException
	 */
	public static int jsonParseList(JSONArray objArray,
			List<ListTemplateInfo> incomeRecordList) throws JSONException {
		if (objArray == null || objArray.length() <= 0
				|| incomeRecordList == null) {
			return 0;
		}

		int count = 0;
		for (int i = 0; i < objArray.length(); i++) {
			JSONObject obj = objArray.getJSONObject(i);
			ListTemplateInfo incomeRecord = jsonParse(obj);

			if (incomeRecord != null) {
				incomeRecordList.add(incomeRecord);
				count++;
			}
		}

		return count;
	}

	public String getGoldDescribe(Context context) {
		// String format = context.getString(R.string.apkGold_format);
		//
		// String str = String.format(format, gold);
		// return str;
		return "100";
	}

	public String getTimeDescribe() {
		String str = "" + time;
		return str;
	}
}
