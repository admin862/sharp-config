package com.dafy.config.service;

import com.dafy.config.constants.CacheConstant;
import com.dafy.config.constants.ZooKeeperConstant;
import com.dafy.config.dao.DAO;
import com.dafy.config.domain.*;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author yanfeng
 * @create 2017-04-06 11:33
 **/
@Service
public class GroupInfoService {

    @Autowired
    private DAO<GroupInfo> groupInfoDAO;

    @Autowired
    private DAO<GroupWhiteList> groupWhiteListDAO;

    @Autowired
    private AppInfoService appInfoService;

    @Autowired
    private ZooKeeperService zooKeeperService;

    private Cache<String,List<GroupWhiteList>> groupWhiteListCache = CacheBuilder.newBuilder().expireAfterWrite(CacheConstant.COMMON_CACHE_TIME,TimeUnit.SECONDS).build();

    public boolean save(GroupInfo groupInfo){
        if(getGroupInfoByEnvIdAndGroupName(groupInfo.getEnvironmentId(),groupInfo.getGroupName()) != null){
            return false;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(ZooKeeperConstant.ZK_ROOT_PATH);
        sb.append("/");
        sb.append(groupInfo.getEnvironmentPath());
        sb.append("/");
        sb.append(groupInfo.getGroupName());
        String path = sb.toString();
        if(!zooKeeperService.existZNode(path)){
            zooKeeperService.createPersistentZNode(sb.toString());
        }
        return groupInfoDAO.save("GroupInfoMapper.save",groupInfo) == 1;
    }

    public GroupInfo getGroupInfoById(long groupInfoId){
        return groupInfoDAO.getModelBy("GroupInfoMapper.getGroupInfoById",groupInfoId);
    }

    public GroupInfo getGroupInfoByEnvIdAndGroupName(long envId,String groupName){
        Map<String,Object> map = Maps.newHashMap();
        map.put("envId",envId);
        map.put("groupName",groupName);
        return groupInfoDAO.getModelBy("GroupInfoMapper.getGroupInfoByEnvIdAndGroupName",map);
    }

    public List<GroupInfo> getGroupInfoListByGroupName(String groupName){
        return groupInfoDAO.getListBy("GroupInfoMapper.getGroupInfoByGroupName",groupName);
    }

    public List<GroupInfo> getUndeleteGroupInfoListByGroupName(String groupName){
        return groupInfoDAO.getListBy("GroupInfoMapper.getUndeleteGroupInfoListByGroupName",groupName);
    }

    public List<GroupInfo> getAllGroupInfoList(){
        return groupInfoDAO.getListBy("GroupInfoMapper.getAllGroupInfo",null);
    }

    public List<GroupInfo> getUndeleteAllGroupInfoList(){
        return groupInfoDAO.getListBy("GroupInfoMapper.getUndeleteAllGroupInfo",null);
    }

    public List<GroupInfo> getGroupInfoListByEnvId(long envId){
        return groupInfoDAO.getListBy("GroupInfoMapper.getGroupInfoListByEnvId",envId);
    }

    public boolean deleteGroupById(Long groupId){
        GroupInfo groupInfo = this.getGroupInfoById(groupId);
        if(groupInfo == null){
            return false;
        }
        List<AppInfo> appInfoList = appInfoService.getAppInfoListByGroupId(groupId);
        for (AppInfo appInfo : appInfoList) {
            if(appInfo.getIsDelete() == 1){
                continue;
            }
            appInfoService.deleteAppByAppId(appInfo.getId());
        }
        groupInfoDAO.update("GroupInfoMapper.deleteGroupById",groupId);
        zooKeeperService.deleteRecursiveByPath(groupInfo.getZKPath());
        return true;
    }

    /**
     * 获取组的白名单(有缓存)
     * @return
     */
    public List<GroupWhiteList> getGroupWhiteList(){
        List<GroupWhiteList> result = Lists.newArrayList();
        try {
            result = groupWhiteListCache.get("groupWhiteListCache", new Callable<List<GroupWhiteList>>() {
                @Override
                public List<GroupWhiteList> call() throws Exception {
                    return groupWhiteListDAO.getListBy("GroupWhiteListMapper.getList",null);
                }
            });
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean isInWhiteList(long groupId){
        for (GroupWhiteList domain : getGroupWhiteList()){
            if(domain.getGroupId() == groupId){
                return true;
            }
        }
        return false;
    }

}
