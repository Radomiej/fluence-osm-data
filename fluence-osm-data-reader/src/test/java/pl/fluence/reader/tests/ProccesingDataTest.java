package pl.fluence.reader.tests;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openstreetmap.osmosis.core.domain.v0_6.Entity;
import org.openstreetmap.osmosis.core.domain.v0_6.Node;
import org.openstreetmap.osmosis.core.domain.v0_6.Tag;
import org.openstreetmap.osmosis.core.domain.v0_6.Way;

import pl.fluence.reader.database.ElementsDatabase;
import pl.fluence.reader.filters.AndFilter;
import pl.fluence.reader.filters.KeyFilter;
import pl.fluence.reader.filters.KeyValuesFilter;
import pl.fluence.reader.filters.StringComparator;
import pl.fluence.reader.importers.OsmImporter;
import pl.fluence.reader.processors.KeysElementProccesor;
import pl.fluence.reader.processors.AllElementsProccesor;
import pl.fluence.reader.processors.FilterElementProccesor;
import pl.fluence.reader.processors.KarlsruheSchemaProccesor;
import pl.fluence.reader.processors.OsmElementProccesor;
import pl.fluence.reader.tag.TagHelper;
import pl.fluence.reader.tag.TagPrettyPrinter;

public class ProccesingDataTest {

	File mapFile;
	OsmImporter osmImporter;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@Before
	public void setUp() throws Exception {
		ClassLoader classLoader = getClass().getClassLoader();
		mapFile = new File(classLoader.getResource("andorra-latest.osm.pbf").getFile());
		osmImporter = new OsmImporter();
	}

	@After
	public void tearDown() throws Exception {
	}

	 @Test
	public void testKeysTollProccessor() {
		mapFile = new File("C://mapy//lubuskie-latest.osm.pbf");
		
		KeyFilter highwayKeyFilter = new KeyFilter("highway");
		KeyFilter tollKeyFilter = new KeyFilter("toll:");
		KeyValuesFilter tollKeyValuesFilter = new KeyValuesFilter("toll", "yes");
		tollKeyValuesFilter.setKeyComparator(StringComparator.CONTAINS);
		
		AndFilter andFilter = new AndFilter(tollKeyValuesFilter, highwayKeyFilter);
		FilterElementProccesor filterElementProccesor = new FilterElementProccesor();
		filterElementProccesor.addOrFilter(andFilter);
		
		
		KeysElementProccesor keysProccesor = new KeysElementProccesor(true, true, false);
		keysProccesor.setContainsChecker(false);
		keysProccesor.addValidKey("toll");
		keysProccesor.addValidKey("toll:hsv");
		osmImporter.addProccessor(filterElementProccesor);
		osmImporter.proccesImport(mapFile);
		
		ElementsDatabase elementsDatabase = filterElementProccesor.getElementsDatabase();
		for(Entity entity : elementsDatabase.getWaysMap().values()){
			System.out.println(TagPrettyPrinter.prettyPrintTagCollection(entity.getTags()));
		}
		System.out.println("Razem: " + elementsDatabase.getWaysMap().values().size());
		assertEquals(494, keysProccesor.getValidNodes().size());
	}

//	@Test
	public void testKeysProccessor() {
		KeysElementProccesor keysProccesor = new KeysElementProccesor(true, true, false);
		keysProccesor.setAllChecker(true);
		keysProccesor.addValidKey("addr");
		keysProccesor.addValidKey("name");
		osmImporter.addProccessor(keysProccesor);
		osmImporter.proccesImport(mapFile);
		for (Node node : keysProccesor.getValidNodes()) {
			for (Tag tag : node.getTags()) {
				System.out.println(tag.getKey() + " = " + tag.getValue());
			}
		}
		System.out.println("---------");

		assertEquals(81, keysProccesor.getValidNodes().size());
		assertEquals(113, keysProccesor.getValidWays().size());
	}

//	@Test
	public void testAllElementsProccessor() {
		AllElementsProccesor allNodeProccesor = new AllElementsProccesor();
		osmImporter.addProccessor(allNodeProccesor);
		osmImporter.proccesImport(mapFile);
		assertEquals(173598, allNodeProccesor.getElementDatabase().getNodesMap().keySet().size());
		assertEquals(6756, allNodeProccesor.getElementDatabase().getWaysMap().keySet().size());
		assertEquals(158, allNodeProccesor.getElementDatabase().getRelationsMap().keySet().size());
		assertEquals(1, allNodeProccesor.getElementDatabase().getBoundsMap().keySet().size());
	}

//	@Test
	public void testKarlsruheProccessor() {
		KarlsruheSchemaProccesor karlsruheSchemaProccesor = new KarlsruheSchemaProccesor();
		osmImporter.addProccessor(karlsruheSchemaProccesor);
		osmImporter.proccesImport(mapFile);
		assertEquals(39, karlsruheSchemaProccesor.getResultsSize());
	}
}
