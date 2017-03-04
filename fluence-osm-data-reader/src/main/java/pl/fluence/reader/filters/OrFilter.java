package pl.fluence.reader.filters;

public class OrFilter extends LogicFilter {

	public OrFilter(OsmElementFilter... filters) {
		super(filters);
	}

	@Override
	public boolean getResultCheck() {
		for (OsmElementFilter filter : childrenFilters) {
			boolean result = filter.getResultCheck();
			if (result){
				return true;
			}
		}
		return false;
	}

}
