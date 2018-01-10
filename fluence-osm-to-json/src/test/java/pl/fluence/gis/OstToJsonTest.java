package pl.fluence.gis;
import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import pl.fluence.gis.OsmToJsonEngine;
import pl.fluence.reader.filters.KeyValuesFilter;
import pl.fluence.reader.processors.FilterElementProccesor;

public class OstToJsonTest {

	File mapFile, outputFile;

	@Before
	public void setUp() throws Exception {
		outputFile = new File("H:/mapy/layers");
		ClassLoader classLoader = getClass().getClassLoader();	
		mapFile = new File(classLoader.getResource("andorra-latest.osm.pbf").getFile());
//		mapFile = new File("C://mapy//lubuskie-latest.osm.pbf");
	}
	
	@Test
	public void testImportStreets() {
		KeyValuesFilter keyFilters = new KeyValuesFilter("highway", "motorway", "motorway_link", "trunk", "trunk_link", "primary", "primary_link", "secondary", "secondary_link", "tertiary", "tertiary_link", "unclassified", "residential");
		FilterElementProccesor proccesor = new FilterElementProccesor();
		proccesor.addOrFilter(keyFilters);
		
		OsmToJsonEngine osmToJsonEngine = new OsmToJsonEngine(mapFile, outputFile, proccesor, "streets");
		osmToJsonEngine.run(false, true, false);
	}
	
	@Test
	public void testImportStreetsOther() {
		KeyValuesFilter keyFilters = new KeyValuesFilter("highway", "living_street", "service", "road", "track");
		FilterElementProccesor proccesor = new FilterElementProccesor();
		proccesor.addOrFilter(keyFilters);
		
		OsmToJsonEngine osmToJsonEngine = new OsmToJsonEngine(mapFile, outputFile, proccesor, "streets-other");
		osmToJsonEngine.run(false, true, false);
	}
	
	@Test
	public void testImportRiver() {
		KeyValuesFilter keyFilters = new KeyValuesFilter("waterway", "canal", "river", "ditch");
		FilterElementProccesor proccesor = new FilterElementProccesor();
		proccesor.addOrFilter(keyFilters);
		
		OsmToJsonEngine osmToJsonEngine = new OsmToJsonEngine(mapFile, outputFile, proccesor, "rivers");
		osmToJsonEngine.run(false, true, false);
	}
	
	@Test
	public void testImportRiverBank() {
		KeyValuesFilter keyFilters = new KeyValuesFilter("waterway", "riverbank");
		FilterElementProccesor proccesor = new FilterElementProccesor();
		proccesor.addOrFilter(keyFilters);
		
		OsmToJsonEngine osmToJsonEngine = new OsmToJsonEngine(mapFile, outputFile, proccesor, "riverbanks");
		osmToJsonEngine.run(false, true, false);
	}
	
	@Test
	public void testImportLake() {
		KeyValuesFilter keyFilters = new KeyValuesFilter("natural", "water");
		FilterElementProccesor proccesor = new FilterElementProccesor();
		proccesor.addOrFilter(keyFilters);
		
		OsmToJsonEngine osmToJsonEngine = new OsmToJsonEngine(mapFile, outputFile, proccesor, "lakes");
		osmToJsonEngine.run(false, true, false);
	}
	
	@Test
	public void testImportForest() {
		KeyValuesFilter keyFilters = new KeyValuesFilter("landuse", "forest");
		FilterElementProccesor proccesor = new FilterElementProccesor();
		proccesor.addOrFilter(keyFilters);
		
		OsmToJsonEngine osmToJsonEngine = new OsmToJsonEngine(mapFile, outputFile, proccesor, "forest");
		osmToJsonEngine.run(false, true, true);
	}
	
	@Test
	public void testImportTree() {
		KeyValuesFilter keyFilters = new KeyValuesFilter("natural", "tree");
		FilterElementProccesor proccesor = new FilterElementProccesor();
		proccesor.addOrFilter(keyFilters);
		
		OsmToJsonEngine osmToJsonEngine = new OsmToJsonEngine(mapFile, outputFile, proccesor, "tree");
		osmToJsonEngine.run(true, true, false);
	}
	
	@Test
	public void testImportGrass() {
		KeyValuesFilter keyFilters = new KeyValuesFilter("landuse", "meadow", "scrub", "wetland");
		FilterElementProccesor proccesor = new FilterElementProccesor();
		proccesor.addOrFilter(keyFilters);
		
		OsmToJsonEngine osmToJsonEngine = new OsmToJsonEngine(mapFile, outputFile, proccesor, "grass");
		osmToJsonEngine.run(false, true, false);
	}

	@Test
	public void testImportBuildings() {
		KeyValuesFilter keyFilters = new KeyValuesFilter("building", "yes", "house", "residential", "garage", "apartments", "hut", "industrial", "shed", "roof", "detached", "commercial", "garages", "terrace", "school", "retail", "greenhouse", "construction", "farm_auxiliary", "church", "barn", "warehouse", "service", "cabin", "farm");
		FilterElementProccesor proccesor = new FilterElementProccesor();
		proccesor.addOrFilter(keyFilters);
		
		OsmToJsonEngine osmToJsonEngine = new OsmToJsonEngine(mapFile, outputFile, proccesor, "buildings");
		osmToJsonEngine.run(true, true, true);
	}
	
	@Test
	public void testImportRailways() {
		KeyValuesFilter keyFilters = new KeyValuesFilter("railway", "tram", "rail");
		FilterElementProccesor proccesor = new FilterElementProccesor();
		proccesor.addOrFilter(keyFilters);
		
		OsmToJsonEngine osmToJsonEngine = new OsmToJsonEngine(mapFile, outputFile, proccesor, "railways");
		osmToJsonEngine.run(false, true, false);
	}
}
