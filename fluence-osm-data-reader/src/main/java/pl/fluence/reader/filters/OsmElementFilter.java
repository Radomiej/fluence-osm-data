package pl.fluence.reader.filters;

import org.openstreetmap.osmosis.core.domain.v0_6.Entity;

public interface OsmElementFilter {
	public void startElement(Entity element);
	public void checkTag(String key, String value);
	public boolean getResultCheck();
	public void stopElement();
}
