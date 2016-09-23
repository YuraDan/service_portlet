package ru.gradis.sovzond.util;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by donchenko-y on 20.09.16.
 */
public class ParamMap {

	private final Map<String, Map<Class<?>, Object>> internalMap;

	public ParamMap() {
		internalMap = new HashMap<>();
	}

	public <T> void put(String textKey, Class<T> typeKey, T chachedValue) {
		Map<Class<?>, Object> mapForTextKey = getMapForTextKey(textKey);
		mapForTextKey.put(typeKey, chachedValue);
	}

	public void putLong(String textKey, long value) {
		put(textKey, Long.class, value);
	}

	public void putString(String textKey, String value) {
		put(textKey, String.class, value);
	}

	public void clear() {
		internalMap.clear();
	}

	private Map<Class<?>, Object> getMapForTextKey(String textKey) {
		if (!internalMap.containsKey(textKey))
			internalMap.put(textKey, new HashMap<>());
		return internalMap.get(textKey);
	}

	public <T> T get(String textKey, Class<T> typeKey) {
		Object untypedCachedValue = getUntyped(textKey, typeKey);
		return typeKey.cast(untypedCachedValue);
	}

	public String getString(String textKey) {
		return get(textKey, String.class);
	}

	public long getLong(String textKey) {
		return get(textKey, Long.class);
	}

	@Nullable
	private Object getUntyped(String textKey, Class<?> typeKey) {
		if (internalMap.containsKey(textKey))
			return internalMap.get(textKey).get(typeKey);
		else
			return null;
	}

}
