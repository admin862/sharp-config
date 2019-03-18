package com.dafy.config.domain.api;

import java.util.Date;

/**
 * 在ZooKeeper bean
 * @author yanfeng
 * @create 2017-04-13 20:11
 **/
public class ConfigApiBean {

    /** 配置名称 **/
    private String configName;
    /** 版本号 **/
    private int version;
    /** 时间戳 **/
    private long timestamp;

    public ConfigApiBean(){}

    public ConfigApiBean(String configName,int version){
        this.configName = configName;
        this.version = version;
        timestamp = new Date().getTime();
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConfigApiBean apiBean = (ConfigApiBean) o;

        if (version != apiBean.version) return false;
        if (timestamp != apiBean.timestamp) return false;
        boolean result = configName != null ? configName.equals(apiBean.configName) : apiBean.configName == null;
        return result;
    }

    @Override
    public int hashCode() {
        int result = configName != null ? configName.hashCode() : 0;
        result = 31 * result + version;
        result = 31 * result + (int) (timestamp ^ (timestamp >>> 32));
        return result;
    }
}
