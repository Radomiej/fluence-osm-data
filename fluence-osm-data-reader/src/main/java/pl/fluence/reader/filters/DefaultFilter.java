package pl.fluence.reader.filters;

import org.openstreetmap.osmosis.core.domain.v0_6.Entity;

public abstract class DefaultFilter implements OsmElementFilter {

	protected Entity current;
	protected boolean result = false;
	private boolean negation = false;
	
	public void startElement(Entity element) {
		current = element;
	}

	public abstract void checkTag(String key, String value);

	public boolean getResultCheck(){
		return negation ? !result : result;
	};

	public void stopElement() {
		result = false;
		current = null;
	}

	public boolean isNegation() {
		return negation;
	}

	public void setNegation(boolean negation) {
		this.negation = negation;
	}

}
