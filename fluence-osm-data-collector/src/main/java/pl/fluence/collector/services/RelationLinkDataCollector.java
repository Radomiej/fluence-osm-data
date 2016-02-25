package pl.fluence.collector.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
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
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Polygon;

import pl.fluence.reader.database.SimpleElementsDatabase;
import pl.fluence.reader.processors.AllElementsProccesor;

public class RelationLinkDataCollector {
	private SimpleElementsDatabase elementsDatabase;
	List<RelationLink> relationLinks = new ArrayList<RelationLink>();
	private Map<Long, RelationLink> relationMap = new HashMap<Long, RelationLink>();

	private int nullLink = 0;
	private int anyLink = 0;

	public RelationLinkDataCollector(SimpleElementsDatabase elementsDatabase) {
		this.elementsDatabase = elementsDatabase;
	}

	public void procces() {
		Collection<Relation> relations = elementsDatabase.getRelationsMap().values();
		for (Relation relation : relations) {
			RelationLink relationLink = createRelationLink(relation);
			relationLinks.add(relationLink);
			relationMap.put(relation.getId(), relationLink);
		}

		for (RelationLink relationLink : relationLinks) {
			createMultipolygon(relationLink);
		}
		System.out.println("Null link: " + nullLink + " any links: " + anyLink);
	}

	private RelationLink createRelationLink(Relation relation) {
		RelationLink relationLink = new RelationLink(relation);
		for (RelationMember relationMember : relation.getMembers()) {
			long memberId = relationMember.getMemberId();
			Entity entity = elementsDatabase.getRelationsMap().get(memberId);
			if (entity == null)
				entity = elementsDatabase.getWaysMap().get(memberId);
			if (entity == null)
				entity = elementsDatabase.getNodesMap().get(memberId);
			if (entity == null) {
				relationLink.addNullMember(relationMember);
				// System.out.println("Null members: " +
				// relationMember.getMemberId() + " type: " +
				// relationMember.getMemberType() + " role: " +
				// relationMember.getMemberRole());
				nullLink++;
			} else {
				relationLink.addMemeber(relationMember, entity);
				// System.out.println("Any members: " +
				// relationMember.getMemberId() + " type: " +
				// relationMember.getMemberType() + " role: " +
				// relationMember.getMemberRole());
				anyLink++;
			}
		}
		return relationLink;
	}

	private void createMultipolygon(RelationLink relationLink) {
		if (relationLink.isCreated()) {
			return;
		}
		relationLink.setCreated(true);
		// Create Lines
		List<Coordinate> outerCoordinates = new ArrayList<Coordinate>();
		for (RelationMember relationMember : relationLink.getRelation().getMembers()) {
			Entity entity = relationLink.getRelationMembers().get(relationMember);
			if(entity == null) continue;
			if (entity.getType() == EntityType.Way) {
				if (relationMember.getMemberRole().equalsIgnoreCase("outer")) {
					addWayNodesCoordinates((Way) entity, outerCoordinates);
				}
			} else if (entity.getType() == EntityType.Relation) {
				createMultipolygon(relationMap.get(entity.getId()));
			}
		}

		if (outerCoordinates.size() > 2) {
			outerCoordinates.add(outerCoordinates.get(0));
			Coordinate[] coordinatesTab = new Coordinate[outerCoordinates.size()];
			outerCoordinates.toArray(coordinatesTab);

			GeometryFactory fact = new GeometryFactory();
			LinearRing linear = new GeometryFactory().createLinearRing(coordinatesTab);
			Polygon poly = new Polygon(linear, null, fact);
			Polygon[] polys = { poly };
			MultiPolygon multiPolygon = new GeometryFactory().createMultiPolygon(polys);
			relationLink.setMultiPolygon(multiPolygon);
		}
	}

	private void addWayNodesCoordinates(Way entity, List<Coordinate> outerCoordinates) {
		for (WayNode wayNode : entity.getWayNodes()) {
			Node node = elementsDatabase.getNodesMap().get(wayNode.getNodeId());
			Coordinate coord = new Coordinate(node.getLatitude(), node.getLongitude());
			outerCoordinates.add(coord);
		}
	}

	public Map<Long, RelationLink> getRelationMap() {
		return relationMap;
	}
}
