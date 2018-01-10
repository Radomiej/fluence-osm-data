package pl.fluence.collector.services;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.openstreetmap.osmosis.core.domain.v0_6.Node;
import org.openstreetmap.osmosis.core.domain.v0_6.Way;
import org.openstreetmap.osmosis.core.domain.v0_6.WayNode;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;

import pl.fluence.reader.database.ElementsDatabase;
import pl.fluence.reader.importers.OsmImporter;
import pl.fluence.reader.processors.GeoIdProccesor;

public class NeededNodeCollector {
	private HashMap<Long, Node> nodesDatabase = new HashMap<Long, Node>();
	private GeoIdProccesor geoIdProccesor;
	private File osmFile;

	private GeometryFactory fact = new GeometryFactory();
	
	public NeededNodeCollector(ElementsDatabase elementsDatabase, File osmFile) {
		this.osmFile = osmFile;
		Set<Long> lookingEntities = new HashSet<Long>();

		for (Way way : elementsDatabase.getWaysMap().values()) {
			for (WayNode wayNode : way.getWayNodes()) {
				lookingEntities.add(wayNode.getNodeId());
			}
		}

		geoIdProccesor = new GeoIdProccesor(lookingEntities);
	}

	public void process() {
		OsmImporter osmImporter = new OsmImporter();
		osmImporter.addProccessor(geoIdProccesor);
		osmImporter.proccesImport(osmFile);

		for (Node node : geoIdProccesor.getElementsDatabase().getNodesMap().values()) {
			nodesDatabase.put(node.getId(), node);
		}
	}

	public WayLink getWayLink(Way way) {
		WayLink wayLink = new WayLink();
		wayLink.setWay(way);

		List<Coordinate> coordinates = new LinkedList<Coordinate>();
		addWayNodesCoordinates(way, coordinates);

		if (coordinates.size() <= 1) {
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
			Node node = nodesDatabase.get(wayNode.getNodeId());
			if (node == null) {
				System.err.println("Brakujacy node: " + wayNode.getNodeId());
			}
			Coordinate coord = new Coordinate(node.getLongitude(), node.getLatitude());
			outerCoordinates.add(coord);
		}
	}
}
