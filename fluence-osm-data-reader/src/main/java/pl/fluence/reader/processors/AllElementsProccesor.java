package pl.fluence.reader.processors;

import org.openstreetmap.osmosis.core.domain.v0_6.Bound;
import org.openstreetmap.osmosis.core.domain.v0_6.Node;
import org.openstreetmap.osmosis.core.domain.v0_6.Relation;
import org.openstreetmap.osmosis.core.domain.v0_6.Way;

import pl.fluence.reader.database.SimpleElementsDatabase;

public class AllElementsProccesor implements OsmElementProccesor {

	private SimpleElementsDatabase elementDatabase = new SimpleElementsDatabase();

	public void complete() {

	}

	public void proccesWay(Way way) {
		elementDatabase.addEntity(way);
	}

	public void proccesRelation(Relation relation) {
		elementDatabase.addEntity(relation);
	}

	public void proccesBound(Bound bound) {
		elementDatabase.addEntity(bound);
	}

	public void proccesNode(Node node) {
		elementDatabase.addEntity(node);
	}

	public SimpleElementsDatabase getElementDatabase() {
		return elementDatabase;
	}
	
}
