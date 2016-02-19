package pl.fluence.reader.models;

public class ElementModel {
	private long geoId;
	private int version;
	private String tagKeys;
	private String tagValues;

	@Override
	public String toString() {
		return "ElementModel [geoId=" + geoId + ", version=" + version + ", tagKeys=" + tagKeys + ", tagValues="
				+ tagValues + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (geoId ^ (geoId >>> 32));
		result = prime * result + ((tagKeys == null) ? 0 : tagKeys.hashCode());
		result = prime * result + ((tagValues == null) ? 0 : tagValues.hashCode());
		result = prime * result + version;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ElementModel other = (ElementModel) obj;
		if (geoId != other.geoId)
			return false;
		if (tagKeys == null) {
			if (other.tagKeys != null)
				return false;
		} else if (!tagKeys.equals(other.tagKeys))
			return false;
		if (tagValues == null) {
			if (other.tagValues != null)
				return false;
		} else if (!tagValues.equals(other.tagValues))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

	public long getGeoId() {
		return geoId;
	}

	public void setGeoId(long geoId) {
		this.geoId = geoId;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getTagKeys() {
		return tagKeys;
	}

	public void setTagKeys(String tagKeys) {
		this.tagKeys = tagKeys;
	}

	public String getTagValues() {
		return tagValues;
	}

	public void setTagValues(String tagValues) {
		this.tagValues = tagValues;
	}
}
