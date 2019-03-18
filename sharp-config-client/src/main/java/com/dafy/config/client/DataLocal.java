package com.dafy.config.client;

/**
 * 本地文件存储
 * <p>
 * 将配置信息保存在本地
 * <p>
 * Created by de on 2017/4/8.
 */
public interface DataLocal<T> {

    void local(ConfigInfo configInfo, T content);

}
