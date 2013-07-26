package com.gpslistener.models;

import com.gpslistener.helpers.GPSListenerDbHelper;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class DetecedLocationsProvider extends ContentProvider {
	
	protected SQLiteDatabase mDatabase;
	GPSListenerDbHelper dbHelper;
	private static final UriMatcher sUriMatcher;
	private static final int LOCATION_LIST = 1;
	private static final int LOCATION_ID = 2;	   
    
    static
    {
    	sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    	sUriMatcher.addURI(GPSListenerContract.AUTHORITY, GPSListenerContract.DetectedLocation.CONTENT_PATH, LOCATION_LIST);
    	sUriMatcher.addURI(GPSListenerContract.AUTHORITY, GPSListenerContract.DetectedLocation.CONTENT_PATH + "/#", LOCATION_ID);
    }


	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) 
	{
		mDatabase = dbHelper.getWritableDatabase();
		
		switch (sUriMatcher.match(uri)) {
		case LOCATION_LIST:
			//all is well
			break;
			
		case LOCATION_ID: //only return the one with the id included in the uri
			selection = GPSListenerContract.DetectedLocation._ID;
			selectionArgs = new String[] {uri.getLastPathSegment()};
			break;
			
		default:
			throw new IllegalArgumentException("Unsupported URI for query: " + uri);
		}
		
		int id = mDatabase.delete(GPSListenerContract.DetectedLocation.Table_Name, selection, selectionArgs);
		
		return id;
	}

	@Override
	public String getType(Uri uri) 
	{
		switch (sUriMatcher.match(uri)) 
		{
	      case LOCATION_LIST:
	         return GPSListenerContract.CONTENT_TYPE;
	      case LOCATION_ID:
	         return GPSListenerContract.CONTENT_ITEM_TYPE;
	      default:
	         throw new IllegalArgumentException("Unsupported URI: " + uri);
	   }
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) 
	{
		if(sUriMatcher.match(uri) != LOCATION_LIST)
			throw new IllegalArgumentException("Unsupported URI for insertion: " + uri);  
			
		mDatabase = dbHelper.getWritableDatabase();
		long id = mDatabase.insert(GPSListenerContract.DetectedLocation.Table_Name, null, values);
			      
		if(id > 0)
		{
			// notify all listeners of changes and return itemUri: 
			Uri itemUri = ContentUris.withAppendedId(uri, id); 
		    getContext().getContentResolver().notifyChange(itemUri, null); 
		    return itemUri;
		}
		throw new SQLException("Problem while inserting into database");
	}

	@Override
	public boolean onCreate() 
	{
	    dbHelper = new GPSListenerDbHelper(this.getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) 
	{
		mDatabase = dbHelper.getReadableDatabase();
		
		switch (sUriMatcher.match(uri)) {
		case LOCATION_LIST:
			//all is well
			break;
			
		case LOCATION_ID: //only return the one with the id included in the uri
			selection = GPSListenerContract.DetectedLocation._ID;
			selectionArgs = new String[] {uri.getLastPathSegment()};
			break;
			
		default:
			throw new IllegalArgumentException("Unsupported URI for query: " + uri);
		}
		
		Cursor cursor = mDatabase.query(GPSListenerContract.DetectedLocation.Table_Name, projection, selection, selectionArgs, null, null, null);
		
		return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) 
	{
		mDatabase = dbHelper.getWritableDatabase();
		
		switch (sUriMatcher.match(uri)) {
		case LOCATION_LIST:
			//all is well
			break;
			
		case LOCATION_ID: //only return the one with the id included in the uri
			selection = GPSListenerContract.DetectedLocation._ID;
			selectionArgs = new String[] {uri.getLastPathSegment()};
			break;
			
		default:
			throw new IllegalArgumentException("Unsupported URI for query: " + uri);
		}
		
		int id = mDatabase.update(GPSListenerContract.DetectedLocation.Table_Name, values, selection, selectionArgs);
		
		return id;
	}

}
