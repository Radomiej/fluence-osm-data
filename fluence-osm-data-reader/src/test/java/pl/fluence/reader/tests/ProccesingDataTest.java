package pl.fluence.reader.tests;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import pl.fluence.reader.importers.OsmImporter;
import pl.fluence.reader.processors.KeysElementProccesor;
import pl.fluence.reader.processors.AllNodeProccesor;
import pl.fluence.reader.processors.KarlsruheSchemaProccesor;
import pl.fluence.reader.processors.OsmElementProccesor;

public class ProccesingDataTest {

	File mapFile;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@Before
	public void setUp() throws Exception {
		ClassLoader classLoader = getClass().getClassLoader();
		mapFile = new File(classLoader.getResource("andorra-latest.osm.pbf").getFile());
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		
		OsmImporter osmImporter = new OsmImporter();
		KarlsruheSchemaProccesor karlsruheSchemaProccesor = new KarlsruheSchemaProccesor();
		KeysElementProccesor keysProccesor = new KeysElementProccesor();
		keysProccesor.addValidKey("addr");
		
		AllNodeProccesor allNodeProccesor = new AllNodeProccesor();
		osmImporter.addProccessor(karlsruheSchemaProccesor);
		osmImporter.addProccessor(keysProccesor);
		osmImporter.addProccessor(allNodeProccesor);
		osmImporter.proccesImport(mapFile);
		assertEquals(39, karlsruheSchemaProccesor.getResultsSize());
		assertEquals(81, keysProccesor.getValidNodes().size());
		assertEquals(113, keysProccesor.getValidWays().size());
		assertEquals(173598, allNodeProccesor.getNodesMap().keySet().size());
		assertEquals(6756, allNodeProccesor.getWaysMap().keySet().size());
		assertEquals(158, allNodeProccesor.getRelationsMap().keySet().size());
		assertEquals(1, allNodeProccesor.getBoundsMap().keySet().size());
		
	}

}
