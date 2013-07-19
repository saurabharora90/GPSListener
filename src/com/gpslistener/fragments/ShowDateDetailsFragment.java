package com.gpslistener.fragments;

import com.gpslistener.R;
import com.gpslistener.helpers.GPSListenerDbHelper;
import com.gpslistener.models.GPSListenerContract;

import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class ShowDateDetailsFragment extends Fragment 
{
	private String selectedDate;

	/**
	 * @return the selectedDate
	 */
	public String getSelectedDate() {
		return selectedDate;
	}

	/**
	 * @param selectedDate the selectedDate to set
	 */
	public void setSelectedDate(String selectedDate) {
		this.selectedDate = selectedDate;
	}

	public ShowDateDetailsFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_show_date_details, container,
				false);
		
		//read from database
	    GPSListenerDbHelper dbHelper = new GPSListenerDbHelper(getActivity());
	    SQLiteDatabase mDatabase = dbHelper.getReadableDatabase();
		String projection[] = {GPSListenerContract.StoredLocations._ID, GPSListenerContract.StoredLocations.COLUMN_NAME_TIME_STRING, GPSListenerContract.StoredLocations.COLUMN_NAME_LocationDetail, GPSListenerContract.StoredLocations.COLUMN_NAME_Latitude, GPSListenerContract.StoredLocations.COLUMN_NAME_Longitude};
		String where = GPSListenerContract.StoredLocations.COLUMN_NAME_DATE_STRING + "=?";
		
		Cursor cursor = mDatabase.query(GPSListenerContract.StoredLocations.Table_Name, projection, where, new String[] {selectedDate}, null, null, null);
		String from[] = {GPSListenerContract.StoredLocations.COLUMN_NAME_TIME_STRING, GPSListenerContract.StoredLocations.COLUMN_NAME_LocationDetail, GPSListenerContract.StoredLocations.COLUMN_NAME_Latitude, GPSListenerContract.StoredLocations.COLUMN_NAME_Longitude};
		int to[] = {R.id.time, R.id.location, R.id.lat, R.id.lon};
		SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(getActivity(), R.layout.listview_customstyle, cursor, from, to, 0);
	    ListView myListView = (ListView) view.findViewById(R.id.detailsListview);
	    myListView.setAdapter(cursorAdapter);
	    
	    //close the cursor and database.
	    //cursor.close();
	    mDatabase.close();
	    
		return view;
	}

}
