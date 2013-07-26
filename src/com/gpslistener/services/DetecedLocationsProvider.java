package com.gpslistener.services;

import com.gpslistener.helpers.GPSListenerDbHelper;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class DetecedLocationsProvider extends ContentProvider {
	
	SQLiteDatabase mDatabase;

	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		return null;
	}

	@Override
	public boolean onCreate() 
	{
	    GPSListenerDbHelper dbHelper = new GPSListenerDbHelper(this.getContext());
	    mDatabase = dbHelper.getWritableDatabase();
	    if(mDatabase == null)
	    	return false;
	    
	    if(mDatabase.isReadOnly() == true)
	    {
	    	mDatabase.close();
	    	mDatabase = null;
	    	return false;
	    }
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		return 0;
	}

}
