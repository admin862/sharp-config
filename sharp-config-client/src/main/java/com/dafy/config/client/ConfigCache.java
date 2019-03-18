package com.dafy.config.client;

import java.util.concurrent.Callable;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dafy.config.client.impl.GuavaCacheImpl;

/**
 * Created by de on 2017/4/6.
 */
public class ConfigCache<K, V, T> {
	private static final Logger LOG = LoggerFactory.getLogger(ConfigCache.class);
	private SCache<K, V> cache;
	private SCache<K, V> tmpCache;
	final private ConfigBuilder.Reload<K, V, T> remoteReload;
	final private ConfigBuilder.Reload<K, V, T> localReload;
	private ReadWriteLock rwLock = new ReentrantReadWriteLock();

	private ConfigInfo current;
	private ConfigInfo next;
	private T tmpContent;
	private boolean onLineModel = false;

	/**
	 * 当前是否有缓存数据
	 * 
	 * @return
	 */
	public boolean isCurrentNULL() {
		return current == null;
	}

	public ConfigInfo currentConfigInfo() {
		return current;
	}

	private SCache<K, V> buildCache() {
		return GuavaCacheImpl.build();
	}

	public ConfigInfo getCurrent() {
		return this.current == null ? new ConfigInfo() : this.current;
	}

	ConfigCache(ConfigBuilder.Reload<K, V, T> reload0, ConfigBuilder.Reload<K, V, T> reload1, boolean onLineModel) {
		SCache<K, V> cache = buildCache();
		this.cache = cache;
		this.remoteReload = reload0;
		this.localReload = reload1;
		this.onLineModel = onLineModel;
	}

	/**
	 * @param info
	 * @return
	 */
	public Callable<ConfigRet> prepare(final ConfigInfo info) {
		this.next = info;
		return new Callable<ConfigRet>() {
			@Override
			public ConfigRet call() throws Exception {
				try {
					T content;
					if (ConfigCache.this.onLineModel)
						content = ConfigCache.this.remoteReload.load(info);
					else
						content = ConfigCache.this.localReload.load(info);

					// 临时存储
					tmpContent = content;
					LOG.info("Config:[{}] Content:[{}]", info.getConfigName(), content);
					tmpCache = buildCache();
					if (ConfigCache.this.onLineModel) {
						ConfigCache.this.remoteReload.dataProcess(tmpCache, content);
						// ConfigCache.this.remoteReload.local(info, content);
					} else {
						ConfigCache.this.localReload.dataProcess(tmpCache, content);
					}
				} catch (Exception e) {
					e.printStackTrace();
					return new ConfigRet(false, e.getMessage());
				}
				return new ConfigRet(true, "OK");
			}
		};
	}

	/**
	 * @return
	 */
	public boolean confirm() {
		if (this.tmpCache != null && this.tmpCache.size() > 0) {
			rwLock.writeLock().lock();
			try {
				LOG.info("-----Before Replace--------------------------------\n\t {} ",this.cache.asMap().toString());
				this.cache.cleanUp();
				this.cache.putAll(this.tmpCache.asMap());
				this.tmpCache = null;
				if (this.onLineModel) { // 仅当线上模式时会触发本地存储动作
					// if (this.current == null) {// 第一次启动
					// this.localReload.local(this.next, this.tmpContent);
					// } else {
					// this.localReload.local(this.current, this.tmpContent);
					// }
					this.localReload.local(this.next, this.tmpContent);
				}
				LOG.info("-----After Replace--------------------------------\n\t {} ",this.cache.asMap().toString());
				this.tmpContent = null;
				this.current = this.next;
				this.next = null;
				return true;
			} finally {
				rwLock.writeLock().unlock();
			}
		} else {
			LOG.info("Confirm Ignore " + (this.current == null ? "" : this.current.getConfigName())
					+ ", nothing change");
		}
		return false;
	}

	public String getString(Object key) {
		rwLock.readLock().lock();
		try {
			Object v = (Object) cache.getIfPresent(key);
			return v == null ? null : v.toString();
		} finally {
			rwLock.readLock().unlock();
		}
	}

	public String getString(Object key, String defaultValue) {
		String v = this.getString(key);
		return v == null ? defaultValue : v;
	}

	public int getInt(Object key) {
		rwLock.readLock().lock();
		try {
			Object v = cache.getIfPresent(key);
			if (v == null) {
				throw new RuntimeException("Cannot get [" + key + "]. Null Value");
			}
			return Integer.parseInt(v.toString());
		} finally {
			rwLock.readLock().unlock();
		}
	}

	public Integer getInt(Object key, int defaultValue) {
		rwLock.readLock().lock();
		try {
			Object v = cache.getIfPresent(key);
			if (v == null) {
				return defaultValue;
			} else
				return Integer.parseInt(v.toString());
		} finally {
			rwLock.readLock().unlock();
		}
	}

	public long getLong(Object key) {
		rwLock.readLock().lock();
		try {
			Object v = cache.getIfPresent(key);
			if (v == null) {
				throw new RuntimeException("Cannot get [" + key + "]. Null Value");
			}
			return Long.parseLong(v.toString());
		} finally {
			rwLock.readLock().unlock();
		}
	}

	public long getLong(Object key, long defaultValue) {
		rwLock.readLock().lock();
		try {
			Object v = cache.getIfPresent(key);
			if (v == null) {
				return defaultValue;
			} else
				return Long.parseLong(v.toString());
		} finally {
			rwLock.readLock().unlock();
		}
	}

	public Object get(Object key) {
		rwLock.readLock().lock();
		try {
			return cache.getIfPresent(key);
		} finally {
			rwLock.readLock().unlock();
		}
	}

	public Object get(Object key, Object defaultValue) {
		Object v = get(key);
		return v == null ? defaultValue : v;
	}

	public String[] getStrArray(String key, String split) {
		rwLock.readLock().lock();
		try {
			String val = cache.getIfPresent(key).toString();
			return val.split(split);
		} finally {
			rwLock.readLock().unlock();
		}
	}
}
