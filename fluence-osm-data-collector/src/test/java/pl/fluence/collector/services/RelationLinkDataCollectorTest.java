package pl.fluence.collector.services;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.vividsolutions.jts.geom.MultiPolygon;

import pl.fluence.collector.tools.CollisionChecker;
import pl.fluence.reader.importers.OsmImporter;
import pl.fluence.reader.processors.AllElementsProccesor;
import pl.fluence.reader.processors.KarlsruheSchemaProccesor;
import pl.fluence.reader.processors.KeysElementProccesor;

public class RelationLinkDataCollectorTest {

	File mapFile;

	@Before
	public void setUp() throws Exception {
		ClassLoader classLoader = getClass().getClassLoader();
//		mapFile = new File(classLoader.getResource("andorra-latest.osm.pbf").getFile());
		mapFile = new File("C://mapy//lubuskie-latest.osm.pbf");
	}

	@Test
	public void test() {
		OsmImporter osmImporter = new OsmImporter();
		AllElementsProccesor allNodeProccesor = new AllElementsProccesor();
		osmImporter.addProccessor(allNodeProccesor);
		osmImporter.proccesImport(mapFile);
		
		RelationLinkDataCollector relationLinkDataCollector = new RelationLinkDataCollector(allNodeProccesor.getElementDatabase());
		relationLinkDataCollector.procces();
		System.out.println("Relation links: " + relationLinkDataCollector.relationLinks.size());
		relationLinkDataCollector.getRelationMap();
		
		
		for(RelationLink relationLink : relationLinkDataCollector.getRelationMap().values()){
			if(relationLink.getRelation().getId() == 130969){
//				System.out.println("Brak granicy dla : " + relationLink.getRelation() + " multipolygon: " + relationLink.getMultiPolygon());
			}
			MultiPolygon multiPolygon = relationLink.getMultiPolygon();
			if (multiPolygon != null && CollisionChecker.checkMultipolygonAndPointText(multiPolygon.toString(), 52.757618,
					15.262077)) {
				System.out.println("Kolizja: " + relationLink.getRelation());
			}
		}
	}

}
