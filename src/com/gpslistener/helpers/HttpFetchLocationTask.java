package com.gpslistener.helpers;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import android.os.AsyncTask;

public class HttpFetchLocationTask extends AsyncTask<String, Void, String> {

	@Override
	protected String doInBackground(String... params)
	{
		HttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();
		HttpGet httpGet = new HttpGet(params[0]);
		String text = null;
		try 
		{
			HttpResponse response = httpClient.execute(httpGet, localContext);
			HttpEntity entity = response.getEntity();
		} 
		catch (Exception e) 
		{
			return e.getLocalizedMessage();
		}
		
		return text;
	}
	
	@Override
	protected void onPostExecute(String results)
	{
		
	}

}
