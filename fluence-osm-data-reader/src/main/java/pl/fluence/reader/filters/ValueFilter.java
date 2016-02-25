package pl.fluence.reader.filters;

public class ValueFilter extends DefaultFilter {
	private String value;
	private StringComparator valueComparator = StringComparator.EQUALS;

	public ValueFilter(String value) {
		this.value = value;
	}

	@Override
	public void checkTag(String key, String value) {
		if (valueComparator.compareString(this.value, value)) {
			result = true;
		}
	}

	public StringComparator getValueComparator() {
		return valueComparator;
	}

	public void setValueComparator(StringComparator valueComparator) {
		this.valueComparator = valueComparator;
	}
}
