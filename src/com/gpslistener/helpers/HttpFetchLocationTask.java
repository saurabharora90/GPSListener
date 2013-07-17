package com.gpslistener.helpers;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import com.gpslistener.models.PlacesAPI_Response;

import android.os.AsyncTask;

public class HttpFetchLocationTask extends AsyncTask<String, Void, String> {

	@Override
	protected String doInBackground(String... params)
	{
		HttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();
		HttpGet httpGet = new HttpGet(params[0]);
		String json = null;
		try 
		{
			HttpResponse response = httpClient.execute(httpGet, localContext);
			HttpEntity entity = response.getEntity();
			json = JSONParser.getJSONFromEntity(entity);
		} 
		catch (Exception e) 
		{
			return e.getLocalizedMessage();
		}
		
		return json;
	}
	
	@Override
	protected void onPostExecute(String results)
	{
		PlacesAPI_Response response = JSONParser.parseJSON(results);
	}

}
