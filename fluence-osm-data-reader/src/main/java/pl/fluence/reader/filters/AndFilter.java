package pl.fluence.reader.filters;

import pl.fluence.reader.tag.TagPrettyPrinter;

public class AndFilter extends LogicFilter {

	public AndFilter(OsmElementFilter... filters) {
		super(filters);
	}

	@Override
	public boolean getResultCheck() {
		
		for (OsmElementFilter filter : childrenFilters) {
			boolean result = filter.getResultCheck();
			if (!result){
				return false;
			}
		}
		return true;
	}

}
