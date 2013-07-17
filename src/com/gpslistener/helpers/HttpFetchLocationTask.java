package com.gpslistener.helpers;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONObject;

import com.gpslistener.models.GeoCodingAPI_Response;

import android.os.AsyncTask;

public class HttpFetchLocationTask extends AsyncTask<String, Void, JSONObject> {

	@Override
	protected JSONObject doInBackground(String... params)
	{
		HttpClient httpClient = new DefaultHttpClient();
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
		}
		
		return json;
	}
	
	@Override
	protected void onPostExecute(JSONObject jsonObject)
	{
		GeoCodingAPI_Response response = JSONParser.parseJSON(jsonObject);
	}

}
