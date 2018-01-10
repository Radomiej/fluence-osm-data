package pl.fluence.gis.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.vividsolutions.jts.geom.Point;

import pl.fluence.gis.GeoPoint;


public class PolygonElement implements Serializable {
	private Set<Long> ids = new HashSet<Long>();
	private Map<String, String> tags;
	private List<GeoPoint> points = new ArrayList<GeoPoint>();

	public Set<Long> getIds() {
		return ids;
	}

	public void setIds(Set<Long> ids) {
		this.ids = ids;
	}

	public Map<String, String> getTags() {
		return tags;
	}

	public void setTags(Map<String, String> tags) {
		this.tags = tags;
	}

	public List<GeoPoint> getPoints() {
		return points;
	}

	public void setPoints(List<GeoPoint> points) {
		this.points = points;
	}

	public void setJSTPoints(List<Point> addPoints) {
		points.clear();
		for(Point point : addPoints){
			points.add(new GeoPoint(point.getY(), point.getX()));
		}
	}
}
