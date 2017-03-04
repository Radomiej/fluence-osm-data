package pl.fluence.reader.processors;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openstreetmap.osmosis.core.domain.v0_6.Bound;
import org.openstreetmap.osmosis.core.domain.v0_6.Node;
import org.openstreetmap.osmosis.core.domain.v0_6.Relation;
import org.openstreetmap.osmosis.core.domain.v0_6.Tag;
import org.openstreetmap.osmosis.core.domain.v0_6.Way;

public class KeysElementProccesor implements OsmElementProccesor {

	private Set<Node> validNodes = new HashSet<Node>();
	private Set<Way> validWays = new HashSet<Way>();
	private Set<Relation> validRelations = new HashSet<Relation>();

	private boolean checkNode = true;
	private boolean checkWay = true;
	private boolean checkRelation = true;

	private boolean containsChecker = true;
	/**
	 * true - All tags must be in Entity. False - otherwise
	 */
	private boolean allChecker = false;

	private List<String> validKeys = new ArrayList<String>();
	private List<String> breakKeys = new ArrayList<String>();

	public KeysElementProccesor() {
	}

	public KeysElementProccesor(boolean checkNode, boolean checkWay, boolean checkRelation) {
		this.checkNode = checkNode;
		this.checkWay = checkWay;
		this.checkRelation = checkRelation;
	}

	public void complete() {

	}

	public void proccesWay(Way way) {
		if (!checkWay)
			return;
		boolean add = false;
		Set<String> needTags = new HashSet<String>(validKeys);
		for (Tag tag : way.getTags()) {
			if (isBreak(tag)) {
				add = false;
				break;
			}
			if (isValid(tag)) {
				needTags.remove(tag.getKey());
				add = true;
			}
		}
		if (add) {
			if (allChecker && needTags.isEmpty()) {
				validWays.add(way);
			}else if(!allChecker){
				validWays.add(way);
			}
		}
	}

	public void proccesRelation(Relation relation) {
		if (!checkRelation)
			return;

		boolean add = false;
		Set<String> needTags = new HashSet<String>(validKeys);
		for (Tag tag : relation.getTags()) {
			if (isBreak(tag)) {
				add = false;
				break;
			}
			if (isValid(tag)) {
				needTags.remove(tag.getKey());
				add = true;
			}
		}
		if (add) {
			if (allChecker && needTags.isEmpty()) {
				validRelations.add(relation);
			}else if(!allChecker){
				validRelations.add(relation);
			}
		}
	}

	public void proccesBound(Bound bound) {
	}

	public void proccesNode(Node node) {
		if (!checkNode)
			return;

		boolean add = false;
		Set<String> needTags = new HashSet<String>(validKeys);
		for (Tag tag : node.getTags()) {
			if (isBreak(tag)) {
				add = false;
				break;
			}
			if (isValid(tag)) {
				needTags.remove(tag.getKey());
				add = true;
			}
		}
		if (add) {
			if (allChecker && needTags.isEmpty()) {
				validNodes.add(node);
			}else if(!allChecker){
				validNodes.add(node);
			}
		}
	}

	private boolean isValid(Tag tag) {
		for (String checkValid : validKeys) {
			if (compareString(tag.getKey(), checkValid)) {
				return true;
			}
		}
		return false;
	}

	private boolean isBreak(Tag tag) {
		for (String checkValid : breakKeys) {
			if (compareString(tag.getKey(), checkValid)) {
				return true;
			}
		}
		return false;
	}

	private boolean compareString(String key, String checkValid) {
		// System.out.println("compare: " + key + " " + checkValid);
		if (containsChecker) {
			return key.contains(checkValid);
		}
		return key.equalsIgnoreCase(checkValid);
	}

	public void addValidKey(String validKey) {
		validKeys.add(validKey);
	}

	public void addInvalidKey(String invalidKey) {
		breakKeys.add(invalidKey);
	}

	public boolean isContainsChecker() {
		return containsChecker;
	}

	public void setContainsChecker(boolean containsChecker) {
		this.containsChecker = containsChecker;
	}

	public boolean isAllChecker() {
		return allChecker;
	}

	/**
	 * true - All tags must be in Entity. False - only one is needed
	 */
	public void setAllChecker(boolean allChecker) {
		this.allChecker = allChecker;
	}

	public Set<Node> getValidNodes() {
		return validNodes;
	}

	public Set<Way> getValidWays() {
		return validWays;
	}

	public Set<Relation> getValidRelations() {
		return validRelations;
	}
}
