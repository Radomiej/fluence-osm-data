package pl.fluence.gis;

public class GeoPoint {
	private double lat, lon;

	public GeoPoint() {
	}

	public GeoPoint(double latitude, double longitude) {
		this.lat = latitude;
		this.lon = longitude;
	}

	public double getLat() {
		return lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	@Override
	public String toString() {
		return "GeoPoint [lat=" + lat + ", lon=" + lon + "]";
	}
	
	
}
