package com.gpslistener.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

public class ConnectionChangeReceiver extends BroadcastReceiver
{
	
	@Override
	public void onReceive(final Context context, final Intent intent) 
	{
		final ConnectivityManager connMgr = (ConnectivityManager) context
              .getSystemService(Context.CONNECTIVITY_SERVICE);

	      final android.net.NetworkInfo wifi = connMgr
	              .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
	
	      final android.net.NetworkInfo mobile = connMgr
	              .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
	      
	      Intent offlineServiceIntent = new Intent(context, com.gpslistener.services.OfflineLocationsResolverService.class);
	      
	      if (wifi.isAvailable() || mobile.isAvailable()) 
	      {
	    	  context.startService(offlineServiceIntent);
	      }
	      else {
			context.stopService(offlineServiceIntent);
		}
	}
}
