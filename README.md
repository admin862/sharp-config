## sharp-config  分布式系统配置中心 ##
======= 

## 主要目标：
1.支持分布式系统配置。    
2.1个jar包,处处运行。    
3.动态改变系统运行时的参数。

## sharp-config-client所需依赖
JDK1.7以及以上

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

### 使用 ###
在pom中添加
    
    <dependency>    
         <groupId>com.dafy.config</groupId>          
         <artifactId>sharp-config-client</artifactId>    |   
         <version>${sharp-config-client.version}</version>    
    </dependency>
    
    
### 初始化ConfigBuilder

<pre>
ConfigBuilder
    .build()
    .bind("app.properties")
    .start();
</pre>
    
### 使用
<pre>
ConfigBuilder.build().get("app.properties","key");
</pre>


## demos 
参考demo项目sharp-config-demo


## 版本
sharp-config-client当前版本0.2.0
