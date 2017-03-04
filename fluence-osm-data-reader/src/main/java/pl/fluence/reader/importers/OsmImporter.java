package pl.fluence.reader.importers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.openstreetmap.osmosis.core.domain.v0_6.Bound;
import org.openstreetmap.osmosis.core.domain.v0_6.Node;
import org.openstreetmap.osmosis.core.domain.v0_6.Relation;
import org.openstreetmap.osmosis.core.domain.v0_6.Way;
import org.openstreetmap.osmosis.core.task.v0_6.Sink;
import org.openstreetmap.osmosis.pbf2.v0_6.PbfReader;

import pl.fluence.reader.database.SimpleElementsDatabase;
import pl.fluence.reader.processors.OsmElementProccesor;
import pl.fluence.reader.sinks.OsmSink;

public class OsmImporter implements Importer {
	public static final String SEPARATOR = System.lineSeparator();
	
	private List<OsmElementProccesor> proccesors = new ArrayList<OsmElementProccesor>();
	
	public void addProccessor(OsmElementProccesor proccesor){
		proccesors.add(proccesor);
	}
	
	public void proccesImport(File pbfFile) {
		PbfReader reader = new PbfReader(pbfFile, 4);
		Sink sink = new OsmSink() {

			@Override
			protected void parseWay(Way entity) {
				for(OsmElementProccesor proccesor : proccesors){
					proccesor.proccesWay(entity);
				}
			}

			@Override
			protected void parseRelation(Relation entity) {
				for(OsmElementProccesor proccesor : proccesors){
					proccesor.proccesRelation(entity);;
				}
			}

			@Override
			protected void parseBound(Bound entity) {
				for(OsmElementProccesor proccesor : proccesors){
					proccesor.proccesBound(entity);
				}
			}

			@Override
			protected void parseNode(Node entity) {
				for(OsmElementProccesor proccesor : proccesors){
					proccesor.proccesNode(entity);
				}
			}

			public void release() {
				for (OsmElementProccesor proccesor : proccesors) {
					proccesor.complete();
				}
			}
		};
		reader.setSink(sink);
		reader.run();
	}

	public void proccesImport(SimpleElementsDatabase elementsDatabase) {
		for(Bound bound : elementsDatabase.getBoundsMap().values()){
			for(OsmElementProccesor proccesor : proccesors){
				proccesor.proccesBound(bound);
			}
		}
		for(Node node : elementsDatabase.getNodesMap().values()){
			for(OsmElementProccesor proccesor : proccesors){
				proccesor.proccesNode(node);
			}
		}
		for(Way way : elementsDatabase.getWaysMap().values()){
			for(OsmElementProccesor proccesor : proccesors){
				proccesor.proccesWay(way);
			}
		}
		for(Relation relation : elementsDatabase.getRelationsMap().values()){
			for(OsmElementProccesor proccesor : proccesors){
				proccesor.proccesRelation(relation);
			}
		}
		for (OsmElementProccesor proccesor : proccesors) {
			proccesor.complete();
		}
	}
	
}
