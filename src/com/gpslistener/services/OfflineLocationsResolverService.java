package com.gpslistener.services;

import java.util.ArrayList;

import com.gpslistener.AsyncResponse;
import com.gpslistener.AsyncTasks.HttpFetchLocationTask;
import com.gpslistener.database.GPSListenerContract;
import com.gpslistener.database.GPSListenerDbHelper;
import com.gpslistener.helpers.Constants;
import com.gpslistener.models.GeoCodingAPI_Response;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.util.Log;

public class OfflineLocationsResolverService extends Service implements AsyncResponse
{	
	public static Boolean Service_status = false; //the service is stopped.
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
			GPSListenerDbHelper dbHelper = new GPSListenerDbHelper(getApplicationContext());
		    SQLiteDatabase mDatabase = dbHelper.getReadableDatabase();
		    String queryString = "SELECT * FROM " + GPSListenerContract.DetectedLocation.Table_Name + " WHERE " + GPSListenerContract.DetectedLocation.COLUMN_NAME_LOCATION_DETAIL + " IS NULL";
		    
		    Cursor cursor = mDatabase.rawQuery(queryString, null);
		    while(cursor.moveToNext())
		    {
		    	HttpFetchLocationTask.delegate = this;
		    	double lat = cursor.getDouble(cursor.getColumnIndex(GPSListenerContract.DetectedLocation.COLUMN_NAME_LATITUDE));
		    	double lon = cursor.getDouble(cursor.getColumnIndex(GPSListenerContract.DetectedLocation.COLUMN_NAME_LONGITUDE));
		    	new HttpFetchLocationTask().execute(Constants.getNEARBY_SEARCH_URI(lat, lon, "true", 100), cursor.getString(cursor.getColumnIndex(GPSListenerContract.DetectedLocation._ID)));
		    }
		
		Log.v("OfflineLocationService", "Service Started");
		Service_status = true;
	    return START_STICKY;
	}
	
	@Override
	  public void onDestroy()
	{
		Log.v("OfflineLocationService", "Destroying Service");
		Service_status = false;
		super.onDestroy();
	}

	@Override
	public void onTaskCompleted(Object values) 
	{
		GPSListenerDbHelper dbHelper = new GPSListenerDbHelper(getApplicationContext());
		SQLiteDatabase mDatabase = dbHelper.getWritableDatabase();
				
		ContentValues vContentValues = new ContentValues();
		ArrayList<Object> params = (ArrayList<Object>) values;
		GeoCodingAPI_Response response = (GeoCodingAPI_Response) params.get(0);
		vContentValues.put(GPSListenerContract.DetectedLocation.COLUMN_NAME_LOCATION_DETAIL, response.getName());
		
		String where = GPSListenerContract.DetectedLocation._ID + "=?";
		mDatabase.update(GPSListenerContract.DetectedLocation.Table_Name, vContentValues, where, new String[]{ params.get(1).toString() });
		mDatabase.close();
		Log.v("OfflineLocationService", "Database update successful");
	}
}