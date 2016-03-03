package pl.fluence.reader.database;

import java.util.Map;

import org.openstreetmap.osmosis.core.domain.v0_6.Bound;
import org.openstreetmap.osmosis.core.domain.v0_6.Entity;
import org.openstreetmap.osmosis.core.domain.v0_6.Node;
import org.openstreetmap.osmosis.core.domain.v0_6.Relation;
import org.openstreetmap.osmosis.core.domain.v0_6.Way;

public interface ElementsDatabase {
	public Map<Long, Node> getNodesMap();
	public Map<Long, Way> getWaysMap();
	public Map<Long, Relation> getRelationsMap();
	public Map<Long, Bound> getBoundsMap();
	
	public void addEntity(Entity entity);
	public Entity getEntity(Long id);
	public void add(ElementsDatabase otherDatabase);
	public void remove(ElementsDatabase otherDatabase);
}
