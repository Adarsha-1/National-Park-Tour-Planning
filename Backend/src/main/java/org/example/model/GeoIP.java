package org.example.model;

public class GeoIP {

	private String ipAddress;
	private String city;
	private String state;
	private String country;
	private String latitude;
	private String longitude;
	private String accuracyRadius;
	private String postalCode;
	

	public GeoIP() {
		super();
	}

	public GeoIP(String ipAddress,String accuracyRadius, String postalCode ,String city,String state, String country ,String latitude, String longitude) {
		super();
		this.ipAddress = ipAddress;
		this.city = city;
		this.latitude = latitude;
		this.longitude = longitude;
		this.state = state;
		this.country= country;
		this.accuracyRadius = accuracyRadius;
		this.postalCode = postalCode;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getAccuracyRadius() {
		return accuracyRadius;
	}

	public void setAccuracyRadius(String accuracyRadius) {
		this.accuracyRadius = accuracyRadius;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	@Override
	public String toString() {
		return "GeoIP [ipAddress=" + ipAddress + ", city=" + city + ", latitude=" + latitude + ", longitude="
				+ longitude + "]";
	}

}
