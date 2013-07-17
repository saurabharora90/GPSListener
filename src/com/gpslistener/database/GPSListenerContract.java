package com.gpslistener.database;

import android.provider.BaseColumns;

public final class GPSListenerContract {
	
	private static final String TEXT_TYPE = " TEXT";
	private static final String COMMA_SEP = ", ";
	private static final String INTEGER_TYPE = " INTEGER";
	
	public static abstract class StoredLocations implements BaseColumns
	{
		public static final String Table_Name = "StoredLocations";
		public static final String COLUMN_NAME_TIMESTAMP_STRING = "LocationTimestamp";
		public static final String COLUMN_NAME_Latitude = "Latitude";
		public static final String COLUMN_NAME_Longitude = "Longitude";
		public static final String COLUMN_NAME_LocationDetail = "LocationDetails";
	}
	
	public static abstract class OfflineLocations implements BaseColumns
	{
		public static final String Table_Name = "OfflineLocations";
		public static final String COLUMN_NAME_TIMESTAMP_STRING = "LocationTimestamp";
		public static final String COLUMN_NAME_Latitude = "Latitude";
		public static final String COLUMN_NAME_Longitude = "Longitude";
	}
	
	public static final String CREATE_TABLE_STORED_LOCATIONS = "CREATE TABLE " + StoredLocations.Table_Name + " ( " + StoredLocations._ID + INTEGER_TYPE + " PRIMARY KEY" + COMMA_SEP + StoredLocations.COLUMN_NAME_TIMESTAMP_STRING + TEXT_TYPE + COMMA_SEP + StoredLocations.COLUMN_NAME_Latitude + TEXT_TYPE + COMMA_SEP + StoredLocations.COLUMN_NAME_Longitude + TEXT_TYPE + COMMA_SEP + StoredLocations.COLUMN_NAME_LocationDetail + TEXT_TYPE +" )";
	public static final String CREATE_TABLE_OFFLINE_LOCATIONS = "CREATE TABLE " + OfflineLocations.Table_Name + " ( " + OfflineLocations._ID + INTEGER_TYPE + " PRIMARY KEY" + COMMA_SEP + OfflineLocations.COLUMN_NAME_TIMESTAMP_STRING + TEXT_TYPE + COMMA_SEP + OfflineLocations.COLUMN_NAME_Latitude + TEXT_TYPE + COMMA_SEP + StoredLocations.COLUMN_NAME_Longitude + TEXT_TYPE + COMMA_SEP + " )";
}
