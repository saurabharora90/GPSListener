package com.gpslistener;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import com.gpslistener.helpers.Constants;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private LocationManager manager;
	private gpslistenerLocationListener listener = new gpslistenerLocationListener();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		manager = (LocationManager) getSystemService(LOCATION_SERVICE);
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
			new LongRunningGetIO().execute(Constants.getNEARBY_SEARCH_URI(lat, lon, "false", 10));
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

	private class LongRunningGetIO extends AsyncTask <String, Void, String> {
		protected String getASCIIContentFromEntity(HttpEntity entity) throws IllegalStateException, IOException {
		InputStream in = entity.getContent();


		StringBuffer out = new StringBuffer();
		int n = 1;
		while (n>0) {
		byte[] b = new byte[4096];
		n =  in.read(b);


		if (n>0) out.append(new String(b, 0, n));
		}


		return out.toString();
		}


		@Override


		protected String doInBackground(String... params) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();
		HttpGet httpGet = new HttpGet(params[0]);
		String text = null;
		try {
		HttpResponse response = httpClient.execute(httpGet, localContext);


		HttpEntity entity = response.getEntity();


		text = getASCIIContentFromEntity(entity);


		} catch (Exception e) {
		return e.getLocalizedMessage();
		}


		return text;
		}


		protected void onPostExecute(String results) 
		{
			if (results!=null) 
			{
				EditText et = (EditText)findViewById(R.id.PlaceText);
				et.setText(results);
			}
		}
	}

}
