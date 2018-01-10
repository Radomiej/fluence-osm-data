package pl.fluence.gis.models;

import java.io.Serializable;
import java.util.Map;

import com.vividsolutions.jts.geom.Point;

import pl.fluence.gis.GeoPoint;

public class SymbolElement implements Serializable {
	private Long id;
	private Map<String, String> tags;
	private GeoPoint point;

	public Map<String, String> getTags() {
		return tags;
	}

	public void setTags(Map<String, String> tags) {
		this.tags = tags;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public GeoPoint getPoint() {
		return point;
	}

	public void setPoint(GeoPoint point2) {
		this.point = point2;
	}

	public void setPoint(Point point2) {
		this.point = new GeoPoint(point2.getY(), point2.getX());
	}
}
