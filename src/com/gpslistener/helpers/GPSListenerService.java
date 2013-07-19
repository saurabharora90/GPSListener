package com.gpslistener.helpers;

import com.gpslistener.AsyncResponse;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class GPSListenerService extends Service implements AsyncResponse {
	
	private LocationManager manager;
	private gpslistenerLocationListener listener = new gpslistenerLocationListener();
	private static Boolean serviceStatus = false;; //if true then service is running

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
			Log.v("Service", "Service Started");
		
			manager = (LocationManager) getSystemService(LOCATION_SERVICE);
			if(manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
				manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 50, listener);
			}
			if(manager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
				manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 50, listener);
			}
			HttpFetchLocationTask.delegate = this;
			serviceStatus = true;
		}
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
			double lat = location.getLatitude();
			double lon = location.getLongitude();
			
			Log.v("Location Service", "Location retrieved");
			
			new HttpFetchLocationTask().execute(Constants.getNEARBY_SEARCH_URI(lat, lon, "true", 100));
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
		GPSListenerDbHelper dbHelper = new GPSListenerDbHelper(getApplicationContext());
		Object params[] = {value, dbHelper};
		new DatabaseTask().execute(params);
	}


}
