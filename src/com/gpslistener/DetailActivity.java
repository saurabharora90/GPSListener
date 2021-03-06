package com.gpslistener;

import com.gpslistener.fragments.ShowDateDetailsFragment;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;

public class DetailActivity extends Activity {
	
	private String selectedDate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		selectedDate = intent.getStringExtra("date");
		
		//setContentView(R.layout.activity_detail);
		 //Show the Up button in the action bar.
		setupActionBar();
	}
	
	@Override
	protected void onStart()
	{
		super.onStart();
		ShowDateDetailsFragment fragment = new ShowDateDetailsFragment();
		fragment.setSelectedDate(selectedDate);
		getFragmentManager().beginTransaction().add(android.R.id.content, fragment).commit();
		setTitle(selectedDate);
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detail, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
