package com.dafy.config.test;

import com.alibaba.fastjson.JSONObject;
import com.dafy.config.client.ConfigBuilder;
import com.dafy.config.client.ConfigInfo;
import com.dafy.config.client.DataLocal;
import com.dafy.config.client.DataProcess;
import com.dafy.config.client.constans.RunModel;
import com.dafy.config.client.exception.SharpConfigException;
import com.dafy.config.client.impl.DataLocalFactory;
import com.dafy.config.client.impl.LocalReloadFactory;
import com.dafy.config.client.impl.RemoteHttpReloadFactory;
import com.dafy.config.client.utils.FileFinder;
import com.google.common.cache.Cache;
import com.google.common.collect.Lists;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.*;
import java.util.*;

/**
 * Created by de on 2017/4/7.
 */

public class ConfigBuilderTest {

    @Test
    public void testBuild() {

        try {
            ConfigBuilder.build()
                    .bind("remote.properties") //远程properties配置文件，使用默认的本地存储/本地加载
                    .start();
        } catch (SharpConfigException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

//        ConfigBuilder.build().on("remote.properties").getString("para0");

//        ConfigBuilder configBuilder1 = ConfigBuilder.build();
//        Assert.assertEquals(configBuilder, configBuilder1);
//        configBuilder.reload(Thread.currentThread().getContextClassLoader().getResourceAsStream("data.0.json"));
//        configBuilder.reload(Thread.currentThread().getContextClassLoader().getResourceAsStream("data.1.json"));
    }

    @Test
    public void testLocalReloadFactory() throws IOException {
//        Properties properties = new Properties();
//        try {
//            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("sharp.config.properties"));
//            LocalReloadFactory.DefaultProperties defaultProperties = new LocalReloadFactory.DefaultProperties(properties);
//            ConfigInfo configInfo = new ConfigInfo();
//            configInfo.setConfigName("config0.properties");
//            System.out.println(defaultProperties.load(configInfo));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Test
    public void testLoad() {

//        Properties properties = new Properties();
//        try {
//            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("sharp.config.properties"));
//            LocalReloadFactory.DefaultProperties defaultProperties = new LocalReloadFactory.DefaultProperties(properties);
//            ConfigInfo configInfo = new ConfigInfo();
//            configInfo.setConfigName("config0.properties");
//            configInfo.setTimestamp(123123123);
//            configInfo.setVersion("111");
////            LocalReloadFactory.build(properties);
////            LocalReloadFactory.defaultProperties.
//            DataLocalFactory.build(properties);
//            DataLocalFactory.getDefaultDataLocal().local(configInfo, "asdfasdfasdfasdf");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        ConfigBuilder configBuilder = ConfigBuilder.build()
//                .bind("config0", RemoteHttpReloadFactory.defaultProperties())
//                .init(Thread.currentThread().getContextClassLoader().getResourceAsStream("data.x.json"));
//        Assert.assertEquals(configBuilder.on("config0").getInt("para0"),1);

    }

    @Test
    public void testDir() {
        FileFinder ff = new FileFinder("D:\\temp\\", "config.properties");
        try {
            ff.walk();
            System.out.println(ff.getSortedFileList());
            System.out.println(ff.lastFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void enumtest() {
        ConfigInfo cc = new ConfigInfo();
        cc.setModel(RunModel.OFFLINE.toString());
        System.out.println(JSONObject.toJSON(cc));
        System.out.println(Arrays.toString("a$sdfsd$sdfsdf".split("\\$")));
    }

    @Test
    public void testLocalIp() {
        try {
            System.out.println(InetAddress.getLocalHost());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
//        ConfigBuilder cb = ConfigBuilder.build();
//        cb.get("","")
    }

    @Test
    public void testLocal() {
        try {
            ConfigBuilder.build().bind("element.properties").start();
        } catch (SharpConfigException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println(Lists.asList("", ConfigBuilder.build().on("element.properties").getStrArray("nBorrowMode", ",")).contains("30"));
    }

    @Test
    public void testIpAddr() throws UnknownHostException, SocketException {
        System.out.println(currentIpAdd());
    }

    public static String currentIpAdd(){
        List<String> ips = Lists.newArrayList();
        Enumeration allNetInterfaces = null;
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
            if (ips.isEmpty()){
                return "127.0.0.1";
            }
            if (ips.size()==1){
                return ips.get(0);
            }
            Collections.sort(ips);
            return ips.get(ips.size()-1);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "127.0.0.1";
    }
}
