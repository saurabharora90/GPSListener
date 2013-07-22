package com.gpslistener.helpers;

import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONObject;

import com.gpslistener.AsyncResponse;
import com.gpslistener.models.GeoCodingAPI_Response;

import android.os.AsyncTask;
import android.util.Log;

public class HttpFetchLocationTask extends AsyncTask<String, Void, Object> {

	public static AsyncResponse delegate = null;
	public String id;
	
	@Override
	protected JSONObject doInBackground(String... params)
	{
		id = params[1];
		HttpParams httpParameters = new BasicHttpParams();
		// Set the timeout in milliseconds until a connection is established.
		// The default value is zero, that means the timeout is not used. 
		int timeoutConnection = 3000;
		HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
		// Set the default socket timeout (SO_TIMEOUT) 
		// in milliseconds which is the timeout for waiting for data.
		int timeoutSocket = 5000;
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
		HttpClient httpClient = new DefaultHttpClient(httpParameters);
		
		HttpContext localContext = new BasicHttpContext();
		HttpGet httpGet = new HttpGet(params[0]);
		JSONObject json = null;
		try 
		{
			HttpResponse response = httpClient.execute(httpGet, localContext);
			HttpEntity entity = response.getEntity();
			json = JSONParser.getJSONFromEntity(entity);
		} 
		catch (Exception e) 
		{
			//Connection timed out/data not received.
			Log.v("HttpFetchLocationTask", "No data received");
			return null;
		}
		
		return json;
	}
	
	@Override
	protected void onPostExecute(Object dataReceived)
	{
		if(dataReceived == null)
		{
			delegate.onTaskCompleted(null);
		}
		else 
		{
			JSONObject jsonObject = (JSONObject) dataReceived;
			GeoCodingAPI_Response response = JSONParser.parseJSON(jsonObject);
			ArrayList<Object> params = new ArrayList<Object>();
			params.add(response);
			params.add(id);
			delegate.onTaskCompleted(params);
		}
		
	}
}