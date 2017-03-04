package pl.fluence.reader.filters;

public class XorFilter extends LogicFilter {

	public XorFilter(OsmElementFilter... filters) {
		super(filters);
	}

	@Override
	public boolean getResultCheck() {
		boolean firstTrue = false;
		boolean secoundTrue = false;
		for (OsmElementFilter filter : childrenFilters) {
			boolean result = filter.getResultCheck();
			if (result && !firstTrue){
				firstTrue = true;
			}else if(result && firstTrue){
				secoundTrue = true;
				break;
			}
		}
		if(firstTrue && !secoundTrue) {
			return true;
		}
		return false;
	}

}
