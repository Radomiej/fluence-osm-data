package pl.fluence.reader.filters;

public enum StringComparator {
	EQUALS{
		@Override
		public boolean compareString(String comparatorString, String valueToCheck) {
			return comparatorString.equals(valueToCheck);
		}
		
	},
	EQUALS_IGNORE_CASE{
		@Override
		public boolean compareString(String comparatorString, String valueToCheck) {
			return comparatorString.equalsIgnoreCase(valueToCheck);
		}
		
	},
	CONTAINS{
		@Override
		public boolean compareString(String comparatorString, String valueToCheck) {
			return valueToCheck.contains(comparatorString);
		}
		
	},
	START{
		@Override
		public boolean compareString(String comparatorString, String valueToCheck) {
			return valueToCheck.startsWith(comparatorString);
		}
		
	},
	END{
		@Override
		public boolean compareString(String comparatorString, String valueToCheck) {
			return valueToCheck.endsWith(comparatorString);
		}
		
	};
	
	public abstract boolean compareString(String comparatorString, String valueToCheck);
}
