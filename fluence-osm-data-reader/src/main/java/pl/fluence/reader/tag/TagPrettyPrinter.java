package pl.fluence.reader.tag;

import java.util.Collection;

import org.openstreetmap.osmosis.core.domain.v0_6.Tag;

public class TagPrettyPrinter {
	public static String prettyPrintTagCollection(Collection<Tag> tags){
		String tagsText = "";
		for (Tag tag : tags) {
			tagsText += tag.getKey() + "=" + tag.getValue() + ",";
		}
		return tagsText;
	}
}
