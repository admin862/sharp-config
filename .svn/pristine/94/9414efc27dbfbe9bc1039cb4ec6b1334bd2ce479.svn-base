[TOC]

sharp-config
===========

#### 配置中心的核心理念

配置中心的目标是实现配置的中心化管理，对更改配置的行为无需重启系统，同时尽最大可能保证分布式情况下配置的一致性。
    
配置中心采用树形结构进行分层，并且这种分层结构与zookeeper上的目录结构保持一致。
1. 第一层为环境。由于我们承担App可能在多个不同的环境下会有不同的配置情况，因此配置中心
采用多个环境来支持，比如我们有线上环境(online)，开发环境（dev），12测试环境（12）等等。
2. 第二层为组。组的概念是App的上一级，之所以有组的概念，是为了更好的管理App。
3. 第三层为App。App的概念就是我们的应用App。

其大致结构如下图所示。
![](/imag)
    
### 开发者使用指南


#### 所需依赖

        <dependency>    
            <groupId>com.dafy.config</groupId>          
            <artifactId>sharp-config-client</artifactId>    
        </dependency>
        <dependency>   
            <groupId>org.apache.zookeeper</groupId>     
            <artifactId>zookeeper</artifactId>   
        </dependency>  
        <dependency>   
            <groupId>com.101tec</groupId>    
            <artifactId>zkclient</artifactId>    
        </dependency>    
        <dependency>    
            <groupId>com.google.guava</groupId>   
            <artifactId>guava</artifactId>    
        </dependency>
        <dependency>   
            <groupId>com.alibaba</groupId>     
            <artifactId>fastjson</artifactId>    
        </dependency>  
        
#### 关于sharp.config.properties
在接入sharp-config-client时，首先要在自己的项目下添加配置文件sharp.config.properties。

测试环境的默认模板如下所示。

    #######zookeeper-host      
    zk.host=192.168.0.121:2181       
    #######zookeeper的session超时时间(单位毫秒) 建议设置在10000-60000之间      
    zk.session.timeout=10000       
    #######zookeeper的连接超时时间(单位毫秒) 建议设置为10000       
    zk.connection.timeout=10000       
    #######模式 online模式则从配置中心获取配置  offline模式则从本地获取配置        
    model=online         
    #######配置中心的服务url          
    remote.url=http://192.168.0.103:8000/api/       
    #######本地配置保存的路径         
    local.resource.path=D://DAFY/        
    #######所属的环境       
    env.name=example          
    #######所属的组          
    group.name=sharp-config-example         
    #######所属的App         
    app.name=SharpDemo        


#### Api的使用说明
初始化（建议在项目启动的时候就初始化，而不要在项目运行的时候初始化），如果初始化失败则会抛出异常。
<pre>
    ConfigBuilder
        .build()
        .bind("app.properties")
        .start();
</pre>
其中app.properties为key-value形式的配置文件。    
如果不是key-value形式的文件，而是在后台自定义的格式，那么则需要覆写

* 绑定key-value形式的配置文件时
    <pre>ConfigBuilder.build().bind("app.properties");</pre>
* 绑定json形式的配置文件时
            ConfigBuilder.build().
                bind("config.json", new DataProcess<String, String, String>() {

                            @Override
                            public void dataProcess(SCache<String, String> cache, String content) {
                                        System.out.println(content);
                                        System.out.println(JSON.parse(content));
                                        cache.put("p", content);
                            }
                });
* 绑定数据库形式的配置文件时

        ConfigBuilder.build()
                     .bind("db-tbFundProvider",new ConfigBuilder.BaseReload<Integer, CDO, CDO[]>(){
						/**
						 * 加载数据
						 */
						@Override
						public CDO[] load(ConfigInfo configInfo) throws IOException {
							System.out.println("加载数据！！");
							CDO cdoRequest = CDO.newRequest("FundProviderService", "getAllFundProviders");
							CDO cdoResponse = new CDO();
							Return retGetConfig = BusinessService.getInstance().handleTrans(cdoRequest, cdoResponse);
							if (retGetConfig == null || retGetConfig.getCode() != 0) {
								throw new RuntimeException("init data config exception");
							}
							CDO[] cdosFundProvider = cdoResponse.getCDOArrayValue("cdosFundProvider");
							for (CDO cdo : cdosFundProvider) {
								System.out.println(cdo.toXML());
							}
							return cdosFundProvider;
						}

						/**
						 * 数据处理
						 */
						@Override
						public void dataProcess(SCache<Integer, CDO> cache, CDO[] content) {
							for (CDO cdo : content) {
								cache.put(cdo.getIntegerValue("nCode"), cdo);
							}
						}
					});

使用
<pre>
    String value = ConfigBuilder.build().getString("app.properties","key");
</pre>


### demo



### 配置中心的服务端说明
配置中心的配置说明。
在一级菜单业务系统配置中，其二级菜单就是各个环境下的配置文件，我们可以通过App进行搜索，搜索到该App下的配置文件，不过需要注意的是，一个App可能在多个环境中存在。











