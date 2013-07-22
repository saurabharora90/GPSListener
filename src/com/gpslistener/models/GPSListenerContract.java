package com.gpslistener.models;

import android.provider.BaseColumns;

public final class GPSListenerContract {
	
	private static final String TEXT_TYPE = " TEXT";
	private static final String COMMA_SEP = ", ";
	private static final String INTEGER_TYPE = " INTEGER";
	
	public static abstract class DetectedLocation implements BaseColumns
	{
		public static final String Table_Name = "DetectedLocation";
		public static final String COLUMN_NAME_TIME_STRING = "Time";
		public static final String COLUMN_NAME_DATE_STRING = "Date";
		public static final String COLUMN_NAME_LATITUDE = "Latitude";
		public static final String COLUMN_NAME_LONGITUDE = "Longitude";
		public static final String COLUMN_NAME_LOCATION_DETAIL = "Detail";
	}
	
	public static final String CREATE_TABLE_DETECTED_LOCATION = "CREATE TABLE " + DetectedLocation.Table_Name + " ( " + DetectedLocation._ID + INTEGER_TYPE + " PRIMARY KEY" + COMMA_SEP + DetectedLocation.COLUMN_NAME_TIME_STRING + TEXT_TYPE + COMMA_SEP + DetectedLocation.COLUMN_NAME_DATE_STRING +TEXT_TYPE +COMMA_SEP + DetectedLocation.COLUMN_NAME_LATITUDE + TEXT_TYPE + COMMA_SEP + DetectedLocation.COLUMN_NAME_LONGITUDE + TEXT_TYPE + COMMA_SEP + DetectedLocation.COLUMN_NAME_LOCATION_DETAIL + TEXT_TYPE + " )";
}
