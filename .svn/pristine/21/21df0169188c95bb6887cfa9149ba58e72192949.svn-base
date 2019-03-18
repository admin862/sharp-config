package com.dafy.config.client.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dafy.config.client.ConfigBuilder;
import com.dafy.config.client.ConfigInfo;
import com.dafy.config.client.DataLocal;
import com.dafy.config.client.DataProcess;
import com.dafy.config.client.SCache;
import com.dafy.config.client.constans.Constant;
import com.dafy.config.client.utils.FileFinder;

/**
 * 从本地加载配置文件
 * <p>
 * Created by de on 2017/4/8.
 */
public final class LocalReloadFactory<K, V> implements ConfigBuilder.Reload<K, V, String> {

	private static final Logger LOG = LoggerFactory.getLogger(LocalReloadFactory.class);

	private String localResourcePath;
	private DataProcess<K, V, String> _dataProcess;
	private DataLocal<String> _dataLocal;

	public static <K, V> ConfigBuilder.Reload<K, V, String> build(Properties appProperties) {
		return new LocalReloadFactory<K, V>(appProperties);
	}

	public void setDataProcess(DataProcess<K, V, String> dataProcess) {
		this._dataProcess = dataProcess;
	}

	@Override
	public String load(ConfigInfo configInfo) throws IOException {
		FileFinder ff = new FileFinder(this.localResourcePath, configInfo.getConfigName());
		ff.walk();
		File currentFile = ff.lastFile();
		String ret;
		if (currentFile != null) {
			// throw new IOException("File [" + configInfo.getConfigName() + "]
			// not Exists !");
			LOG.info("Current File: {}", currentFile.getAbsoluteFile());
			String filename = currentFile.getName();
			String[] p = filename.split("\\$");
			if (LOG.isDebugEnabled()) {
				LOG.debug("Parser File Name:" + Arrays.toString(p));
			}
			if (p.length == 3) {
				configInfo.setVersion(p[1]);
				configInfo.setTimestamp(Long.parseLong(p[2]));
			} else {
				LOG.error("Cannot parser filename:" + filename);
			}

			try (FileInputStream ins = new FileInputStream(currentFile)) {
				byte[] array = new byte[ins.available()];
				ins.read(array);
				ret = new String(array, "UTF-8");
			}
			return ret;
		} else {
			LOG.info("Search classpath");
			InputStream is = null;
			is = Thread.currentThread().getContextClassLoader().getResourceAsStream(configInfo.getConfigName());
			if (is == null) {
				is = ConfigBuilder.class.getResourceAsStream(configInfo.getConfigName());
			}
			if (is == null) {
				throw new FileNotFoundException("[" + configInfo.getConfigName() + "] does not exits in classpath and ["
						+ this.localResourcePath + "]");
			}
			try {
				configInfo.setVersion(String.valueOf(Integer.MIN_VALUE));
				configInfo.setTimestamp(System.currentTimeMillis());
				byte[] array = new byte[is.available()];
				is.read(array);
				ret = new String(array, "UTF-8");
			} finally {
				is.close();
			}
			return ret;
		}

	}

	public LocalReloadFactory(Properties appProperties) {
		this.localResourcePath = appProperties.getProperty(Constant.LOCAL_RESOURCE_PATH) + File.separator
				+ appProperties.getProperty(Constant.ENV_NAME) + File.separator
				+ appProperties.getProperty(Constant.GROUP_NAME) + File.separator
				+ appProperties.getProperty(Constant.APP_NAME);
		this._dataProcess = DefaultDataProcessor.getDefaultDataProcessor();
		this._dataLocal = new DataLocalFactory(appProperties);
	}

	@Override
	public void dataProcess(SCache<K, V> cache, String content) {
		_dataProcess.dataProcess(cache, content);
	}

	@Override
	public void local(ConfigInfo configInfo, String content) {
		this._dataLocal.local(configInfo, content);
	}

}
