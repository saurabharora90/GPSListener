package com.gpslistener.fragments;

import com.gpslistener.R;
import com.gpslistener.database.GPSListenerContract;
import com.gpslistener.database.GPSListenerDbHelper;

import android.app.Activity;
import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class ShowDateFragment extends Fragment {

	OnDateSelectedListener mListener;
	
	public ShowDateFragment() {
		// Required empty public constructor
	}
	
	// Container Activity must implement this interface
    public interface OnDateSelectedListener {
        public void onDateSelected(String selectedDate);
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_show_date, container, false);

		//read from database
	    GPSListenerDbHelper dbHelper = new GPSListenerDbHelper(getActivity());
	    SQLiteDatabase mDatabase = dbHelper.getReadableDatabase();
	    String queryString = "SELECT DISTINCT " + GPSListenerContract.DetectedLocation._ID +", " + GPSListenerContract.DetectedLocation.COLUMN_NAME_DATE_STRING + " FROM " + GPSListenerContract.DetectedLocation.Table_Name + " GROUP BY " + GPSListenerContract.DetectedLocation.COLUMN_NAME_DATE_STRING;
	    
	    Cursor cursor = mDatabase.rawQuery(queryString, null);
	    SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_list_item_1, cursor, new String[] {GPSListenerContract.DetectedLocation.COLUMN_NAME_DATE_STRING}, new int [] {android.R.id.text1}, 0);
	    ListView myListView = (ListView) view.findViewById(R.id.dateListview);
	    myListView.setAdapter(cursorAdapter);
	    
	    myListView.setOnItemClickListener(new OnItemClickListener()
	    {
	        @Override 
	        public void onItemClick(AdapterView<?> list, View view,int position, long id)
	        { 
	        	String selectedDate = null;
	        	Cursor cursor = (Cursor) list.getItemAtPosition(position);
	        	selectedDate = cursor.getString(cursor.getColumnIndex("Date"));
	        	mListener.onDateSelected(selectedDate);
	        }
	    });
	    return view;
	}
	
	@Override
	public void onAttach(Activity activity) 
	{
        super.onAttach(activity);
        try 
        {
            mListener = (OnDateSelectedListener) activity;
        } 
        catch (ClassCastException e) 
        {
            throw new ClassCastException(activity.toString() + " must implement OnDateSelectedListener");
        }
	}
}
