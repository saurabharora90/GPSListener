package com.gpslistener.AsyncTasks;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.gpslistener.database.GPSListenerContract;
import com.gpslistener.database.GPSListenerDbHelper;
import com.gpslistener.models.GeoCodingAPI_Response;
import com.gpslistener.services.GPSListenerService;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

public class DatabaseWritingTask extends AsyncTask<Object, Void, Void> {

	@Override
	protected Void doInBackground(Object... arg0) 
	{
		GPSListenerDbHelper dbHelper = (GPSListenerDbHelper) arg0[1];
		//Add to database
		SQLiteDatabase mDatabase = dbHelper.getWritableDatabase();

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


		if(arg0[0]!=null)
		{
			ArrayList<Object> params = (ArrayList<Object>) arg0[0];
			GeoCodingAPI_Response response = (GeoCodingAPI_Response) params.get(0);
			vContentValues.put(GPSListenerContract.DetectedLocation.COLUMN_NAME_LOCATION_DETAIL, response.getName());
		}

		mDatabase.insert(GPSListenerContract.DetectedLocation.Table_Name, "null", vContentValues);
		//acquire reference and close database. Acquire reference as it was passed as a param
		mDatabase.close();

		Log.v("Database write", "DatabaseWritingTask write successful");
		return null;
	}

}