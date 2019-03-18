package com.dafy.config.client.impl;

import java.net.*;
import java.util.*;
import java.util.concurrent.BlockingQueue;

import com.google.common.collect.Lists;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dafy.config.client.constans.Constant;

/**
 * Created by de on 2017/4/8.
 */
public class StateNotice implements IZkDataListener {

    private static final Logger LOG = LoggerFactory.getLogger(StateNotice.class);
    private ZkClient zkClient;
    private String groupName;
    private String appName;
    private String zkWatchPath;
    private String selfNodePath;
    private BlockingQueue<String> noticeQueue;

    public StateNotice(Properties properties, BlockingQueue<String> noticeQueue) {
        this.noticeQueue = noticeQueue;
        String zkHost = properties.getProperty(Constant.ZK_HOST);
        int sessionTimeout = Integer.parseInt(properties.getProperty("zk.session.timeout"));
        int connectionTimeout = Integer.parseInt(properties.getProperty("zk.connection.timeout"));
        String zkPath = String.format("/config/%s", properties.getProperty(Constant.ENV_NAME));
        this.groupName = properties.getProperty(Constant.GROUP_NAME);
        this.appName = properties.getProperty(Constant.APP_NAME);
        LOG.info("Application GroupName[{}] AppName[{}] ", this.groupName, this.appName);
        init(zkHost, sessionTimeout, connectionTimeout, zkPath);
    }

    private void init(String zkHost, int sessionTimeout, int connectionTimeout, String zkPath) {
        String localAddr = currentIpAdd();
        zkClient = new ZkClient(zkHost, sessionTimeout, connectionTimeout);
        this.zkWatchPath = String.format("%s/%s/%s", zkPath, this.groupName, this.appName);
        if (LOG.isDebugEnabled()) {
            LOG.debug("ZK Watch Path:" + this.zkWatchPath);
        }
        this.selfNodePath = this.zkWatchPath + "/" + localAddr + "-" + UUID.randomUUID().toString();
        LOG.info("Create ephemeral node:[{}] .", selfNodePath);
        zkClient.createEphemeral(selfNodePath, Calendar.getInstance().getTime().getTime());
        zkClient.subscribeDataChanges(this.zkWatchPath, this);
    }

    public void close() {
        zkClient.close();
    }

    public String readData() {
        String content = zkClient.readData(this.zkWatchPath);
        LOG.info("Content: {}", content);
        return content;
    }

    public void writeData(String content) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Content -> ZK: " + content);
        }
        zkClient.writeData(selfNodePath, content);
    }

    @Override
    public void handleDataChange(String dataPath, Object data) throws Exception {
        if (LOG.isDebugEnabled())
            LOG.debug("ZK data change: {} ,{} ", dataPath, data);

        this.noticeQueue.put(data.toString());
    }

    @Override
    public void handleDataDeleted(String dataPath) throws Exception {
        // ignore
    }

    public String currentIpAdd() {
        List<String> ips = Lists.newArrayList();
        Enumeration allNetInterfaces;
        try {
            allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                Enumeration addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    ip = (InetAddress) addresses.nextElement();
                    if (ip != null && ip instanceof Inet4Address) {
                        ips.add(ip.getHostAddress());
                    }
                }
            }
            if (ips.isEmpty()) {
                return "127.0.0.1";
            }
            if (ips.size() == 1) {
                return ips.get(0);
            }
            Collections.sort(ips);
            return ips.get(ips.size() - 1);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "127.0.0.1";
    }
}
