package com.keanbin.app.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.keanbin.framework.base.BaseClass;
import com.keanbin.app.entity.ListTemplateInfo;

/**
 * 收益记录数据库
 * 
 * @author kevin
 */
public class ListTemplateDB extends BaseClass {

	private DBHelper mDBHelper;
	private Context mContext;

	private static final byte[] sLock = new byte[0];

	// 数据库表名称
	private static final String TABLE = "income_record";

	// 数据库表的字段：记录 Id （服务器传过来的）
	private static final String DB_FIELD_ID = "_id";
	// 数据库表的字段：图标网址
	private static final String DB_FIELD_ICON_URL = "_icon_url";
	// 数据库表的字段：名称
	private static final String DB_FIELD_NAME = "_name";
	// 数据库表的字段：金币
	private static final String DB_FIELD_GOLD = "_gold";
	// 数据库表的字段：时间
	private static final String DB_FIELD_TIME = "_time";

	/**
	 * 数据库字段数组
	 */
	private static final String[] COLUMUS = new String[] { DB_FIELD_ID,
			DB_FIELD_ICON_URL, DB_FIELD_NAME, DB_FIELD_GOLD, DB_FIELD_TIME };

	/**
	 * 创建表的Sql语句
	 */
	public static String CREATE_TABLE = "create table " + TABLE
			+ "(_auto_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
			+ DB_FIELD_ID + " text," + DB_FIELD_ICON_URL + " text,"
			+ DB_FIELD_NAME + " text," + DB_FIELD_GOLD + " text,"
			+ DB_FIELD_TIME + " text)";

	public ListTemplateDB(Context context) {
		mContext = context;
		mDBHelper = new DBHelper(mContext);
	}

	/**
	 * 把数据转化成 ContentValues
	 * 
	 * @param incomeRecord
	 * @return
	 */
	public ContentValues putData(ListTemplateInfo incomeRecord) {

		if (incomeRecord == null) {
			return null;
		}

		ContentValues cv = new ContentValues();
		cv.put(DB_FIELD_ID, incomeRecord.getId());
		cv.put(DB_FIELD_ICON_URL, incomeRecord.getIcon());
		cv.put(DB_FIELD_NAME, incomeRecord.getName());
		cv.put(DB_FIELD_GOLD, incomeRecord.getGold());
		cv.put(DB_FIELD_TIME, incomeRecord.getTime());

		return cv;
	}

	/**
	 * 批量插入
	 * 
	 * @param incomeRecordList
	 */
	public void insert(List<ListTemplateInfo> incomeRecordList) {
		if (null == incomeRecordList || incomeRecordList.size() < 1) {
			return;
		}

		synchronized (sLock) {
			SQLiteDatabase db = mDBHelper.getWritableDatabase();
			db.beginTransaction();

			for (ListTemplateInfo incomeRecord : incomeRecordList) {
				ContentValues cv = putData(incomeRecord);
				if (cv != null) {
					db.insert(TABLE, null, cv);
				}
			}

			db.setTransactionSuccessful();
			db.endTransaction();
			db.close();
		}
	}

	/**
	 * 查询数据
	 */
	public List<ListTemplateInfo> query() {

		List<ListTemplateInfo> incomeRecords = new ArrayList<ListTemplateInfo>();

		SQLiteDatabase db = mDBHelper.getWritableDatabase();

		Cursor cursor = db.query(TABLE, COLUMUS, null, null, null, null, null);

		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			ListTemplateInfo incomeRecord = new ListTemplateInfo();

			incomeRecord.setId(cursor.getLong(0));
			incomeRecord.setIcon(cursor.getString(1));
			incomeRecord.setName(cursor.getString(2));
			incomeRecord.setGold(cursor.getLong(3));
			incomeRecord.setTime(cursor.getLong(4));

			if (!incomeRecord.isError()) {
				incomeRecords.add(incomeRecord);
			}
		}

		if (cursor != null) {
			cursor.close();
		}

		if (db != null) {
			db.close();
		}

		return incomeRecords;
	}

	/**
	 * 清空数据库
	 */
	public void clear() {
		SQLiteDatabase db = mDBHelper.getWritableDatabase();

		db.delete(TABLE, null, null);

		db.close();
	}

}
