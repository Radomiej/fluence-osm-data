package pl.fluence.reader.processors;

import org.openstreetmap.osmosis.core.domain.v0_6.Bound;
import org.openstreetmap.osmosis.core.domain.v0_6.Node;
import org.openstreetmap.osmosis.core.domain.v0_6.Relation;
import org.openstreetmap.osmosis.core.domain.v0_6.Way;

public interface OsmElementProccesor {
	public void complete();
	public void proccesWay(Way way);
	public void proccesRelation(Relation relation);
	public void proccesBound(Bound bound);
	public void proccesNode(Node node);
}
