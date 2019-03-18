package com.dafy.config.service;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dafy.config.constants.ZooKeeperConstant;
import com.dafy.config.dao.DAO;
import com.dafy.config.domain.AppInfo;
import com.dafy.config.domain.ConfigBean;
import com.dafy.config.domain.Environment;
import com.dafy.config.domain.GroupInfo;
import com.dafy.config.domain.api.ZooKeeperDataApi;

/**
 * @author yanfeng
 * @create 2017-04-06 11:33
 **/
@Service
public class EnvironmentService {

    private static final int CACHE_EXPIRE_TIME = 60;

    private static final String CACHE_KEY = "cacheEnvironmentList";

    private Cache<String,List<Environment>> cache = CacheBuilder.newBuilder()
            .expireAfterWrite(CACHE_EXPIRE_TIME, TimeUnit.SECONDS)
            .build();

    @Autowired
    private DAO<Environment> environmentDAO;

    @Autowired
    private ZooKeeperService zooKeeperService;

    @Autowired
    private GroupInfoService groupInfoService;

    @Autowired
    private AppInfoService appInfoService;

    @Autowired
    private ConfigService configService;

    public boolean save(Environment environment){
        if(getEnvironmentByName(environment.getName()) != null){
            return false;
        }
        String path = ZooKeeperConstant.ZK_ROOT_PATH + "/" + environment.getPath();
        if(!zooKeeperService.existZNode(path)){
            zooKeeperService.createPersistentZNode(path);
        }
        cache.invalidate(CACHE_KEY);
        return environmentDAO.save("EnvironmentMapper.save",environment) == 1;
    }

    public Environment getEnvironmentById(long id){
        return environmentDAO.getModelBy("EnvironmentMapper.getEnvironmentById",id);
    }

    public List<Environment> getAllEnvironmentList(){
        if(cache.getIfPresent(CACHE_KEY) == null){
            cache.put(CACHE_KEY,environmentDAO.getListBy("EnvironmentMapper.getAllEnvironment",null));
        }
        return cache.getIfPresent(CACHE_KEY);
    }

    public Environment getEnvironmentByName(String name){
        return environmentDAO.getModelBy("EnvironmentMapper.getEnvironmentByName",name);
    }

    public Environment getEnvironmentByPath(String path){
        return environmentDAO.getModelBy("EnvironmentMapper.getEnvironmentByPath",path);
    }

    public boolean synchronizeSingleZooKeeperEnvironment(Long envId){
        Environment environment = getEnvironmentById(envId);
        if(environment == null){
            return false;
        }
        String envPath = ZooKeeperConstant.ZK_ROOT_PATH + "/" + environment.getPath();
        if(!zooKeeperService.existZNode(envPath)){
            zooKeeperService.createPersistentZNode(envPath);
        }
        List<GroupInfo> groupInfoList = groupInfoService.getGroupInfoListByEnvId(environment.getId());
        for (GroupInfo groupInfo : groupInfoList) {
            String groupInfoPath = envPath + "/" + groupInfo.getGroupName();
            if(!zooKeeperService.existZNode(groupInfoPath)){
                zooKeeperService.createPersistentZNode(groupInfoPath);
            }
            List<AppInfo> appInfoList = appInfoService.getAppInfoListByGroupId(groupInfo.getId());
            for (AppInfo appInfo : appInfoList) {
                String appPath = groupInfoPath + "/" + appInfo.getAppName();
                if(!zooKeeperService.existZNode(appPath)){
                    zooKeeperService.createPersistentZNode(appPath);
                    ZooKeeperDataApi api = new ZooKeeperDataApi();
                    List<ConfigBean> configBeanList = configService.getConfigByAppId(appInfo.getId());
                    api.setCurrent(configService.toListConfigApiBean(configBeanList));
                    api.setState(ZooKeeperDataApi.ZkDataState.NORMAL.getState());
                    zooKeeperService.updateZNodeData(appPath,api.toJSONString());
                }
            }
        }
        return true;
    }

    public boolean synchronizeAllZooKeeperEnvironment(){
        List<Environment> environmentList = getAllEnvironmentList();
        for (Environment environment : environmentList) {
            synchronizeSingleZooKeeperEnvironment(environment.getId());
        }
        return true;
    }

