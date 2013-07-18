package com.gpslistener.fragments;

import com.gpslistener.R;
import com.gpslistener.helpers.GPSListenerDbHelper;
import com.gpslistener.models.GPSListenerContract;

import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class ShowDateFragment extends Fragment {

	public ShowDateFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_show_date, container, false);

		//read from database
	    GPSListenerDbHelper dbHelper = new GPSListenerDbHelper(getActivity());
	    SQLiteDatabase mDatabase = dbHelper.getReadableDatabase();
	    String[] projection = {GPSListenerContract.StoredLocations._ID,GPSListenerContract.StoredLocations.COLUMN_NAME_DATE_STRING};
	    String sortOrder = GPSListenerContract.StoredLocations.COLUMN_NAME_DATE_STRING;
	    
	    Cursor cursor = mDatabase.query(GPSListenerContract.StoredLocations.Table_Name, projection, null, null, null, null, sortOrder);
	    SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_list_item_1, cursor, new String[] {GPSListenerContract.StoredLocations.COLUMN_NAME_DATE_STRING}, new int [] {android.R.id.text1}, 0);
	    ListView myListView = (ListView) view.findViewById(R.id.dateListview);
	    myListView.setAdapter(cursorAdapter);
	    return view;
	}

}
