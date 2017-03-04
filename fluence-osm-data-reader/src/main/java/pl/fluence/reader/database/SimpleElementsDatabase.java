package pl.fluence.reader.database;

import java.util.HashMap;
import java.util.Map;

import org.openstreetmap.osmosis.core.domain.v0_6.Bound;
import org.openstreetmap.osmosis.core.domain.v0_6.Entity;
import org.openstreetmap.osmosis.core.domain.v0_6.Node;
import org.openstreetmap.osmosis.core.domain.v0_6.Relation;
import org.openstreetmap.osmosis.core.domain.v0_6.Way;

public class SimpleElementsDatabase implements ElementsDatabase {
	private Map<Long, Node> nodesMap = new HashMap<Long, Node>();
	private Map<Long, Way> waysMap = new HashMap<Long, Way>();
	private Map<Long, Relation> relationsMap = new HashMap<Long, Relation>();
	private Map<Long, Bound> boundsMap = new HashMap<Long, Bound>();

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

	public void addEntity(Entity entity) {
		if (entity instanceof Node) {
			addNode((Node) entity);
		} else if (entity instanceof Way) {
			addWay((Way) entity);
		} else if (entity instanceof Relation) {
			addRelation((Relation) entity);
		} else if (entity instanceof Bound) {
			addBound((Bound) entity);
		}
	}

	public void addBound(Bound entity) {
		boundsMap.put(entity.getId(), entity);
	}

	public Entity getEntity(Long id) {
		return nodesMap.containsKey(id) ? nodesMap.get(id)
				: waysMap.containsKey(id) ? waysMap.get(id)
						: relationsMap.containsKey(id) ? relationsMap.get(id) : boundsMap.get(id);
	}

	public void addRelation(Relation entity) {
		relationsMap.put(entity.getId(), entity);
	}

	public void addWay(Way entity) {
		waysMap.put(entity.getId(), entity);
	}

	public void addNode(Node entity) {
		nodesMap.put(entity.getId(), entity);
	}

	public void add(ElementsDatabase otherDatabase) {
		for(Way way : otherDatabase.getWaysMap().values()){
			waysMap.put(way.getId(), way);
		}
		for(Bound bound : otherDatabase.getBoundsMap().values()){
			boundsMap.put(bound.getId(), bound);
		}
		for(Node node : otherDatabase.getNodesMap().values()){
			nodesMap.put(node.getId(), node);
		}
		for(Relation relation : otherDatabase.getRelationsMap().values()){
			relationsMap.put(relation.getId(), relation);
		}
	}

	public void remove(ElementsDatabase otherDatabase) {
		for(Way way : otherDatabase.getWaysMap().values()){
			waysMap.remove(way);
		}
		for(Bound bound : otherDatabase.getBoundsMap().values()){
			boundsMap.remove(bound);
		}
		for(Node node : otherDatabase.getNodesMap().values()){
			nodesMap.remove(node);
		}
		for(Relation relation : otherDatabase.getRelationsMap().values()){
			relationsMap.remove(relation);
		}
	}

	public void addEntites(Iterable<Entity> entitiesToAdds) {
		for(Entity entity : entitiesToAdds){
			addEntity(entity);
		}
	}

	public void addWays(Iterable<Way> waysToAdds) {
		for(Way way : waysToAdds){
			addWay(way);
		}		
	}

	public void addNodes(Iterable<Node> nodeToAdds) {
		for(Node node : nodeToAdds){
			addNode(node);
		}	
	}
	
	public void addRelations(Iterable<Relation> relationToAdds) {
		for(Relation relation : relationToAdds){
			addRelation(relation);
		}	
	}
	
	@Override
	public String toString() {
		return "Database nodes: " + nodesMap.size() + " ways: " + waysMap.size() + " relations: " + relationsMap.size(); 
	}
	
	public void remove(long elementId) {
		nodesMap.remove(elementId);
		waysMap.remove(elementId);
		relationsMap.remove(elementId);
		boundsMap.remove(elementId);
	}
}
