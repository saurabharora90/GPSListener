package com.gpslistener.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.gpslistener.AsyncResponse;
import com.gpslistener.AsyncTasks.HttpFetchLocationTask;
import com.gpslistener.database.GPSListenerContract;
import com.gpslistener.helpers.Constants;
import com.gpslistener.models.GeoCodingAPI_Response;

import android.app.Service;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.UserDictionary;
import android.util.Log;

public class GPSListenerService extends Service implements AsyncResponse {
	
	private LocationManager manager;
	private gpslistenerLocationListener listener = new gpslistenerLocationListener();
	private static Boolean serviceStatus = false;; //if true then service is running
	private static double lat;
	private static double lon;

	@Override
	public IBinder onBind(Intent arg0) 
	{
		return null;
	}
	
	@Override
	  public int onStartCommand(Intent intent, int flags, int startId)
	{
		if(serviceStatus == false)
		{
			manager = (LocationManager) getSystemService(LOCATION_SERVICE);
			if(manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
				manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 1000, listener);
			}
			if(manager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
				manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 1000, listener);
			}
			HttpFetchLocationTask.delegate = this;
			serviceStatus = true;
		}
		
		Log.v("GPSListenerService", "Service Started");
		return START_STICKY;
	}
	
	@Override
	  public void onDestroy()
	{
		Log.v("Service", "Destroying Service");
		manager.removeUpdates(listener);
		serviceStatus = false;
		super.onDestroy();
	}
	
	public class gpslistenerLocationListener implements LocationListener {

		@Override
		public void onLocationChanged(Location location) {
			lat = location.getLatitude();
			lon = location.getLongitude();
			
			Log.v("Location Service", "Location retrieved");
			int id = -1; //no id to be passed as this is not an update database time.
			new HttpFetchLocationTask().execute( Constants.getNEARBY_SEARCH_URI(lat, lon, "true", 100), String.valueOf(id));
		}

		@Override
		public void onProviderDisabled(String provider) {

		}

		@Override
		public void onProviderEnabled(String provider) {

		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {

		}

	}

	@Override
	public void onTaskCompleted(Object value) 
	{
		ContentResolver resolver = getApplicationContext().getContentResolver();
		ContentValues vContentValues = new ContentValues();

		//get date and time.
		Calendar c = Calendar.getInstance();

		SimpleDateFormat df1 = new SimpleDateFormat("dd-MM-yyyy");
		String formattedDate = df1.format(c.getTime());

		SimpleDateFormat df2 = new SimpleDateFormat("kk:mm:ss");
		String formattedTime = df2.format(c.getTime());

	    vContentValues.put(GPSListenerContract.DetectedLocation.COLUMN_NAME_LATITUDE, GPSListenerService.getLat());
		vContentValues.put(GPSListenerContract.DetectedLocation.COLUMN_NAME_LONGITUDE, GPSListenerService.getLon());
		vContentValues.put(GPSListenerContract.DetectedLocation.COLUMN_NAME_DATE_STRING, formattedDate);
		vContentValues.put(GPSListenerContract.DetectedLocation.COLUMN_NAME_TIME_STRING, formattedTime);


		if(value!=null)
		{
			ArrayList<Object> params = (ArrayList<Object>) value;
			GeoCodingAPI_Response response = (GeoCodingAPI_Response) params.get(0);
			vContentValues.put(GPSListenerContract.DetectedLocation.COLUMN_NAME_LOCATION_DETAIL, response.getName());
		}
		
		resolver.insert(GPSListenerContract.CONTENT_URI, vContentValues);
		
		Log.v("Database write", "Database write successful");
	}

	public static double getLat() {
		return lat;
	}

	public static double getLon() {
		return lon;
	}

}
