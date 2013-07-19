package com.gpslistener.helpers;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.gpslistener.models.GPSListenerContract;
import com.gpslistener.models.GeoCodingAPI_Response;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

public class DatabaseTask extends AsyncTask<Object, Void, Void> {

	@Override
	protected Void doInBackground(Object... arg0) 
	{
		GeoCodingAPI_Response response = (GeoCodingAPI_Response) arg0[0];
		GPSListenerDbHelper dbHelper = (GPSListenerDbHelper) arg0[1];
		//Add to database
		SQLiteDatabase mDatabase = dbHelper.getWritableDatabase();
				
		//get date and time.
		Calendar c = Calendar.getInstance();

		SimpleDateFormat df1 = new SimpleDateFormat("dd-MM-yyyy");
		String formattedDate = df1.format(c.getTime());

		SimpleDateFormat df2 = new SimpleDateFormat("kk:mm:ss");
		String formattedTime = df2.format(c.getTime());
				
		ContentValues vContentValues = new ContentValues();
		vContentValues.put(GPSListenerContract.StoredLocations.COLUMN_NAME_Latitude, response.getLatitude());
		vContentValues.put(GPSListenerContract.StoredLocations.COLUMN_NAME_Longitude, response.getLongitude());
		vContentValues.put(GPSListenerContract.StoredLocations.COLUMN_NAME_LocationDetail, response.getName());
		vContentValues.put(GPSListenerContract.StoredLocations.COLUMN_NAME_DATE_STRING, formattedDate);
		vContentValues.put(GPSListenerContract.StoredLocations.COLUMN_NAME_TIME_STRING, formattedTime);
		mDatabase.insert(GPSListenerContract.StoredLocations.Table_Name, "null", vContentValues);
		
		//acquire reference and close database. Acquire reference as it was passed as a param
		//mDatabase.close();
		return null;
	}

}
