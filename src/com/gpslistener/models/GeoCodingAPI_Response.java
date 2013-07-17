package com.gpslistener.models;

public class GeoCodingAPI_Response 
{
	public static String TAG_RESULTS = "results";
	public static String TAG_ADDRESS = "formatted_address";
	public static String TAG_GEOMETRY = "geometry";
	public static String TAG_LOCATION = "location";
	public static String TAG_LATITUDE = "lat";
	public static String TAG_LONGITUDE = "lng";
	
	private String latitude;
	private String longitude;
	private String formatted_address;
	
	/**
	 * @return the latitude
	 */
	public String getLatitude() {
		return latitude;
	}
	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	/**
	 * @return the longitude
	 */
	public String getLongitude() {
		return longitude;
	}
	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return formatted_address;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.formatted_address = name;
	}
}
