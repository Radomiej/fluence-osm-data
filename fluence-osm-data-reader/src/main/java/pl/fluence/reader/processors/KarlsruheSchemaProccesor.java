package pl.fluence.reader.processors;

import java.util.HashSet;
import java.util.Set;

import org.openstreetmap.osmosis.core.domain.v0_6.Bound;
import org.openstreetmap.osmosis.core.domain.v0_6.Node;
import org.openstreetmap.osmosis.core.domain.v0_6.Relation;
import org.openstreetmap.osmosis.core.domain.v0_6.Tag;
import org.openstreetmap.osmosis.core.domain.v0_6.Way;

public class KarlsruheSchemaProccesor implements OsmElementProccesor {

	private Set<Node> validNodes = new HashSet<Node>();

	public void complete() {
	}

	public void proccesWay(Way way) {
	}

	public void proccesRelation(Relation relation) {
	}

	public void proccesBound(Bound bound) {
	}

	public void proccesNode(Node node) {
		for (Tag tag : node.getTags()) {
			if (tag.getKey().contains("addr:housenumber")) {
				validNodes.add(node);
				return;
			}
		}
	}

	public int getResultsSize() {
		return validNodes.size();
	}

	public Iterable<Node> getResults() {
		return validNodes;
	}
}
