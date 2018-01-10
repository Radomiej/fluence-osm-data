package pl.fluence.gis;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.geotools.factory.Hints;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.openstreetmap.osmosis.core.domain.v0_6.Entity;
import org.openstreetmap.osmosis.core.domain.v0_6.EntityType;
import org.openstreetmap.osmosis.core.domain.v0_6.Node;
import org.openstreetmap.osmosis.core.domain.v0_6.Relation;
import org.openstreetmap.osmosis.core.domain.v0_6.RelationMember;
import org.openstreetmap.osmosis.core.domain.v0_6.Tag;
import org.openstreetmap.osmosis.core.domain.v0_6.Way;
import org.openstreetmap.osmosis.core.domain.v0_6.WayNode;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

import pl.fluence.gis.models.LineElement;
import pl.fluence.gis.models.PolygonElement;
import pl.fluence.gis.models.SymbolElement;
import pl.fluence.reader.database.ElementsDatabase;
import pl.fluence.reader.importers.OsmImporter;
import pl.fluence.reader.processors.AllElementsProccesor;
import pl.fluence.reader.processors.FilterElementProccesor;
import pl.fluence.reader.processors.GeoIdProccesor;
import pl.fluence.reader.processors.OsmElementProccesor;
import pl.fluence.reader.tag.TagHelper;

public class OsmToJsonEngine {
	private GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
	private ObjectMapper mapper = new ObjectMapper();
	private String prefix = "";
	
	File osmFile, targetFolder;
	FilterElementProccesor elementProccesor;

	private ElementsDatabase database;
	
	private List<PolygonElement> polygons;
	private List<LineElement> lines;
	private List<SymbolElement> symbols;
	
	public OsmToJsonEngine(File osmFile, File targetFolder, FilterElementProccesor elementsProccesor, String prefix) {
		this.osmFile = osmFile;
		this.targetFolder = targetFolder;
		this.elementProccesor = elementsProccesor;
		if(prefix != null) this.prefix = prefix;
	}

	public void run() {
		run(true, true, true);
	}

	public void run(boolean symbolsJson, boolean linesJson, boolean polygonsJson) {
		long timeId = System.currentTimeMillis();

		System.out.println("import osm");
		OsmImporter osmImporter = new OsmImporter();
		osmImporter.addProccessor(elementProccesor);
		osmImporter.proccesImport(osmFile);

		System.out.println("import brakujacych elementow");
		database = elementProccesor.getElementsDatabase();
		importNeededWaysAndNodesForRelations();
		importNeededNodesForWays();

		System.out.println("tworzenie obiektow json");
		polygons = createPolygons();
		lines = createLines();
		symbols = createSymbols();

		System.out.println("polygons count: " + polygons.size());
		System.out.println("lines count: " + lines.size());
		System.out.println("symbols count: " + symbols.size());

		System.out.println("zapis plikow json");
		if (polygonsJson)
			saveJsonToFile("polygons-" + timeId, polygons);
		if (linesJson)
			saveJsonToFile("lines-" + timeId, lines);
		if (symbolsJson)
			saveJsonToFile("symbols-" + timeId, symbols);
	}

