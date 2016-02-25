package pl.fluence.reader.models;

import java.util.HashMap;
import java.util.Map;

import org.openstreetmap.osmosis.core.domain.v0_6.Bound;
import org.openstreetmap.osmosis.core.domain.v0_6.Entity;
import org.openstreetmap.osmosis.core.domain.v0_6.Node;
import org.openstreetmap.osmosis.core.domain.v0_6.Relation;
import org.openstreetmap.osmosis.core.domain.v0_6.Way;

public class SimpleElementsDatabase {
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
	
	public void addEntity(Entity entity){
		if (entity instanceof Node) {
			addNode((Node) entity);
		}else if (entity instanceof Way) {
			addWay((Way) entity);
		}else if (entity instanceof Relation) {
			addRelation((Relation) entity);
		}else if(entity instanceof Bound) {
			addBound((Bound) entity);
		}
	}

	public void addBound(Bound entity) {
		boundsMap.put(entity.getId(), entity);		
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
}
