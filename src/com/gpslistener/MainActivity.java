package com.gpslistener;

import com.gpslistener.fragments.ShowDateFragment.OnDateSelectedListener;
import com.gpslistener.helpers.GPSListenerService;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class MainActivity extends Activity implements OnDateSelectedListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Intent listenerIntent = new Intent(this, GPSListenerService.class);
		startService(listenerIntent);
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
	}
	
	@Override
	public void onPause() {
	    super.onPause();  // Always call the superclass method first
	}
	
	public void onDateSelected(String selectedDate)
	{
		Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
		intent.putExtra("date", selectedDate);
		startActivity(intent);
	}
	
	public void stopGPSListenerService()
	{
		stopService(new Intent(this,GPSListenerService.class));
	}
}
