package com.kpu.plantid;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {
	private final static String TAG = "DbHelper";
	public static final String DB_NAME="fav.db";
	public static final int DB_VERSION = 1;
	
	
	public DbHelper(Favpestlist context) {
		super(context, DB_NAME, null, DB_VERSION);
	
	}	
	public DbHelper(InfoMapPest context) {
		super(context, DB_NAME, null, DB_VERSION);
	}
	public DbHelper(Infopestfav context) {
		super(context, DB_NAME, null, DB_VERSION);
	
	}
	public DbHelper(Info context) {
		super(context, DB_NAME, null, DB_VERSION);
	
	}
	public DbHelper(Infopest context) {
		super(context, DB_NAME, null, DB_VERSION);
	
	}
	public DbHelper(Infofav context) {
		super(context, DB_NAME, null, DB_VERSION);
	
	}
	
	public DbHelper(Favlist context) {
		super(context, DB_NAME, null, DB_VERSION);
	
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		try{
		String sql = String.format("CREATE TABLE mypest (species TEXT,family TEXT ,common TEXT, pid TEXT unique);");
		String sql2 = String.format("CREATE TABLE myfav (species TEXT,family TEXT ,common TEXT, pid TEXT unique);");

		Log.d(TAG,sql+ "ran");
		db.execSQL(sql);
		db.execSQL(sql2);
		}
		catch (Exception e)
		{
			Log.d(TAG,"ERROR");
		}
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table if exists mypest");
		db.execSQL("drop table if exists myfav");
		Log.d(TAG,"Dropped the Table");
	}

}
