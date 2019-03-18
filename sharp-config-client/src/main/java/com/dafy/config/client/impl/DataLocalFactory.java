package com.dafy.config.client.impl;

import com.dafy.config.client.ConfigInfo;
import com.dafy.config.client.DataLocal;
import com.dafy.config.client.constans.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 默认的保存到本地文件
 * 
 * Created by de on 2017/4/14.
 */
public final class DataLocalFactory implements DataLocal<String> {
	private static final Logger LOG = LoggerFactory.getLogger(DataLocalFactory.class);
	private String path;

	public DataLocalFactory(Properties properties) {
		this.path = properties.getProperty(Constant.LOCAL_RESOURCE_PATH) + File.separator
				+ properties.getProperty(Constant.ENV_NAME) + File.separator
				+ properties.getProperty(Constant.GROUP_NAME) + File.separator
				+ properties.getProperty(Constant.APP_NAME);
		File dir = new File(this.path);
		if (!dir.exists()) {
			dir.mkdirs();
			LOG.info("Create DIR:" + dir.getAbsolutePath());
		}
		LOG.info("Local File Path:[" + dir.getAbsolutePath() + "]");
	}

	@Override
	public void local(ConfigInfo configInfo, String content) {
		String version = configInfo.getVersion() == null ? "" : configInfo.getVersion();
		String filename = configInfo.getConfigName() + "$" + version + "$" + configInfo.getTimestamp();
		File localFile = new File(this.path + File.separator + filename);
		try (FileOutputStream outputStream = new FileOutputStream(localFile)) {
			outputStream.write(content.getBytes("UTF-8"));
			LOG.info("Save Local File [{}] ", localFile.getAbsoluteFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
