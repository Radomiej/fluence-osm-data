package pl.fluence.reader.filters;

public class KeyFilter extends DefaultFilter {
	private String compareKey;
	private StringComparator keyComparator = StringComparator.EQUALS;

	public KeyFilter(String key) {
		this.compareKey = key;
	}

	@Override
	public void checkTag(String key, String value) {
		if (keyComparator.compareString(this.compareKey, key)) {
//			System.out.println("KeyFilter check: " + compareKey + "==" + key);
			result = true;
		}
	}

	public StringComparator getKeyComparator() {
		return keyComparator;
	}

	public void setKeyComparator(StringComparator keyComparator) {
		this.keyComparator = keyComparator;
	}
}
