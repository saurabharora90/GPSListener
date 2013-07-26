package com.gpslistener.models;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class GPSListenerContract {
	
	private static final String TEXT_TYPE = " TEXT";
	private static final String COMMA_SEP = ", ";
	private static final String INTEGER_TYPE = " INTEGER";
	
	// public constants for client development
	public static final String AUTHORITY = "com.gpslistener.provider";
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + DetectedLocation.CONTENT_PATH);
	public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + AUTHORITY + "." + DetectedLocation.CONTENT_PATH;
	public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + AUTHORITY + "." + DetectedLocation.CONTENT_PATH;

	
	public static abstract class DetectedLocation implements BaseColumns
	{
		public static final String Table_Name = "DetectedLocation";
		public static final String COLUMN_NAME_TIME_STRING = "Time";
		public static final String COLUMN_NAME_DATE_STRING = "Date";
		public static final String COLUMN_NAME_LATITUDE = "Latitude";
		public static final String COLUMN_NAME_LONGITUDE = "Longitude";
		public static final String COLUMN_NAME_LOCATION_DETAIL = "Detail";
		
		public static final String CONTENT_PATH = "location";
		public static final String[] PROJECTION_ALL = {_ID, COLUMN_NAME_DATE_STRING, COLUMN_NAME_TIME_STRING, COLUMN_NAME_LATITUDE, COLUMN_NAME_LONGITUDE, COLUMN_NAME_LOCATION_DETAIL};
	}
	
	public static final String CREATE_TABLE_DETECTED_LOCATION = "CREATE TABLE " + DetectedLocation.Table_Name + " ( " + DetectedLocation._ID + INTEGER_TYPE + " PRIMARY KEY" + COMMA_SEP + DetectedLocation.COLUMN_NAME_TIME_STRING + TEXT_TYPE + COMMA_SEP + DetectedLocation.COLUMN_NAME_DATE_STRING +TEXT_TYPE +COMMA_SEP + DetectedLocation.COLUMN_NAME_LATITUDE + TEXT_TYPE + COMMA_SEP + DetectedLocation.COLUMN_NAME_LONGITUDE + TEXT_TYPE + COMMA_SEP + DetectedLocation.COLUMN_NAME_LOCATION_DETAIL + TEXT_TYPE + " )";
}
