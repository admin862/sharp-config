package com.dafy.config.domain.api;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * 存放在zookeeper节点上的信息
 * @author yanfeng
 * @create 2017-04-15 17:54
 **/
public class ZooKeeperDataApi {

    /** 当前配置信息 **/
    private List<ConfigApiBean> current;
    /** 预发布配置信息 **/
    private List<ConfigApiBean> next;
    /**　状态　0-正常状态 1-预发布 2-已确认 **/
    private int state;

    public enum ZkDataState{
        NORMAL(0),
        PREPARE(1),
        CONFIRM(2);
        private int state;
        ZkDataState(int state){
            this.state = state;
        }

        public int getState(){
            return state;
        }
    }

    public List<ConfigApiBean> getCurrent() {
        return current;
    }

    public void setCurrent(List<ConfigApiBean> current) {
        this.current = current;
    }

    public List<ConfigApiBean> getNext() {
        return next;
    }

    public void setNext(List<ConfigApiBean> next) {
        this.next = next;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String toJSONString(){
        return JSON.toJSONString(this);
    }
}
