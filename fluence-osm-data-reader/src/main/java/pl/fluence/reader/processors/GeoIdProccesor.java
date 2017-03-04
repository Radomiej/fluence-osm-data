package pl.fluence.reader.processors;

import java.util.Set;

import org.openstreetmap.osmosis.core.domain.v0_6.Bound;
import org.openstreetmap.osmosis.core.domain.v0_6.Entity;
import org.openstreetmap.osmosis.core.domain.v0_6.EntityType;
import org.openstreetmap.osmosis.core.domain.v0_6.Node;
import org.openstreetmap.osmosis.core.domain.v0_6.Relation;
import org.openstreetmap.osmosis.core.domain.v0_6.Way;

import pl.fluence.reader.database.ElementsDatabase;
import pl.fluence.reader.database.OsmDatabaseCollector;
import pl.fluence.reader.database.SimpleElementsDatabase;

public class GeoIdProccesor implements OsmElementProccesor, OsmDatabaseCollector{

	private ElementsDatabase elementDatabase = new SimpleElementsDatabase();
	private Set<Long> lookingEntities;
	
	public ElementsDatabase getElementsDatabase() {
		return elementDatabase;
	}

	public GeoIdProccesor(Set<Long> lookingEntities) {
		this.lookingEntities = lookingEntities;
	}
	
	public void complete() {
	}

	public void proccesWay(Way way) {
		addEntity(way);
	}

	public void proccesRelation(Relation relation) {
		addEntity(relation);
	}

	public void proccesBound(Bound bound) {
		addEntity(bound);
	}

	public void proccesNode(Node node) {
		addEntity(node);
	}
	
	public void addEntity(Entity entity){
		if(lookingEntities.contains(entity.getId()) && entity.getType().equals(EntityType.Node)){
			if(entity.getId() == 2713355358l){
				System.out.println("Dodaje entity o id 2713355358 type: " + entity.getType() + " class: " + entity.getClass().getSimpleName() + " w bazie: " + elementDatabase.getEntity(2713355358l) + " w bazie2: " + elementDatabase.getEntity(entity.getId()));
			}
			elementDatabase.addEntity(entity);
			lookingEntities.remove(entity.getId());
		}
	}
}
