package pl.fluence.reader.processors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openstreetmap.osmosis.core.domain.v0_6.Bound;
import org.openstreetmap.osmosis.core.domain.v0_6.Node;
import org.openstreetmap.osmosis.core.domain.v0_6.Relation;
import org.openstreetmap.osmosis.core.domain.v0_6.Tag;
import org.openstreetmap.osmosis.core.domain.v0_6.Way;

public class AllNodeProccesor implements OsmElementProccesor {

	private Map<Long, Node> nodesMap = new HashMap<Long, Node>();
	private Map<Long, Way> waysMap = new HashMap<Long, Way>();
	private Map<Long, Relation> relationsMap = new HashMap<Long, Relation>();
	private Map<Long, Bound> boundsMap = new HashMap<Long, Bound>();

	public void complete() {

	}

	public void proccesWay(Way way) {
		waysMap.put(way.getId(), way);
	}

	public void proccesRelation(Relation relation) {
		relationsMap.put(relation.getId(), relation);
	}

	public void proccesBound(Bound bound) {
		boundsMap.put(bound.getId(), bound);
	}

	public void proccesNode(Node node) {
		nodesMap.put(node.getId(), node);
	}

	public Map<Long, Node> getNodesMap() {
		return nodesMap;
	}

	public Map<Long, Way> getWaysMap() {
		return waysMap;
	}

	public Map<Long, Relation> getRelationsMap() {
		return relationsMap;
	}

	public Map<Long, Bound> getBoundsMap() {
		return boundsMap;
	}
}
