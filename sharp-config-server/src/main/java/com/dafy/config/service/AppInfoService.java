package com.dafy.config.service;

import com.dafy.config.constants.ZooKeeperConstant;
import com.dafy.config.dao.DAO;
import com.dafy.config.domain.AppInfo;
import com.dafy.config.domain.ConfigBean;
import com.dafy.config.domain.GroupInfo;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

/**
 * @author yanfeng
 * @create 2017-04-06 11:33
 **/
@Service
public class AppInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppInfoService.class);

    @Autowired
    private DAO<AppInfo> appInfoDAO;

    @Autowired
    private ZooKeeperService zooKeeperService;

    @Autowired
    private ConfigService configService;


    public boolean save(AppInfo appInfo){
        if(getAppInfoByGroupIdAndName(appInfo.getGroupId(),appInfo.getAppName()) != null){
            return false;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(ZooKeeperConstant.ZK_ROOT_PATH);
        sb.append("/");
        sb.append(appInfo.getEnvironmentPath());
        sb.append("/");
        sb.append(appInfo.getGroupName());
        sb.append("/");
        sb.append(appInfo.getAppName());
        String path = sb.toString();
        if(!zooKeeperService.existZNode(path)){
            zooKeeperService.createPersistentZNode(sb.toString());
        }
        return appInfoDAO.save("AppInfoMapper.save",appInfo) == 1;
    }

    public AppInfo getAppInfoById(long id){
        return appInfoDAO.getModelBy("AppInfoMapper.geAppInfoById",id);
    }

    public AppInfo getAppInfoByGroupIdAndName(long groupId,String appName){
        Map<String,Object> map = Maps.newHashMap();
        map.put("groupId",groupId);
        map.put("appName",appName);
        return appInfoDAO.getModelBy("AppInfoMapper.getAppInfoByGroupIdAndName",map);
    }

    public List<AppInfo> getAppInfoListByAppName(String appName){
        return appInfoDAO.getListBy("AppInfoMapper.getAppInfoListByAppName",appName);
    }

    public List<AppInfo> getUndeleteAppInfoListByAppName(String appName){
        return appInfoDAO.getListBy("AppInfoMapper.getExistAppInfoListByAppName",appName);
    }

    public List<AppInfo> getAppInfoListByGroupId(long groupId){
        return appInfoDAO.getListBy("AppInfoMapper.getAppInfoListByGroupId",groupId);
    }

    @Transactional
    public boolean deleteAppByAppId(long appId){
        AppInfo appInfo = this.getAppInfoById(appId);
        if(appInfo == null){
            return false;
        }
        if(zooKeeperService.countChildren(appInfo.getZKPath()) != 0){
            return false;
        }
        List<ConfigBean> configBeanList = configService.getConfigByAppId(appId);
        if(configBeanList == null || configBeanList.size() == 0){
            return true;
        }
        for (ConfigBean configBean : configBeanList) {
            if(!configService.deleteConfigById(configBean.getId())){
                throw new RuntimeException("delete config failed!");
            }
        }
        if(appInfoDAO.update("AppInfoMapper.deleteAppById",appId) != 1){
            LOGGER.error("删除App失败");
            return false;
        }
        return true;
    }

}
