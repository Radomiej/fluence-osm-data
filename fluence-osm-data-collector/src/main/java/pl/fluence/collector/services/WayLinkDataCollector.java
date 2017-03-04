package pl.fluence.collector.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.openstreetmap.osmosis.core.domain.v0_6.Entity;
import org.openstreetmap.osmosis.core.domain.v0_6.EntityType;
import org.openstreetmap.osmosis.core.domain.v0_6.Node;
import org.openstreetmap.osmosis.core.domain.v0_6.Relation;
import org.openstreetmap.osmosis.core.domain.v0_6.RelationMember;
import org.openstreetmap.osmosis.core.domain.v0_6.Way;
import org.openstreetmap.osmosis.core.domain.v0_6.WayNode;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Polygon;

import pl.fluence.reader.database.ElementsDatabase;
import pl.fluence.reader.database.SimpleElementsDatabase;
import pl.fluence.reader.processors.AllElementsProccesor;

public class WayLinkDataCollector {
	private ElementsDatabase elementsDatabase;
	List<WayLink> wayLinks = new ArrayList<WayLink>();
	private Map<Long, WayLink> waysMap = new HashMap<Long, WayLink>();
	private GeometryFactory fact = new GeometryFactory();
	
	public WayLinkDataCollector(ElementsDatabase elementsDatabase) {
		this.elementsDatabase = elementsDatabase;
	}

	public void procces() {
		Collection<Way> ways = elementsDatabase.getWaysMap().values();
		for (Way way : ways) {
			WayLink wayLink = createWayLink(way);
			if(wayLink == null) continue;
			wayLinks.add(wayLink);
			waysMap.put(way.getId(), wayLink);
		}
	}

	

	private WayLink createWayLink(Way way) {
		WayLink wayLink = new WayLink();
		wayLink.setWay(way);
		
		List<Coordinate> coordinates = new LinkedList<Coordinate>();
		addWayNodesCoordinates(way, coordinates);
		
		
		if(coordinates.size() <= 1){
			return null;
		}
		Coordinate[] coordinatesTab = new Coordinate[coordinates.size()];
		coordinatesTab = coordinates.toArray(coordinatesTab);
		
		LineString lineString = fact.createLineString(coordinatesTab);
		wayLink.setLine(lineString);
		
		return wayLink;
	}

	private void addWayNodesCoordinates(Way entity, List<Coordinate> outerCoordinates) {
		for (WayNode wayNode : entity.getWayNodes()) {
			Node node = elementsDatabase.getNodesMap().get(wayNode.getNodeId());
			if(node == null){
				System.err.println("Brakujacy node: " + wayNode.getNodeId());
			}
			Coordinate coord = new Coordinate(node.getLatitude(), node.getLongitude());
			outerCoordinates.add(coord);
		}
	}

	public Map<Long, WayLink> getWaysLinkMap() {
		return waysMap;
	}
}
