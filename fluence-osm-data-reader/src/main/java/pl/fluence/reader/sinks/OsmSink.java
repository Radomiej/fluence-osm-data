package pl.fluence.reader.sinks;

import java.util.Map;

import org.openstreetmap.osmosis.core.container.v0_6.EntityContainer;
import org.openstreetmap.osmosis.core.domain.v0_6.Bound;
import org.openstreetmap.osmosis.core.domain.v0_6.Entity;
import org.openstreetmap.osmosis.core.domain.v0_6.Node;
import org.openstreetmap.osmosis.core.domain.v0_6.Relation;
import org.openstreetmap.osmosis.core.domain.v0_6.Way;
import org.openstreetmap.osmosis.core.task.v0_6.Sink;

public abstract class OsmSink implements Sink {

	public void complete() {
	}
	
	public void initialize(Map<String, Object> metaData) {
	}

	public void process(EntityContainer entityContainer) {
		Entity entity = entityContainer.getEntity();

		if (entity instanceof Node) {
			parseNode((Node) entity);
		}else if (entity instanceof Way) {
			parseWay((Way) entity);
		}else if (entity instanceof Relation) {
			parseRelation((Relation) entity);
		}else if (entity instanceof Bound) {
			parseBound((Bound) entity);
		}
		else {
			 System.out.println("Inny: " + entity.getType());
		}		
	}

	
	protected abstract void parseBound(Bound entity);
	
	protected abstract void parseRelation(Relation entity);

	protected abstract void parseWay(Way entity);

	protected abstract void parseNode(Node entity);

}
