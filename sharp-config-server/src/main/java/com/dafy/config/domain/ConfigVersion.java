package com.dafy.config.domain;

import com.alibaba.fastjson.JSON;
import com.dafy.config.constants.ZooKeeperConstant;

import java.util.Date;

/**
 * @author yanfeng
 * @create 2017-04-12 21:10
 **/
public class ConfigVersion {
    /** 主键id **/
    private int id;
    /** 所属环境id **/
    private long environmentId;
    /** 所属环境id名称 **/
    private String environmentName;
    /** 所属环境路径 **/
    private String environmentPath;
    /** 组id **/
    private long groupId;
    /** 组名称 **/
    private String groupName;
    /** appId **/
    private long appId;
    /** app名称 **/
    private String appName;
    /** 配置名称 **/
    private String configName;
    /** 配置信息 **/
    private String dataInfo;
    /** 版本号 **/
    private int version;
    /** 备注信息 **/
    private String remark;
    /** 创建时间 **/
    private Date createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getEnvironmentId() {
        return environmentId;
    }

    public void setEnvironmentId(long environmentId) {
        this.environmentId = environmentId;
    }

    public String getEnvironmentName() {
        return environmentName;
    }

    public void setEnvironmentName(String environmentName) {
        this.environmentName = environmentName;
    }

    public String getEnvironmentPath() {
        return environmentPath;
    }

    public void setEnvironmentPath(String environmentPath) {
        this.environmentPath = environmentPath;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public long getAppId() {
        return appId;
    }

    public void setAppId(long appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public String getDataInfo() {
        return dataInfo;
    }

    public void setDataInfo(String dataInfo) {
        this.dataInfo = dataInfo;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    public String toJSONString(){
        return JSON.toJSONString(this);
    }

    public ConfigBean toConfigBean(){
        ConfigBean configBean = new ConfigBean();
        configBean.setEnvironmentId(getEnvironmentId());
        configBean.setEnvironmentName(getEnvironmentName());
        configBean.setEnvironmentPath(getEnvironmentPath());
        configBean.setGroupName(getGroupName());
        configBean.setAppId(this.getId());
        configBean.setAppName(getAppName());
        configBean.setConfigName(getConfigName());
        configBean.setVersion(getVersion());
        configBean.setDataInfo(getDataInfo());
        configBean.setCreateTime(getCreateTime());
        return configBean;
    }

    public String getZKPath(){
        StringBuilder sb = new StringBuilder();
        sb.append(ZooKeeperConstant.ZK_ROOT_PATH);
        sb.append("/");
        sb.append(this.environmentPath);
        sb.append("/");
        sb.append(this.groupName);
        sb.append("/");
        sb.append(this.appName);
        return sb.toString();
    }

    @Override
    public String toString() {
        return "ConfigVersion{" +
                "id=" + id +
                ", environmentId=" + environmentId +
                ", environmentName='" + environmentName + '\'' +
                ", environmentPath='" + environmentPath + '\'' +
                ", groupId=" + groupId +
                ", groupName='" + groupName + '\'' +
                ", appId=" + appId +
                ", appName='" + appName + '\'' +
                ", configName='" + configName + '\'' +
                ", dataInfo='" + dataInfo + '\'' +
                ", version=" + version +
                ", remark='" + remark + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
