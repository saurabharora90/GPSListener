package com.gpslistener.helpers;

import com.gpslistener.models.GPSListenerContract;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GPSListenerDbHelper extends SQLiteOpenHelper {
	
	private static final String DATABASE_NAME = "GPSListenerDatabase.db";
	private static final int DATABASE_VERSION = 1;
	
	public GPSListenerDbHelper(Context context) 
	{
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(GPSListenerContract.CREATE_TABLE_DETECTED_LOCATION);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
