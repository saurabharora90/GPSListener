package com.gpslistener;

import java.sql.Date;
import java.sql.Timestamp;

import com.gpslistener.helpers.Constants;
import com.gpslistener.helpers.GPSListenerDbHelper;
import com.gpslistener.helpers.HttpFetchLocationTask;
import com.gpslistener.models.GPSListenerContract;
import com.gpslistener.models.GeoCodingAPI_Response;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity implements AsyncResponse {
	
	private LocationManager manager;
	private gpslistenerLocationListener listener = new gpslistenerLocationListener();
	HttpFetchLocationTask fetchLocationTask = new HttpFetchLocationTask();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		manager = (LocationManager) getSystemService(LOCATION_SERVICE);
		
		//To register the listener to be call backed when the Async Task returns data
		fetchLocationTask.delegate = this;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public void onResume() {
	    super.onResume();  // Always call the superclass method first
	    manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 50, listener);
	}
	
	@Override
	public void onPause() {
	    super.onPause();  // Always call the superclass method first
	    manager.removeUpdates(listener);
	}
	
	@Override
	public void onTaskCompleted(Object values) 
	{
		GeoCodingAPI_Response response = (GeoCodingAPI_Response) values;
		//Add to database
		GPSListenerDbHelper dbHelper = new GPSListenerDbHelper(getApplicationContext());
		SQLiteDatabase mDatabase = dbHelper.getWritableDatabase();
		ContentValues vContentValues = new ContentValues();
		vContentValues.put(GPSListenerContract.StoredLocations.COLUMN_NAME_Latitude, response.getLatitude());
		vContentValues.put(GPSListenerContract.StoredLocations.COLUMN_NAME_Longitude, response.getLongitude());
		vContentValues.put(GPSListenerContract.StoredLocations.COLUMN_NAME_LocationDetail, response.getName());
		mDatabase.insert(GPSListenerContract.StoredLocations.Table_Name, "null", vContentValues);
	}
	
	public class gpslistenerLocationListener implements LocationListener {

		@Override
		public void onLocationChanged(Location location) {
			double lat = location.getLatitude();
			double lon = location.getLongitude();
			
			TextView textLat = (TextView)findViewById(R.id.latitudeText);
			textLat.setText(String.valueOf(lat));
			
			TextView textLon = (TextView)findViewById(R.id.longitudeText);
			textLon.setText(String.valueOf(lon));
			
			/*Bundle locationDataBundle = new Bundle();
			locationDataBundle.putDouble("com.gpslistener.locationData.latitude", lat);
			locationDataBundle.putDouble("com.gpslistener.locationData.longtitude", lon);
			Intent intent = new Intent(getApplicationContext(), FetchLocationService.class);
			intent.putExtra("com.gpslistener.locationData", locationDataBundle);
			startService(intent);*/
			fetchLocationTask.execute(Constants.getNEARBY_SEARCH_URI(lat, lon, "false", 100));
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub

		}

	}



}
