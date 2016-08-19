package pl.fluence.reader.processors;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.openstreetmap.osmosis.core.domain.v0_6.EntityType;
import org.openstreetmap.osmosis.core.domain.v0_6.Relation;
import org.openstreetmap.osmosis.core.domain.v0_6.RelationMember;
import org.openstreetmap.osmosis.core.domain.v0_6.Way;

public class RelationSquareProccesor extends EmptyElementsProccessor {

	private final NodesSquareProccesor nodesSquareProccesor;
	private final WaysSquareProccesor waysSquareProccesor;
	
	private Map<Long, Relation> relationsMap = new HashMap<Long, Relation>(2500000);
	
	
	public RelationSquareProccesor(NodesSquareProccesor nodesSquareProccesor, WaysSquareProccesor waysSquareProccesor) {
		this.nodesSquareProccesor = nodesSquareProccesor;
		this.waysSquareProccesor = waysSquareProccesor;
	}
	
	@Override
	public void proccesRelation(Relation relation) {
		for(RelationMember relationMember : relation.getMembers()){
			if(relationMember.getMemberType() == EntityType.Node){
				if(nodesSquareProccesor.containsNode(relationMember.getMemberId())){
					relationsMap.put(relation.getId(), relation);
					return;
				}
			}else if(relationMember.getMemberType() == EntityType.Way){
				if(waysSquareProccesor.containsWay(relationMember.getMemberId())){
					relationsMap.put(relation.getId(), relation);
					return;
				}
			}
		}
	}

	public Collection<Relation> getRelations() {
		return relationsMap.values();
	}
}
