package pl.fluence.collector.services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.openstreetmap.osmosis.core.domain.v0_6.Entity;
import org.openstreetmap.osmosis.core.domain.v0_6.Relation;
import org.openstreetmap.osmosis.core.domain.v0_6.RelationMember;
import org.openstreetmap.osmosis.core.domain.v0_6.Way;

import com.vividsolutions.jts.geom.MultiPolygon;

public class RelationLink {
	private Relation relation;
	private Map<RelationMember, Entity> relationMembers = new HashMap<RelationMember, Entity>();
	private Set<RelationMember> nullMembers = new LinkedHashSet<RelationMember>();
	private MultiPolygon multiPolygon;
	private boolean created;
	
	public RelationLink(Relation relation) {
		this.relation = relation;
	}

	public Relation getRelation() {
		return relation;
	}

	public void setRelation(Relation relation) {
		this.relation = relation;
	}

	public Map<RelationMember, Entity> getRelationMembers() {
		return relationMembers;
	}

	public void addMemeber(RelationMember relationMember, Entity entity) {
		relationMembers.put(relationMember, entity);
	}

	public Set<RelationMember> getNullMembers() {
		return nullMembers;
	}

	public void addNullMember(RelationMember relationMember) {
		nullMembers.add(relationMember);
	}

	public MultiPolygon getMultiPolygon() {
		return multiPolygon;
	}

	public void setMultiPolygon(MultiPolygon multiPolygon) {
		this.multiPolygon = multiPolygon;
	}

	public boolean isCreated() {
		return created;
	}

	public void setCreated(boolean created) {
		this.created = created;
	}
}
