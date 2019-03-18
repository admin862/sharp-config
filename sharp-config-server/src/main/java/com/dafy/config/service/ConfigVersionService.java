package com.dafy.config.service;

import com.dafy.config.dao.DAO;
import com.dafy.config.domain.ConfigBean;
import com.dafy.config.domain.ConfigVersion;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

@Service("configVersionService")
public class ConfigVersionService {

    @Autowired
    private DAO<ConfigVersion> daoConfigVersion;

    public boolean save(ConfigVersion configVersion){
        return daoConfigVersion.save("ConfigVersionMapper.save",configVersion) == 1;
    }

    public boolean deleteVersionById(long id){
        return daoConfigVersion.update("ConfigVersionMapper.deleteVersion",id) == 1;
    }

    public boolean deleteVersionByAppIdAndConfigName(long appId,String configName){
        Map<String,Object> map = Maps.newHashMap();
        map.put("appId",appId);
        map.put("configName",configName);
        return daoConfigVersion.update("ConfigVersionMapper.deleteVersionByAppIdAndConfigName",map) != 0;
    }

    public ConfigVersion getConfigVersionById(long id){
        return daoConfigVersion.getModelBy("ConfigVersionMapper.getConfigVersionById",id);
    }

    public List<ConfigVersion> getConfigVersionByAppIdAndConfigName(long appId,String configName){
        Map<String,Object> map = Maps.newHashMap();
        map.put("appId",appId);
        map.put("configName",configName);
        return daoConfigVersion.getListBy("ConfigVersionMapper.getConfigVersionByAppIdAndConfigName",map);
    }

    public List<ConfigVersion> getConfigVersionBy(String environmentPath,String groupName,String appName,String configName){
        Map<String,Object> map = Maps.newHashMap();
        map.put("environmentPath",environmentPath);
        map.put("groupName",groupName);
        map.put("appName",appName);
        map.put("configName",configName);
        return daoConfigVersion.getListBy("ConfigVersionMapper.getConfigVersionByEnvAndGroupAndAppAndConfig",map);
    }

    public ConfigVersion getMaxConfigVersionByAppIdAndConfigName(long appId,String configName){
        Map<String,Object> map = Maps.newHashMap();
        map.put("appId",appId);
        map.put("configName",configName);
        return daoConfigVersion.getModelBy("ConfigVersionMapper.getMaxConfigVersionByAppIdAndConfigName",map);
    }

    public ConfigVersion getConfigVersionBy(String environmentPath,String groupName,String appName,String configName,int version){
        Map<String,Object> map = Maps.newHashMap();
        map.put("environmentPath",environmentPath);
        map.put("groupName",groupName);
        map.put("appName",appName);
        map.put("configName",configName);
        map.put("version",version);
        ConfigVersion configVersion = daoConfigVersion.getModelBy("ConfigVersionMapper.getConfigVersionBy",map);
        return configVersion;
    }

    private ConfigVersion getConfigVersionByAppIdAndConfigName(long appId,String configName,int currentVersion){
        List<ConfigVersion> list = this.getConfigVersionByAppIdAndConfigName(appId,configName);
        if(list == null || list.size() == 0){
            return null;
        }
        for (ConfigVersion configVersion : list) {
            if(configVersion.getVersion() != currentVersion){
                return configVersion;
            }
        }
        return null;
    }

    public List<ConfigVersion> getNextVersionList(List<ConfigBean> configBeanList){
        List<ConfigVersion> resultList = Lists.newArrayList();
        for (ConfigBean configBean : configBeanList) {
            resultList.add(getConfigVersionByAppIdAndConfigName(configBean.getAppId(),configBean.getConfigName(),configBean.getVersion()));
        }
        return resultList;
    }

    public boolean canPrepareConfirmByGroup(List<ConfigBean> configBeanList){
        for (ConfigBean configBean : configBeanList) {
            List<ConfigVersion> tmpList = getConfigVersionByAppIdAndConfigName(configBean.getAppId(),configBean.getConfigName());
            if(CollectionUtils.isEmpty(tmpList) || tmpList.size() == 1){
                return false;
            }
        }
        return true;
    }

}
