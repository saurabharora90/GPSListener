package com.gpslistener.helpers;

public class Constants {
	
	private final static String OUTPUT_TYPE = "json?";
	private final static String AMPERSAND_STRING = "&";
	private final static String GEOCODING_STRING = "http://maps.googleapis.com/maps/api/geocode/";
	
	private static String NEARBY_SEARCH_URI;

	/**
	 * @return the nEARBY_SEARCH_URI
	 * 
	 * @param : sensor : true or false
	 */
	public static String getNEARBY_SEARCH_URI(double lat,double lon, String sensor, double radius) 
	{
		//Request denied bug: As this is a static string, the previous location gets concated.
		String LOCATION_STRING = "latlng=";
		String SENSOR_STRING = "sensor=";
		LOCATION_STRING += String.valueOf(lat) + "," + String.valueOf(lon);
		//RADIUS_STRING += String.valueOf(radius);
		SENSOR_STRING += sensor;
		//NEARBY_SEARCH_URI = PLACES_URI + NEARBY_SEARCH_STRING + OUTPUT_TYPE + LOCATION_STRING + AMPERSAND_STRING + RADIUS_STRING + AMPERSAND_STRING +  SENSOR_STRING + AMPERSAND_STRING + KEY_STRING + API_KEY;
		
		NEARBY_SEARCH_URI = GEOCODING_STRING + OUTPUT_TYPE + LOCATION_STRING + AMPERSAND_STRING +  SENSOR_STRING;
		
		return NEARBY_SEARCH_URI;
	}

}