    public boolean synchronizeEnvironment(Long envIdFrom,Long envIdTo){
        Environment environmentA = getEnvironmentById(envIdFrom);
        Environment environmentB = getEnvironmentById(envIdTo);
        if(environmentA == null || environmentB == null){
            return false;
        }

        List<GroupInfo> groupInfoAList = groupInfoService.getGroupInfoListByEnvId(environmentA.getId());
        List<GroupInfo> groupInfoBList = groupInfoService.getGroupInfoListByEnvId(environmentB.getId());

        // 1.对比组
        for (GroupInfo groupInfoA : groupInfoAList) {
            if(!existInGroupInfo(groupInfoBList,groupInfoA)){
                GroupInfo groupInfoB = new GroupInfo();
                groupInfoB.setEnvironmentId(environmentB.getId());
                groupInfoB.setEnvironmentName(environmentB.getName());
                groupInfoB.setEnvironmentPath(environmentB.getPath());
                groupInfoB.setGroupName(groupInfoA.getGroupName());
                groupInfoB.setRemark(groupInfoA.getRemark());
                groupInfoService.save(groupInfoB);
                groupInfoBList = groupInfoService.getGroupInfoListByEnvId(environmentB.getId());
            }
            GroupInfo groupInfoB = getGroupInfoByGroupName(groupInfoBList,groupInfoA.getGroupName());
            List<AppInfo> appInfoListA = appInfoService.getAppInfoListByGroupId(groupInfoA.getId());
            List<AppInfo> appInfoListB = appInfoService.getAppInfoListByGroupId(groupInfoB.getId());

            // 2.对比App
            for (AppInfo appInfoA : appInfoListA) {
                if(!existInAppInfo(appInfoListB,appInfoA)){
                    AppInfo appInfoB = new AppInfo();
                    appInfoB.setEnvironmentId(environmentB.getId());
                    appInfoB.setEnvironmentName(environmentB.getName());
                    appInfoB.setEnvironmentPath(environmentB.getPath());
                    appInfoB.setGroupId(groupInfoB.getId());
                    appInfoB.setGroupName(groupInfoB.getGroupName());
                    appInfoB.setAppName(appInfoA.getAppName());
                    appInfoB.setRemark(appInfoA.getRemark());
                    appInfoService.save(appInfoB);
                    appInfoListB = appInfoService.getAppInfoListByGroupId(groupInfoB.getId());
                }

                // 3.对比config
                AppInfo appInfoB = getAppInfoByAppName(appInfoListB,appInfoA.getAppName());

                List<ConfigBean> configBeanListA = configService.getConfigByAppId(appInfoA.getId());
                List<ConfigBean> configBeanListB = configService.getConfigByAppId(appInfoB.getId());


                for (ConfigBean configBeanA : configBeanListA) {
                    if(!existInConfigBean(configBeanListB,configBeanA)){
                        ConfigBean configBeanB = new ConfigBean();
                        configBeanB.setEnvironmentId(environmentB.getId());
                        configBeanB.setEnvironmentName(environmentB.getName());
                        configBeanB.setEnvironmentPath(environmentB.getPath());
                        configBeanB.setGroupId(groupInfoB.getId());
                        configBeanB.setGroupName(groupInfoB.getGroupName());
                        configBeanB.setAppId(appInfoB.getId());
                        configBeanB.setAppName(appInfoB.getAppName());
                        configBeanB.setConfigName(configBeanA.getConfigName());
                        configBeanB.setVersion(configBeanA.getVersion());
                        configBeanB.setDataInfo(configBeanA.getDataInfo());
                        configBeanB.setRemark(configBeanA.getRemark());
                        configService.save(configBeanB);
                        configBeanListB = configService.getConfigByAppId(appInfoB.getId());
                    }
                }
            }
        }
        return true;
    }

    private boolean existInGroupInfo(List<GroupInfo> groupInfoListB,GroupInfo groupInfoA){
        for (GroupInfo groupInfoB : groupInfoListB) {
            if(groupInfoB.getGroupName().equals(groupInfoA.getGroupName())){
                return true;
            }
        }
        return false;
    }

    private boolean existInAppInfo(List<AppInfo> appInfoListB,AppInfo appInfoA){
        for (AppInfo appInfoInListB : appInfoListB) {
            if(appInfoInListB.getAppName().equals(appInfoA.getAppName())){
                return true;
            }
        }
        return false;
    }

    private boolean existInConfigBean(List<ConfigBean> configBeanListB,ConfigBean configBeanA){
        for (ConfigBean configBeanB : configBeanListB) {
            if(configBeanB.getConfigName().equals(configBeanA.getConfigName())){
                return true;
            }
        }
        return false;
    }

    private GroupInfo getGroupInfoByGroupName(List<GroupInfo> groupInfoListB,String groupName){
        for (GroupInfo groupInfoB : groupInfoListB) {
            if(groupInfoB.getGroupName().equals(groupName)){
                return groupInfoB;
            }
        }
        return null;
    }

    private AppInfo getAppInfoByAppName(List<AppInfo> appInfoListB,String appName){
        for (AppInfo appInoB : appInfoListB) {
            if(appInoB.getAppName().equals(appName)){
                return appInoB;
            }
        }
        return null;
    }

}
