package pl.fluence.reader.processors;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.openstreetmap.osmosis.core.domain.v0_6.Bound;
import org.openstreetmap.osmosis.core.domain.v0_6.Entity;
import org.openstreetmap.osmosis.core.domain.v0_6.Node;

public class NodesSquareProccesor extends EmptyElementsProccessor {

	private Map<Long, Node> overlapNodesMap = new HashMap<Long, Node>(1200000);
	private Map<Long, Node> nodesMap = new HashMap<Long, Node>(1200000);
	private final Bound insideBound, overlapBound;

	public NodesSquareProccesor(Bound insideBound, Bound overlapBound) {
		this.insideBound = insideBound;
		this.overlapBound = overlapBound;
		System.out.println("NodesSquareProccesor insideBound: " + insideBound + " overlapBound: " + overlapBound);
	}

	@Override
	public void proccesNode(Node node) {
		
		if (inInsideBound(node)) {
			nodesMap.put(node.getId(), node);
		}else if(inOverlapBound(node)){
//			overlapNodesMap.put(node.getId(), node);
		}
	}

	private boolean inInsideBound(Node node) {
		if (node.getLongitude() >= insideBound.getLeft() && node.getLongitude() <= insideBound.getRight() 
				&& node.getLatitude() >= insideBound.getBottom()  && node.getLatitude() <= insideBound.getTop()) {
			return true;
		}
		return false;
	}

	private boolean inOverlapBound(Node node) {
		if (node.getLongitude() >= insideBound.getLeft() && node.getLongitude() <= insideBound.getRight() 
				&& node.getLatitude() >= insideBound.getBottom()  && node.getLatitude() <= insideBound.getTop()) {
			return true;
		}
		return false;
	}
	
	public boolean containsNode(long nodeId) {
		return nodesMap.containsKey(nodeId);
	}

	public Collection<Node> getNodes() {
		LinkedList<Node> nodes = new LinkedList<Node>(nodesMap.values());
		nodes.addAll(overlapNodesMap.values());
		return nodes;
	}
}
