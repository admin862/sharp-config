package com.dafy.config.client;

import java.io.IOException;

/**
 *
 * 此服务用于加载数据。
 *
 * load方法应当根据实际情况实现http或者db方式或本地方式加载数据;
 *
 * Created by de on 2017/4/6.
 */
public interface DataLoad<T> {

    T load(ConfigInfo configInfo) throws IOException;

}
