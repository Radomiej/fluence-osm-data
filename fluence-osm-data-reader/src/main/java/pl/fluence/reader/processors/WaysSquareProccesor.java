package pl.fluence.reader.processors;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.openstreetmap.osmosis.core.domain.v0_6.Bound;
import org.openstreetmap.osmosis.core.domain.v0_6.Node;
import org.openstreetmap.osmosis.core.domain.v0_6.Relation;
import org.openstreetmap.osmosis.core.domain.v0_6.RelationMember;
import org.openstreetmap.osmosis.core.domain.v0_6.Way;
import org.openstreetmap.osmosis.core.domain.v0_6.WayNode;

public class WaysSquareProccesor extends EmptyElementsProccessor {

	private final NodesSquareProccesor nodesSquareProccesor;
	
	private Map<Long, Way> wayMap = new HashMap<Long, Way>(2500000);
	
	public WaysSquareProccesor(NodesSquareProccesor nodesSquareProccesor) {
		this.nodesSquareProccesor = nodesSquareProccesor;
	}

	@Override
	public void proccesWay(Way way) {
		for(WayNode wayNode : way.getWayNodes()){
			if(nodesSquareProccesor.containsNode(wayNode.getNodeId())){
				wayMap.put(way.getId(), way);
				return;
			}
		}
	}

	public boolean containsWay(long wayId) {
		return wayMap.containsKey(wayId);
	}

	public Collection<Way> getWays() {
		return wayMap.values();
	}
}
