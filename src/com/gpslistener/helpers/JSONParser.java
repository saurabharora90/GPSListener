package com.gpslistener.helpers;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;

import com.gpslistener.models.GeoCodingAPI_Response;

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
	
	public static GeoCodingAPI_Response parseJSON(String jsonString)
	{
		GeoCodingAPI_Response response = new GeoCodingAPI_Response();
		return response;
	}

}
