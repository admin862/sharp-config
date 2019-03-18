package com.dafy.config.client;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dafy.config.client.constans.Constant;
import com.dafy.config.client.constans.RunModel;
import com.dafy.config.client.exception.SharpConfigException;
import com.dafy.config.client.impl.LocalReloadFactory;
import com.dafy.config.client.impl.RemoteHttpReloadFactory;
import com.dafy.config.client.impl.StateNotice;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * Created by yanhai on 2017/4/6.
 */
public class ConfigBuilder {

	private static final Logger LOG = LoggerFactory.getLogger(ConfigBuilder.class);
	/**
	 * 配置文件对应的本地缓存
	 */
	private Map<String, ConfigCache<?, ?, ?>> configMaps = Maps.newConcurrentMap();
	private static volatile ConfigBuilder configBuilder;
	private static final int TASK_TIMEOUT = 30;
	/**
	 * 加载数据线程池
	 */
	private ThreadPoolExecutor poolExecutor;
	/**
	 * 配置文件
	 */
	private Properties appProperties = new Properties();
	/**
	 * 使用模式
	 */
	private boolean onlineModel = false;
	private StateNotice stateNotice;
	/**
	 * zk通知管道
	 */
	private LinkedBlockingQueue<String> noticeQueue = new LinkedBlockingQueue<>(5);
	/**
	 *
	 */
	private volatile boolean running = false;

