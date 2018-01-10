package pl.fluence.collector.tools;

import org.geotools.geometry.jts.JTSFactoryFinder;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

public class CollisionChecker {
	public static boolean checkMultipolygonAndPoint(String multipolygon, double lat, double lon){
		
		GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory( null );
		WKTReader reader = new WKTReader( geometryFactory );
		
		try {
			MultiPolygon polygon = (MultiPolygon) reader.read(multipolygon);
			Coordinate coord = new Coordinate(lat, lon);
			Point point = geometryFactory.createPoint(coord);
			return polygon.contains(point);
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}
}
