package pl.fluence.reader.processors;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openstreetmap.osmosis.core.domain.v0_6.Bound;
import org.openstreetmap.osmosis.core.domain.v0_6.Node;
import org.openstreetmap.osmosis.core.domain.v0_6.Relation;
import org.openstreetmap.osmosis.core.domain.v0_6.Tag;
import org.openstreetmap.osmosis.core.domain.v0_6.Way;

public class KarlsruheSchemaProccesor implements OsmElementProccesor {

	private Set<Node> validNodes = new HashSet<Node>();

	public void complete() {
		// TODO Auto-generated method stub

	}

	public void proccesWay(Way way) {
		// TODO Auto-generated method stub

	}

	public void proccesRelation(Relation relation) {
		// TODO Auto-generated method stub

	}

	public void proccesBound(Bound bound) {
		// TODO Auto-generated method stub

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
