package pl.fluence.reader.filters;

import java.util.Arrays;
import java.util.List;

import javax.naming.spi.DirStateFactory.Result;

import org.openstreetmap.osmosis.core.domain.v0_6.Entity;

public abstract class LogicFilter implements OsmElementFilter{

	protected List<OsmElementFilter> childrenFilters;
	protected Entity currentEntity = null;
	
	public LogicFilter(OsmElementFilter... filters) {
		childrenFilters = Arrays.asList(filters);
	}
	
	public void startElement(Entity element) {
		currentEntity = element;
		for(OsmElementFilter filter : childrenFilters){
			filter.startElement(element);
		}
	}

	public void checkTag(String key, String value) {
		for(OsmElementFilter filter : childrenFilters){
			filter.checkTag(key, value);
		}
	}

	public abstract boolean getResultCheck();

	public void stopElement() {
		for(OsmElementFilter filter : childrenFilters){
			filter.stopElement();
		}
	}

	

}
