package com.gpslistener.helpers;

public class Constants {
	
	private final static String API_KEY = "AIzaSyA2pFxIDG6Q7bODOYw6U0akJaXWWSwkQKI";
	private final static String PLACES_URI = "https://maps.googleapis.com/maps/api/place/";
	private final static String NEARBY_SEARCH_STRING ="nearbysearch/";
	private final static String OUTPUT_TYPE = "json?";
	private final static String KEY_STRING = "key=";
	private final static String AMPERSAND_STRING = "&";
	
	private static String LOCATION_STRING = "location=";
	private static String RADIUS_STRING = "radius=";
	private static String SENSOR_STRING = "sensor=";
	private static String NEARBY_SEARCH_URI;

	/**
	 * @return the nEARBY_SEARCH_URI
	 * 
	 * @param : sensor : true or false
	 */
	public static String getNEARBY_SEARCH_URI(double lat,double lon, String sensor, double radius) 
	{
		LOCATION_STRING += String.valueOf(lat) + "," + String.valueOf(lon);
		RADIUS_STRING += String.valueOf(radius);
		SENSOR_STRING += sensor;
		NEARBY_SEARCH_URI = PLACES_URI + NEARBY_SEARCH_STRING + OUTPUT_TYPE + LOCATION_STRING + AMPERSAND_STRING + RADIUS_STRING + AMPERSAND_STRING +  SENSOR_STRING + AMPERSAND_STRING + KEY_STRING + API_KEY;
		return NEARBY_SEARCH_URI;
	}

}
