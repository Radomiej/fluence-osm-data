package pl.fluence.gis;
import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import pl.fluence.gis.OsmToJsonEngine;
import pl.fluence.reader.filters.KeyValuesFilter;
import pl.fluence.reader.importers.OsmImporter;
import pl.fluence.reader.processors.FilterElementProccesor;

public class NodesToMapDbTest {

	File mapFile, outputFile;

	@Before
	public void setUp() throws Exception {
		outputFile = new File("H:/mapy/layers");
		ClassLoader classLoader = getClass().getClassLoader();	
		mapFile = new File(classLoader.getResource("andorra-latest.osm.pbf").getFile());
//		mapFile = new File("C://mapy//lubuskie-latest.osm.pbf");
	}
	
//	@Test
	public void testConversion() {
		byte[] positionBytes = NodesProccesor.toByteArrayPosition(15.12345f, 15.12345f);
		long position = NodesProccesor.toLong(positionBytes);
		System.out.println("position: " + position);
		
		float latitude = NodesProccesor.toLatitude(position);
		float longitude = NodesProccesor.toLongitudee(position);
		
		assertEquals(15.12345f, latitude, 0.001f);
		assertEquals(15.12345f, longitude, 0.001f);
	}
	
	@Test
	public void test() {
		OsmImporter osmImporter = new OsmImporter();
		NodesProccesor nodesProccesor = new NodesProccesor();
		osmImporter.addProccessor(nodesProccesor);
		osmImporter.proccesImport(mapFile);
	}
	
}
