package pl.fluence.reader.filters;

public class KeyValuesFilter extends DefaultFilter {
	private String key;
	private String[] validValues;
	private StringComparator keyComparator = StringComparator.EQUALS;
	private StringComparator valuesComparator = StringComparator.EQUALS;

	public KeyValuesFilter(String key, String... validValues) {
		this.key = key;
		this.validValues = validValues;
	}

	@Override
	public void checkTag(String key, String value) {
		if (keyComparator.compareString(this.key, key)) {
			for (String compareValues : validValues) {
				if (valuesComparator.compareString(compareValues, value)){
					result = true;
				}
			}
		}
	}

	public StringComparator getValuesComparator() {
		return valuesComparator;
	}

	public void setValuesComparator(StringComparator valuesComparator) {
		this.valuesComparator = valuesComparator;
	}

	public StringComparator getKeyComparator() {
		return keyComparator;
	}

	public void setKeyComparator(StringComparator keyComparator) {
		this.keyComparator = keyComparator;
	}
}
