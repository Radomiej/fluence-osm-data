package pl.fluence.reader.processors;

import java.util.HashSet;
import java.util.Set;

import org.openstreetmap.osmosis.core.domain.v0_6.Node;
import org.openstreetmap.osmosis.core.domain.v0_6.Way;
import org.openstreetmap.osmosis.core.domain.v0_6.WayNode;

import pl.fluence.reader.database.SimpleElementsDatabase;

public class NodeCompleterProccesor extends EmptyElementsProccessor {

	private SimpleElementsDatabase elementsDatabase;
	private Set<Long> neededNodes = new HashSet<Long>();
	
	public NodeCompleterProccesor(SimpleElementsDatabase squareDatabase) {
		this.elementsDatabase = squareDatabase;
		for(Way way : squareDatabase.getWaysMap().values()){
			for(WayNode wayNode : way.getWayNodes()){
				if(!squareDatabase.getNodesMap().containsKey(wayNode.getNodeId())){
					neededNodes.add(wayNode.getNodeId());
				}
			}
		}
	}
	
	@Override
	public void proccesNode(Node node) {
		if(neededNodes.contains(node.getId())){
			elementsDatabase.addNode(node);
			neededNodes.remove(node.getId());
//			System.out.println("Dodaje brakujacy node");
		}
	}
	
}
