package com.dafy.config.client.constans;

/**
 * Created by de on 2017/4/18.
 */
public enum RunModel {
    ONLINE{
        @Override
        public String toString() {
            return "online";
        }
    }, OFFLINE{
        @Override
        public String toString() {
            return "offline";
        }
    }

}
