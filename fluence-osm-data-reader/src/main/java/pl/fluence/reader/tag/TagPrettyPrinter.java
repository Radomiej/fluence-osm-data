package pl.fluence.reader.tag;

import java.util.Collection;

import org.openstreetmap.osmosis.core.domain.v0_6.Node;
import org.openstreetmap.osmosis.core.domain.v0_6.Relation;
import org.openstreetmap.osmosis.core.domain.v0_6.Tag;
import org.openstreetmap.osmosis.core.domain.v0_6.Way;

public class TagPrettyPrinter {
	public static String prettyPrintTagCollection(Collection<Tag> tags){
		String tagsText = "";
		for (Tag tag : tags) {
			tagsText += tag.getKey() + "=" + tag.getValue() + ",";
		}
		return tagsText;
	}

	public static String prettyPrintNodeTags(Node node) {
		return prettyPrintTagCollection(node.getTags());
	}

	public static String prettyPrintWayTags(Way way) {
		return prettyPrintTagCollection(way.getTags());
	}

	public static String prettyPrintWayTags(Relation relation) {
		return prettyPrintTagCollection(relation.getTags());
	}
}
