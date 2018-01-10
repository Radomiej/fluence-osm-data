package pl.fluence.reader.tag;

import org.openstreetmap.osmosis.core.domain.v0_6.Entity;
import org.openstreetmap.osmosis.core.domain.v0_6.Tag;

public class TagHelper {
	
	/**
	 * Contains key in entity tags
	 * @param key
	 * @param entity
	 * @return
	 */
	public static boolean isTag(String key, Entity entity) {
		for (Tag tag : entity.getTags()) {
			if (tag.getKey().equals(key)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isContainsTagSequence(String keySequnece, Entity entity) {
		for (Tag tag : entity.getTags()) {
			if (tag.getKey().contains(keySequnece)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isStartWith(String keySequnece, Entity entity) {
		for (Tag tag : entity.getTags()) {
			if (tag.getKey().startsWith(keySequnece)) {
				return true;
			}
		}
		return false;
	}

	public static boolean haveOneValue(String key, Entity entity, String... values) {
		for (Tag tag : entity.getTags()) {
			if (tag.getKey().equals(key)) {
				for (int x = 0; x < values.length; x++) {
					if (tag.getValue().equals(values[x])) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public static boolean containsOneValue(String key, Entity entity, String... values) {
		for (Tag tag : entity.getTags()) {
			if (tag.getKey().equals(key)) {
				for (int x = 0; x < values.length; x++) {
					if (tag.getValue().contains(values[x])) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public static String getValue(String key, Entity entity) {
		for (Tag tag : entity.getTags()) {
			if (tag.getKey().equals(key)) {
				return tag.getValue();
			}
		}
		return "";
	}
	
	public static String getValueWithDefault(String key, Entity entity, String defaultValue) {
		for (Tag tag : entity.getTags()) {
			if (tag.getKey().equals(key)) {
				return tag.getValue();
			}
		}
		return defaultValue;
	}
}