	private void saveJsonToFile(String fileName, List listToSave) {

		File output = new File(targetFolder, prefix + "-" + fileName + ".json");

		try {
			output.createNewFile();
			mapper.writeValue(output, listToSave);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private List<SymbolElement> createSymbols() {
		List<SymbolElement> symbols = new ArrayList<SymbolElement>();

		for (Node node : database.getNodesMap().values()) {
			SymbolElement symbolElement = new SymbolElement();
			symbolElement.setId(node.getId());
			symbolElement.setTags(getTagsMap(node));

			Point point = geometryFactory.createPoint(new Coordinate(node.getLongitude(), node.getLatitude()));
			symbolElement.setPoint(point);
			symbols.add(symbolElement);
		}
		return symbols;
	}

	private List<LineElement> createLines() {
		List<Long> nodesToRemove = new LinkedList<Long>();

		List<LineElement> lines = new ArrayList<LineElement>();

		for (Way way : database.getWaysMap().values()) {
			LineElement lineElement = new LineElement();
			lineElement.setJSTPoints(getPointsListFromWay(way));
			lineElement.setTags(getTagsMap(way));
			lineElement.getIds().add(way.getId());
			lines.add(lineElement);

			for (WayNode wayNode : way.getWayNodes()) {
				nodesToRemove.add(wayNode.getNodeId());
			}
		}

		for (Long nodeId : nodesToRemove) {
			database.remove(nodeId);
		}

		return lines;
	}

	private List<PolygonElement> createPolygons() {
		List<Long> waysToRemove = new LinkedList<Long>();
		List<PolygonElement> polygons = new ArrayList<PolygonElement>();

		for (Relation relation : database.getRelationsMap().values()) {
			if (!TagHelper.containsOneValue("type", relation, "multipolygon", "boundary")) {
				System.err.println("pominiento relation id: " + relation.getId());
				continue;
			}

			long firstId = relation.getMembers().get(0).getMemberId();

			for (RelationMember relationMember : relation.getMembers()) {
				if (relationMember.getMemberType() != EntityType.Way) {
					System.err.println("relation id: " + relation.getId() + " zawiera nie way id:"
							+ relationMember.getMemberId() + " type: " + relationMember.getMemberType() + " role: "
							+ relationMember.getMemberRole());
				}

				Way way = (Way) database.getWaysMap().get(relationMember.getMemberId());
				if (way == null) {
					System.err.println("brak way " + way + " w database");
					break;
				}

				PolygonElement polygonElement = new PolygonElement();
				polygonElement.getIds().add(relationMember.getMemberId());
				polygonElement.setJSTPoints(getPointsListFromWay(way));
				polygonElement.setTags(getTagsMap(way));

				polygons.add(polygonElement);
				waysToRemove.add(relationMember.getMemberId());
			}
		}

		for (Long wayId : waysToRemove) {
			database.remove(wayId);
		}
		return polygons;
	}

	private Map<String, String> getTagsMap(Entity entity) {
		Map<String, String> tagsMap = new HashMap<String, String>();
		for (Tag tag : entity.getTags()) {
			if(isUnwantedTag(tag)) continue;
			tagsMap.put(tag.getKey(), tag.getValue());
		}

		return tagsMap;
	}

	private boolean isUnwantedTag(Tag tag) {
		if(tag.getKey().startsWith("source")) return true;
		if(tag.getKey().startsWith("created_by")) return true;
		if(tag.getKey().startsWith("ref")) return true;
		if(tag.getKey().startsWith("FIXME")) return true;
		
		return false;
	}

	private List<Point> getPointsListFromWay(Way way) {
		List<Point> points = new ArrayList<Point>();

		for (WayNode wayNode : way.getWayNodes()) {
			Node node = (Node) database.getEntity(wayNode.getNodeId());
			Point point = geometryFactory.createPoint(new Coordinate(node.getLongitude(), node.getLatitude()));
			points.add(point);
		}

		return points;
	}

	private void importNeededWaysAndNodesForRelations() {
		// Link relation with nodes and ways
		GeoIdProccesor geoIdProccesor = createGeoIdProcessorForWays();
		OsmImporter osmImporter = new OsmImporter();
		osmImporter.addProccessor(geoIdProccesor);
		osmImporter.proccesImport(osmFile);
		database.add(geoIdProccesor.getElementsDatabase());
	}

	private void importNeededNodesForWays() {
		// Link streets way with nodes
		GeoIdProccesor geoIdProccesor = createGeoIdProcessorForRelations();
		OsmImporter osmImporter = new OsmImporter();
		osmImporter.addProccessor(geoIdProccesor);
		osmImporter.proccesImport(osmFile);
		database.add(geoIdProccesor.getElementsDatabase());
	}

	private GeoIdProccesor createGeoIdProcessorForRelations() {
		Set<Long> waysAndNodesToFind = new HashSet<Long>();
		for (Relation relation : database.getRelationsMap().values()) {
			for (RelationMember relationMember : relation.getMembers()) {
				waysAndNodesToFind.add(relationMember.getMemberId());
			}
		}

		return new GeoIdProccesor(waysAndNodesToFind);
	}

	private GeoIdProccesor createGeoIdProcessorForWays() {
		Set<Long> nodesToFind = new HashSet<Long>();
		for (Way way : database.getWaysMap().values()) {
			for (WayNode wayNode : way.getWayNodes()) {
				nodesToFind.add(wayNode.getNodeId());
			}
		}

		return new GeoIdProccesor(nodesToFind);
	}

	public List<PolygonElement> getPolygons() {
		return polygons;
	}

	public List<LineElement> getLines() {
		return lines;
	}

	public List<SymbolElement> getSymbols() {
		return symbols;
	}
}
