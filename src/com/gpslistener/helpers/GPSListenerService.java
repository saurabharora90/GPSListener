package com.gpslistener.helpers;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class GPSListenerService extends Service {

	@Override
	public IBinder onBind(Intent arg0) 
	{
		return null;
	}
	
	@Override
	  public int onStartCommand(Intent intent, int flags, int startId)
	{
		return START_STICKY;
	}
	
	@Override
	  public void onCreate()
	{
		
	}
	
	@Override
	  public void onDestroy()
	{
		
	}

}
