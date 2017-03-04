package pl.fluence.reader.processors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.openstreetmap.osmosis.core.domain.v0_6.Bound;

public class BoundProccesor extends EmptyElementsProccessor {
	private List<Bound> bounds = new ArrayList<Bound>();
	
	@Override
	public void proccesBound(Bound bound) {
		bounds.add(bound);
	}
	
	public Collection<Bound> getBounds(){
		return new ArrayList<Bound>(bounds);
	}
}
