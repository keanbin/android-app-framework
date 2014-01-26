package com.keanbin.app.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库操作帮助类
 * 
 * @author keanbin
 * 
 */
public class DBHelper extends SQLiteOpenHelper {
	public static final String TAG = DBHelper.class.getCanonicalName();

	private static final int VERSION = 1;
	private static final String DEFAULT_DB_NAME = "make_gold_db";

	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	public DBHelper(Context context, String name, int version) {
		this(context, name, null, version);
	}

	public DBHelper(Context context, String name) {
		this(context, name, VERSION);
	}

	public DBHelper(Context context) {
		this(context, DEFAULT_DB_NAME);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL(ListTemplateDB.CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
