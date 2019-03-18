package com.dafy.config.client;

import java.util.Map;

public interface SCache<K,V> {

	void put(K k, V v);
	void putAll(Map<? extends K, ? extends V> m);
	void cleanUp();
	V getIfPresent(Object key);
	long size();
	Map<K,V> asMap();
}