	public static final String CLOSE_MESSAGE = "{**close**}";
	private Thread noticeProcess = new Thread() {

		public void run() {
			while (true) {
				try {
					String content = ConfigBuilder.this.noticeQueue.take();
					if (content != null && content.equals(CLOSE_MESSAGE)) {
						LOG.info("Notice Thread Exit.");
						break;
					}
					ConfigBuilder.this.reload(content);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}

	};

	public interface Reload<K, V, T> extends DataLoad<T>, DataProcess<K, V, T>, DataLocal<T> {

	}

	public static class BaseReload<K, V, T> implements Reload<K, V, T> {

		@Override
		public T load(ConfigInfo configInfo) throws IOException {
			return null;
		}

		@Override
		public void dataProcess(SCache<K, V> cache, T content) {
		}

		@Override
		public void local(ConfigInfo configInfo, T content) {
		}

	}

	/**
	 * 是否是在线模式
	 *
	 * @return
	 */
	public boolean isOnlineModel() {
		return onlineModel;
	}

	/**
	 * @param properties
	 */
	private ConfigBuilder(String properties) {
		try {
			InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(properties);
			if (is == null) {
				is = ConfigBuilder.class.getResourceAsStream("sharp.config.properties");
			}
			this.appProperties.load(is);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			LOG.error("[sharp.config.properties] not Exist!");
			e.printStackTrace();
		}
		if (this.appProperties.containsKey("model")) {
			if (!this.appProperties.get("model").toString().toLowerCase().equals("online")) {
				LOG.info("Application Model: Local ");
			} else {
				onlineModel = true;
				LOG.info("Application Model: Online ");
			}
		} else {
			LOG.info("Application Model: Local ");
		}

		LOG.info("Local Resource Path:[{}]", appProperties.get(Constant.LOCAL_RESOURCE_PATH));

		if (this.onlineModel) {
			stateNotice = new StateNotice(this.appProperties, this.noticeQueue);
		}
		poolExecutor = new ThreadPoolExecutor(3, 3, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(5));
	}

	public static ConfigBuilder build() {
		if (configBuilder == null) {
			synchronized (ConfigBuilder.class) {
				if (configBuilder == null)
					configBuilder = new ConfigBuilder("sharp.config.properties");
			}
		}
		return configBuilder;
	}

	/**
	 * 绑定配置文件和处理过程，远程方式，本地方式使用默认
	 *
	 * @param configName
	 * @return
	 */
	public synchronized ConfigBuilder bind(String configName) {
		return this.bind(configName, RemoteHttpReloadFactory.build(appProperties),
				LocalReloadFactory.build(appProperties));
	}

	public synchronized <K, V> ConfigBuilder bind(String configName, DataProcess<K, V, String> dataProcess) {
		Preconditions.checkArgument(dataProcess != null, "Parameter [DataProcess] is NULL");

		ConfigBuilder.Reload<K, V, String> remoteReload = RemoteHttpReloadFactory.build(this.appProperties);
		((RemoteHttpReloadFactory<K, V>) remoteReload).setDataProcess(dataProcess);

		ConfigBuilder.Reload<K, V, String> localReload = LocalReloadFactory.build(this.appProperties);
		((LocalReloadFactory<K, V>) localReload).setDataProcess(dataProcess);

		ConfigCache<K, V, String> configCache = new ConfigCache<K, V, String>(remoteReload, localReload,
				this.onlineModel);
		configMaps.put(configName, configCache);
		return this;
	}

	/**
	 * 绑定配置文件和处理过程，远程方式及本地方式
	 *
	 * @param configName
	 *            配置文件名称
	 * @param remoteReload
	 *            远程加载
	 * @param localReload
	 *            本地处理
	 * @return
	 */
	public synchronized <K, V, T> ConfigBuilder bind(String configName, Reload<K, V, T> remoteReload,
			Reload<K, V, T> localReload) {
		Preconditions.checkArgument(remoteReload != null, "Parameter [RemoteReload] is NULL");
		Preconditions.checkArgument(localReload != null, "Parameter [LocalReload] is NULL");

		if (isOnlineModel()) {
			LOG.info("[{}] Online Model, Reload [{}]", configName, remoteReload);
		} else {
			LOG.info("[{}] Offline Model, Reload [{}]", configName, localReload);
		}
		ConfigCache<K, V, T> configCache = new ConfigCache<>(remoteReload, localReload, this.onlineModel);
		configMaps.put(configName, configCache);
		return this;
	}

	/**
	 * 远程加载、本地加载使用同一种方式
	 * 
	 * @param configName
	 * @param reload
	 * @return
	 */
	public synchronized <K, V, T> ConfigBuilder bind(String configName, Reload<K, V, T> reload) {
		Preconditions.checkArgument(reload != null, "Parameter [reload] is NULL");
		ConfigCache<K, V, T> configCache = new ConfigCache<>(reload, reload, this.onlineModel);
		configMaps.put(configName, configCache);
		return this;
	}

	/**
	 * 启动
	 * 
	 * TODO 抛出异常应该使用Runtime类型，此处会导致永远无法启动
	 * 
	 * @throws SharpConfigException
	 */
	public synchronized void start() throws SharpConfigException {
		if (running) {
			return;
		}
		if (isOnlineModel()) {
			String content = this.stateNotice.readData();
			this.init(content);
			//
			this.noticeProcess.start();
		} else {
			for (Map.Entry<String, ConfigCache<? extends Object, ? extends Object, ? extends Object>> entry : this.configMaps
					.entrySet()) {
				ConfigInfo ci = new ConfigInfo();
				ci.setConfigName(entry.getKey().toString());
				ci.setModel(RunModel.OFFLINE.toString());
				Callable<ConfigRet> callable = ((ConfigCache<? extends Object, ? extends Object, ? extends Object>) entry
						.getValue()).prepare(ci);
				Future<ConfigRet> future = this.poolExecutor.submit(callable);
				try {
					ConfigRet configRet = future.get();
					if (!configRet.isRet())
						throw new RuntimeException("Config File:" + ci.getConfigName() + " prepare failed.");
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
				entry.getValue().confirm();
			}
		}

		running = true;
	}

	public void close() {
		if (this.isOnlineModel()) {
			try {
				ConfigBuilder.this.noticeQueue.put(CLOSE_MESSAGE);
			} catch (InterruptedException e) {
				LOG.warn(e.getMessage());
			}

			this.stateNotice.close();
		}
	}

	/**
	 *
	 * @param configName
	 * @param key
	 * @return
	 */
	public int getInt(String configName, Object key) {
		Preconditions.checkArgument(configName != null && !"".equals(configName), "ConfigName is Empty.");
		Preconditions.checkArgument(this.configMaps.containsKey(configName), "ConfigName NOT Exist!" + configName);
		ConfigCache<?, ?, ?> cc = this.configMaps.get(configName);
		return cc.getInt(key);
	}

	public int getInt(String configName, Object key, int defualtValue) {
		Preconditions.checkArgument(configName != null && !"".equals(configName), "ConfigName is Empty.");
		Preconditions.checkArgument(this.configMaps.containsKey(configName), "ConfigName NOT Exist!" + configName);
		ConfigCache<?, ?, ?> cc = this.configMaps.get(configName);
		return cc.getInt(key, defualtValue);
	}

	public long getLong(String configName, Object key) {
		Preconditions.checkArgument(configName != null && !"".equals(configName), "ConfigName is Empty.");
		Preconditions.checkArgument(this.configMaps.containsKey(configName), "ConfigName NOT Exist!" + configName);
		return this.configMaps.get(configName).getLong(key);
	}

	public long getLong(String configName, Object key, long defaultValue) {
		Preconditions.checkArgument(configName != null && !"".equals(configName), "ConfigName is Empty.");
		Preconditions.checkArgument(this.configMaps.containsKey(configName), "ConfigName NOT Exist!" + configName);
		return this.configMaps.get(configName).getLong(key, defaultValue);
	}

	public String getString(String configName, Object key) {
		Preconditions.checkArgument(configName != null && !"".equals(configName), "ConfigName is Empty.");
		Preconditions.checkArgument(this.configMaps.containsKey(configName), "ConfigName NOT Exist!" + configName);
		return this.configMaps.get(configName).getString(key);
	}

	public String getString(String configName, Object key, String defaultValue) {
		Preconditions.checkArgument(configName != null && !"".equals(configName), "ConfigName is Empty.");
		Preconditions.checkArgument(this.configMaps.containsKey(configName), "ConfigName NOT Exist!" + configName);
		return this.configMaps.get(configName).getString(key, defaultValue);
	}

	public Object get(String configName, Object key) {
		Preconditions.checkArgument(configName != null && !"".equals(configName), "ConfigName is Empty.");
		Preconditions.checkArgument(this.configMaps.containsKey(configName), "ConfigName NOT Exist!" + configName);
		return this.configMaps.get(configName).get(key);
	}

	public Object get(String configName, Object key, Object defaultValue) {
		Preconditions.checkArgument(configName != null && !"".equals(configName), "ConfigName is Empty.");
		Preconditions.checkArgument(this.configMaps.containsKey(configName), "ConfigName NOT Exist!" + configName);
		return this.configMaps.get(configName).get(key, defaultValue);
	}

	public ConfigCache<? extends Object, ? extends Object, ? extends Object> on(String configName) {
		Preconditions.checkArgument(this.running, "Please invoke [start] frist !");
		Preconditions.checkArgument(configName != null && !"".equals(configName), "ConfigName is Empty.");
		Preconditions.checkArgument(configMaps.containsKey(configName), "ConfigName Not Exists!");
		return configMaps.get(configName);
	}

	public ConfigBuilder init(InputStream is) throws SharpConfigException {
		try {
			byte[] array = new byte[is.available()];
			is.read(array);
			return this.init(new String(array, "UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}

	/**
	 * 初始化
	 *
	 * @param content
	 * @throws SharpConfigException
	 */
	private ConfigBuilder init(String content) throws SharpConfigException {
		Preconditions.checkArgument(content != null && !"".equals(content), "Content is Empty.");
		if (LOG.isDebugEnabled())
			LOG.debug("JSON Data:" + content);
		JSONObject jsonObject = (JSONObject) JSONObject.parse(content);
		JSONObject preJSON = prepare(jsonObject, ConfigInfo.CURRENT);
		if (preJSON.containsKey(ConfigInfo.ERRORS)) {
			throw new SharpConfigException("Sharp Config Init Error: \n" + preJSON.toJSONString());
		}
		JSONObject curr = confirm();
		this.stateNotice.writeData(curr.toString());
		return this;
	}

	private JSONObject prepare(JSONObject jsonObject, String key) {
		Preconditions.checkArgument(jsonObject.containsKey(key), "Error Json Text: [next] not Exist!");
		List<ConfigInfo> _preInfos = getDate(jsonObject, key); // 需要更新的配置项
		List<ConfigInfo> updates = Lists.newArrayList();
		List<ConfigInfo> news = Lists.newArrayList();
		// 将更新项分为两个组
		for (ConfigInfo ci : _preInfos) {
			if (configMaps.containsKey(ci.getConfigName())) {
				if (needUpdate(ci, configMaps.get(ci.getConfigName()))) {
					updates.add(ci);
				} else {
					LOG.warn("Config {} version eq. {} No need to change", ci.getConfigName(), ci.getVersion());
				}
			} else {
				news.add(ci);
			}
		}

		if (!news.isEmpty()) {
			for (ConfigInfo ci : news) {
				ci.setText("New Config item Not supported!");
			}
			LOG.warn("New Config item Not supported ! {} ", news);
		}

		List<ConfigInfo> errors = Lists.newArrayList();
		for (ConfigInfo ci : updates) {
			ConfigCache<? extends Object, ? extends Object, ? extends Object> cc = configMaps.get(ci.getConfigName());
			Callable<ConfigRet> call = cc.prepare(ci);
			Future<ConfigRet> future = poolExecutor.submit(call);
			try {
				ConfigRet v = future.get(TASK_TIMEOUT, TimeUnit.SECONDS);
				if (!v.isRet()) {
					ci.setText(v.getRetText());
					errors.add(ci);
					// 当前不存在使用中的缓存数据
					// if (cc.isCurrentNULL()) {
					// // 远程加载失败，走本地模式
					// LOG.info("***** !!!! Remote Failed, Change local Model. "
					// + ci.getConfigName());
					// if (poolExecutor.submit(cc.prepare(ci)).get() != null) {
					// // cc.confirm();
					// LOG.info("***** !!!! Local Model Prepared. " +
					// ci.getConfigName());
					// }
					// ci.setModel(RunModel.OFFLINE.toString());
					// } else {
					// ci.setModel(RunModel.ONLINE.toString());
					// }
				}
				ci.setModel(RunModel.ONLINE.toString());

			} catch (Exception e) {
				ci.setText(e.getMessage());
				errors.add(ci);
				e.printStackTrace();
			}
		}

		if (!errors.isEmpty()) {
			updates.removeAll(errors);
			LOG.warn("Update failed ! {} ", errors);
		}
		jsonObject.put(key, JSONObject.toJSON(updates));
		if (!errors.isEmpty())
			jsonObject.put(ConfigInfo.ERRORS, JSONObject.toJSON(errors));
		if (!news.isEmpty())
			jsonObject.put(ConfigInfo.NEW, JSONObject.toJSON(news));
		return jsonObject;
	}

	private JSONObject confirm() {
		List<ConfigInfo> rets = Lists.newArrayList();
		List<ConfigInfo> errors = Lists.newArrayList();
		for (Map.Entry<String, ConfigCache<? extends Object, ? extends Object, ? extends Object>> entry : configMaps
				.entrySet()) {
			ConfigCache<? extends Object, ? extends Object, ? extends Object> cc = entry.getValue();
			cc.confirm();
			rets.add(cc.getCurrent());
		}
		JSONObject js1 = new JSONObject();
		js1.put(ConfigInfo.CURRENT, rets);
		js1.put(ConfigInfo.STATE, 2);
		if (!errors.isEmpty())
			js1.put(ConfigInfo.ERRORS, errors);
		return js1;
	}

	public void reload(InputStream is) {
		try {
			byte[] array = new byte[is.available()];
			is.read(array);
			this.reload(new String(array, "UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void stateChange(JSONObject jsonObject) {
		LOG.debug(jsonObject.toJSONString());
		stateNotice.writeData(jsonObject.toString());
	}

	private boolean needUpdate(ConfigInfo configInfo,
			ConfigCache<? extends Object, ? extends Object, ? extends Object> configCache) {
		ConfigInfo ci = configCache.currentConfigInfo();
		if (ci == null)
			return true; // 第一次，需要更新
		return !ci.getVersion().equals(configInfo.getVersion());
	}

	/**
	 * 接收父节点数据变化的通知
	 *
	 * @param content
	 */
	public void reload(String content) {
		Preconditions.checkArgument(content != null && !"".equals(content), "Content is Empty.");
		LOG.info("JSON Data:" + content);
		JSONObject jsonObject = (JSONObject) JSONObject.parse(content);
		if (jsonObject.containsKey("state")) {
			int state = Integer.parseInt(jsonObject.get("state").toString());
			switch (state) {
			case 0:// 正常状态 to normal
				LOG.info("Ignore this state [0]");
				break;
			case 1:// 接收到更新请求
				JSONObject ret = prepare(jsonObject, ConfigInfo.NEXT);
				this.stateChange(ret);
				break;
			case 2:// 确认
				JSONObject ret1 = confirm();
				this.stateChange(ret1);
				break;
			default: // 其它
				LOG.error("Error state: " + state);
			}
		} else {
			if (LOG.isDebugEnabled())
				LOG.debug("Ignore this:" + content);
		}

	}

	private List<ConfigInfo> getDate(JSONObject jsonObject, String name) {
		if (jsonObject.containsKey(name)) {
			JSONArray nodes = jsonObject.getJSONArray(name);
			if (!nodes.isEmpty()) {
				return JSONObject.parseArray(nodes.toJSONString(), ConfigInfo.class);
			}
		}
		return Lists.newArrayList();
	}

}
