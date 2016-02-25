package pl.fluence.reader.processors;

import java.util.ArrayList;
import java.util.List;

import org.openstreetmap.osmosis.core.domain.v0_6.Bound;
import org.openstreetmap.osmosis.core.domain.v0_6.Entity;
import org.openstreetmap.osmosis.core.domain.v0_6.Node;
import org.openstreetmap.osmosis.core.domain.v0_6.Relation;
import org.openstreetmap.osmosis.core.domain.v0_6.Tag;
import org.openstreetmap.osmosis.core.domain.v0_6.Way;

import pl.fluence.reader.database.ElementsDatabase;
import pl.fluence.reader.database.OsmDatabaseCollector;
import pl.fluence.reader.database.SimpleElementsDatabase;
import pl.fluence.reader.filters.OsmElementFilter;
import pl.fluence.reader.tag.TagPrettyPrinter;

public class FilterElementProccesor implements OsmElementProccesor, OsmDatabaseCollector {

	private List<OsmElementFilter> filters = new ArrayList<OsmElementFilter>();
	private ElementsDatabase database = new SimpleElementsDatabase();
	
	public void complete() {

	}

	public void proccesWay(Way way) {
		filterEntity(way);
	}

	public void proccesRelation(Relation relation) {
		filterEntity(relation);
	}

	public void proccesBound(Bound bound) {
		filterEntity(bound);
	}

	public void proccesNode(Node node) {
		filterEntity(node);
	}

	private void filterEntity(Entity entity) {
		for (OsmElementFilter filter : filters) {
			filter.startElement(entity);
			for (Tag tag : entity.getTags()) {
				filter.checkTag(tag.getKey(), tag.getValue());
			}
			boolean checkResult = filter.getResultCheck();
			filter.stopElement();
			
			if(checkResult){
				database.addEntity(entity);
				return;
			}
		}
	}

	public ElementsDatabase getElementsDatabase() {
		return database;
	}
	
	public void addOrFilter(OsmElementFilter filter){
		filters.add(filter);
	}
}
