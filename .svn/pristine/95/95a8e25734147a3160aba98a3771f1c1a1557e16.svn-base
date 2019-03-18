package com.dafy.config.client.impl;

import java.util.Map;

import com.dafy.config.client.SCache;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class GuavaCacheImpl<K, V> implements SCache<K, V> {

	public static <K, V> SCache<K, V> build() {
		return new GuavaCacheImpl<>();
	}

	private GuavaCacheImpl() {
		this._cache = CacheBuilder.newBuilder().concurrencyLevel(5).initialCapacity(512).build();
	}

	private Cache<K, V> _cache;
	
	
	@Override
	public void put(K k, V v) {
		this._cache.put(k, v);
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		this._cache.putAll(m);
	}

	@Override
	public void cleanUp() {
		this._cache.cleanUp();
	}

	@Override
	public V getIfPresent(Object k) {
		return this._cache.getIfPresent(k);
	}

	@Override
	public long size() {
		return this._cache.size();
	}

	@Override
	public Map<K, V> asMap() {
		return this._cache.asMap();
	}

}
