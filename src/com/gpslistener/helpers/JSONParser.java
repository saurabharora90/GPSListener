package com.gpslistener.helpers;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;

import com.gpslistener.models.PlacesAPI_Response;

public class JSONParser 
{
	
	public static String getJSONFromEntity(HttpEntity entity)
	{
		StringBuilder builder = new StringBuilder();
		String line = null;
		try
		{
		InputStream in = entity.getContent();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		while((line=reader.readLine())!=null)
			builder.append(line+"\n");
		in.close();
		return builder.toString();
		}
		
		catch(Exception e) 
		{
			return e.getLocalizedMessage();
		}
	}
	
	public static PlacesAPI_Response parseJSON(String jsonString)
	{
		PlacesAPI_Response response = new PlacesAPI_Response();
		return response;
	}

}
