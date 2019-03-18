package com.dafy.config.client.impl;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dafy.config.client.ConfigBuilder;
import com.dafy.config.client.ConfigInfo;
import com.dafy.config.client.DataLocal;
import com.dafy.config.client.DataProcess;
import com.dafy.config.client.SCache;
import com.dafy.config.client.constans.Constant;

/**
 * 
 * 默认远程properties文件加载
 * 
 * Created by de on 2017/4/7.
 */
public final class RemoteHttpReloadFactory<K, V> implements ConfigBuilder.Reload<K, V, String> {

	private static final Logger LOG = LoggerFactory.getLogger(RemoteHttpReloadFactory.class);
	private static final int MAX_TIME_OUT = 2000;
	/**
	 * 重试次数
	 */
	private static final int RETRY_COUNT = 3;

	private static final String _LINE_ = "******************************************************";

	protected String url;
	protected String groupName;
	protected String appName;
	protected DataLocal<String> _dataLocal;
	protected DataProcess<K, V, String> _dataProcess;

	public static <K, V> ConfigBuilder.Reload<K, V, String> build(Properties appProperties) {
		return new RemoteHttpReloadFactory<K, V>(appProperties);
	}

	public void setDataLocal(DataLocal<String> dataLocal) {
		this._dataLocal = dataLocal;
	}

	public void setDataProcess(DataProcess<K, V, String> dataProcess) {
		this._dataProcess = dataProcess;
	}

	public RemoteHttpReloadFactory(Properties appProperties) {
		this.url = appProperties.getProperty(Constant.REMOTE_URL);
		if (this.url.endsWith("/")) {
			this.url = this.url.replaceAll("/$", "");
		}
		this.url = String.format("%s/config/%s/%s/%s", this.url, appProperties.getProperty(Constant.ENV_NAME),
				appProperties.getProperty(Constant.GROUP_NAME), appProperties.getProperty(Constant.APP_NAME));
		this.groupName = appProperties.getProperty(Constant.GROUP_NAME);
		this.appName = appProperties.getProperty(Constant.APP_NAME);
		this._dataLocal = new DataLocalFactory(appProperties);
		this._dataProcess = DefaultDataProcessor.getDefaultDataProcessor();
	}

	private String rpc(ConfigInfo configInfo) throws ClientProtocolException, IOException {
		long startTime = System.currentTimeMillis();
		String strUrl = String.format("%s/%s/%s", this.url, configInfo.getConfigName(), configInfo.getVersion());
		String strBody = "";
		String strReturn = "";
		RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(MAX_TIME_OUT)// 设置从连接池获取连接实例的超时
				.setConnectTimeout(MAX_TIME_OUT)// 设置连接超时
				.setSocketTimeout(MAX_TIME_OUT)// 设置读取超时
				.build();
		HttpClient httpClient = HttpClients.custom()
				// .setConnectionManager(connManager)
				// .setRetryHandler(httpRequestRetry())
				.setDefaultRequestConfig(requestConfig).build();
		HttpResponse response = null;
		HttpGet httpGet = new HttpGet(strUrl);
		httpGet.setHeader("Content-type", "application/x-www-form-urlencoded");
		response = httpClient.execute(httpGet);
		int reqStatus = response.getStatusLine().getStatusCode();
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			strReturn = EntityUtils.toString(entity, "UTF-8");
		}
		long end = System.currentTimeMillis();
		if (reqStatus == 200) {
			StringBuffer sb = new StringBuffer();
			sb.append("\n");
			sb.append(_LINE_).append("\n");
			sb.append("GET:" + strUrl).append("\n");
			sb.append(strReturn).append("\n");
			sb.append("** Time:" + (end - startTime) + " **").append("\n");
			sb.append(_LINE_);
			LOG.info(sb.toString());
		} else {
			StringBuffer sb = new StringBuffer();
			sb.append("\n");
			sb.append(_LINE_).append("\n");
			sb.append("GET:" + strUrl).append("\n");
			sb.append(strUrl).append("\n");
			sb.append(strBody).append("\n");
			sb.append(strReturn).append("\n");
			sb.append("** Time:" + (end - startTime) + " **").append("\n");
			sb.append(_LINE_);
			LOG.error(sb.toString());
			strReturn = "";
		}
		return strReturn;
	}

	/**
	 *
	 * @param configInfo
	 * @return
	 * @throws IOException
	 */
	@Override
	public String load(ConfigInfo configInfo) throws IOException {
		int nr = RETRY_COUNT;
		String result = "";
		Throwable tmp = null;
		while (nr > 0) {
			try {
				result = rpc(configInfo);
				break;
			} catch (IOException e) {
				tmp = e;
				LOG.warn("RemoteFailed:" + e.getMessage() + ", Retry [" + nr + "]");
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e1) {
				}
			}
			nr--;
		}
		if (nr == 0)
			throw new IOException("Remote Call Failed.", tmp);
		return result;
	}

	@Override
	public void local(ConfigInfo configInfo, String content) {
		this._dataLocal.local(configInfo, content);
	}

	@Override
	public void dataProcess(SCache<K, V> cache, String content) {
		this._dataProcess.dataProcess(cache, content.toString());
	}

}
