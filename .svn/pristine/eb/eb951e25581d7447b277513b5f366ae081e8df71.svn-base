package com.dafy.config.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dafy.config.dao.DAO;
import com.dafy.config.domain.*;
import com.dafy.config.domain.api.ConfigApiBean;
import com.dafy.config.domain.api.ZooKeeperDataApi;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.concurrent.*;

/**
 * @author yanfeng
 * @create 2017-03-31 18:16
 **/
@Service("configService")
@Transactional
public class ConfigService {

    @Autowired
    private DAO<ConfigBean> daoConfig;

    @Autowired
    private DAO<ConfigVersion> daoConfigVersion;

    @Autowired
    private ConfigVersionService configVersionService;

    @Autowired
    private ZooKeeperService zooKeeperService;

    @Autowired
    private AuditTaskService auditTaskService;

    /** 重试时候每次睡眠时间 单位:毫秒 **/
    private static final long RETRY_SLEEP_TIME = 100;

    @Transactional
    public boolean save(ConfigBean configBean){
        if(getConfigByAppIdConfigName(configBean.getAppId(),configBean.getConfigName()) != null){
            return false;
        }
        daoConfig.save("ConfigMapper.save",configBean);
        daoConfigVersion.save("ConfigVersionMapper.save",configBean);
        List<ConfigBean> currentList = getConfigByAppId(configBean.getAppId());
        ZooKeeperDataApi api = new ZooKeeperDataApi();
        api.setCurrent(toListConfigApiBean(currentList));
        api.setState(ZooKeeperDataApi.ZkDataState.NORMAL.getState());
        String path = configBean.getZKPath();
        if(!zooKeeperService.existZNode(path)){
            zooKeeperService.createPersistentZNode(path);
        }
        zooKeeperService.updateZNodeData(path,api.toJSONString());
        return true;
    }

    public ConfigBean getConfigById(long id) {
        return daoConfig.getModelBy("ConfigMapper.getConfigById",id);
    }

    public ConfigBean getCurrentConfigByName(String envPath,String groupName,String appName,String configName) {
        Map<String,Object> map = Maps.newHashMap();
        map.put("environmentPath",envPath);
        map.put("groupName",groupName);
        map.put("appName",appName);
        map.put("configName",configName);
        return daoConfig.getModelBy("ConfigMapper.getConfigBy",map);
    }

    public ConfigBean getConfigByAppIdConfigName(long appId,String configName){
        Map<String,Object> map = Maps.newHashMap();
        map.put("appId",appId);
        map.put("configName",configName);
        return daoConfig.getModelBy("ConfigMapper.getConfigByAppIdConfigName",map);
    }

    public List<ConfigBean> getConfigByAppId(long appId){
        return daoConfig.getListBy("ConfigMapper.getConfigByAppId",appId);
    }

    public List<ConfigBean> getConfigByEnvIdAndAppName(long envId,String appName){
        Map<String,Object> map = Maps.newHashMap();
        map.put("envId",envId);
        map.put("appName",appName);
        return daoConfig.getListBy("ConfigMapper.getConfigByEnvIdAndAppName",map);
    }

    public List<ConfigBean> getConfigListByEnvironmentPath(String path){
        return daoConfig.getListBy("ConfigMapper.getConfigByEnvironmentPath",path);
    }

    public List<ConfigBean> getConfigListByEnvGroupConfig(String envPath,String groupName,String configName){
        Map<String,Object> map = Maps.newHashMap();
        map.put("environmentPath",envPath);
        map.put("groupName",groupName);
        map.put("configName",configName);
        return daoConfig.getListBy("ConfigMapper.getConfigListByEnvGroupConfig",map);
    }

    public boolean updateVersion(long id,String data,int version){
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        map.put("version",version);
        map.put("data",data);
        return daoConfig.update("ConfigMapper.updateVersion",map) == 1;
    }

    @Transactional
    public boolean deleteConfigById(long id){
        ConfigBean configBean = getConfigById(id);
        String path = configBean.getZKPath();
        if(zooKeeperService.countChildren(path) != 0){
            return false;
        }
        if(daoConfig.delete("ConfigMapper.deleteConfigById",id) != 1){
            throw new RuntimeException("删除单表tbConfig失败");
        }
        if(!configVersionService.deleteVersionByAppIdAndConfigName(configBean.getAppId(),configBean.getConfigName())){
            throw new RuntimeException("更改tbConfigVersion失败");
        }
        List<ConfigBean> configBeanList = this.getConfigByAppId(configBean.getAppId());

        if(configBeanList == null || configBeanList.size() == 0){
            zooKeeperService.deleteZNodeByPath(path);
        } else {
            ZooKeeperDataApi api = new ZooKeeperDataApi();
            api.setCurrent(toListConfigApiBean(configBeanList));
            api.setState(ZooKeeperDataApi.ZkDataState.NORMAL.getState());
            zooKeeperService.updateZNodeData(path,JSON.toJSONString(api));
        }
        return true;
    }

    /**
     * 预提交
     * @param prepareConfigVersion 预提交的版本
     * @return
     */
    public boolean prepare(ConfigVersion prepareConfigVersion){
        List<ConfigBean> currentList = this.getConfigByAppId(prepareConfigVersion.getAppId());
        ConfigBean nextUpdateBean = prepareConfigVersion.toConfigBean();
        List<ConfigBean> nextList = Lists.newLinkedList();
        nextList.add(nextUpdateBean);
        ZooKeeperDataApi api = new ZooKeeperDataApi();
        api.setCurrent(toListConfigApiBean(currentList));
        api.setNext(toListConfigApiBean(nextList));
        api.setState(ZooKeeperDataApi.ZkDataState.PREPARE.getState());
        String path = prepareConfigVersion.getZKPath();
        zooKeeperService.updateZNodeData(path, JSON.toJSONString(api));
        return true;
    }

    /**
     * 确认
     * @param currentConfigId 当前版本id
     * @param prepareVersionId 预提交版本id
     * @return
     */
    public CommonResult confirm(long currentConfigId,long prepareVersionId){
        CommonResult commonResult = new CommonResult(-1,CommonResult.OPERATE_FAIL);
        ConfigVersion nextConfigVersion = configVersionService.getConfigVersionById(prepareVersionId);
        String path = nextConfigVersion.getZKPath();
        ConfigBean currentConfig = getConfigById(currentConfigId);
        ZooKeeperDataApi currentZKData = JSONObject.parseObject(zooKeeperService.getDataByPath(path).toString(),ZooKeeperDataApi.class);
        if(currentZKData.getNext() == null || currentZKData.getNext().size() <= 0){
            commonResult.setMsg("请先选择版本进行prepare");
            return commonResult;
        }

        int prepareVersion = currentZKData.getNext().get(0).getVersion();
        if(prepareVersion != nextConfigVersion.getVersion()){
            commonResult.setMsg("prepare的版本与当前confirm的版本不一致,prepare的版本为:"+prepareVersion);
            return commonResult;
        }

        List<ConfigBean> currentVersionList = getConfigByAppId(nextConfigVersion.getAppId());
        List<ZNode> zNodeList = zooKeeperService.getZNodeListByPath(path);
        if(zNodeList.size() == 0){
            handleConfirm(currentVersionList,currentConfigId,nextConfigVersion, ZooKeeperDataApi.ZkDataState.NORMAL.getState(),path);
        } else {
            for (ZNode zNode : zNodeList) {
                ZooKeeperDataApi childData = JSONObject.parseObject(zNode.getData().toString(),ZooKeeperDataApi.class);
                if(childData == null || childData.getNext() == null || !childData.getNext().equals(currentZKData.getNext())){
                    commonResult.setMsg("某些集群节点还没有准备好,请稍后重试!");
                    return commonResult;
                }
            }
            handleConfirm(currentVersionList,currentConfigId,nextConfigVersion,ZooKeeperDataApi.ZkDataState.CONFIRM.getState(),path);
        }
        auditTaskService.processState(currentConfigId,currentConfig.getVersion(),nextConfigVersion.getVersion(),AuditTask.AuditState.FINISH);
        commonResult.setCode(0);
        commonResult.setMsg(CommonResult.OPERATE_SUCCESS);
        return commonResult;
    }

    /**
     * 预提交与确认
     * @param currentVersion 当前配置信息版本
     * @param nextVersion    预提交的配置信息版本
     * @param retryNumber    重试次数
     * @return
     */
    public CommonResult prepareAndConfirm(ConfigBean currentVersion,ConfigVersion nextVersion,int retryNumber){
        CommonResult commonResult = new CommonResult(0,CommonResult.OPERATE_SUCCESS);
        if(!prepare(nextVersion)){
            commonResult.setCode(-1);
            commonResult.setMsg("服务器内部错误,请稍后重试!");
            return commonResult;
        }

        ZooKeeperDataApi currentZKData = JSONObject.parseObject(zooKeeperService.getDataByPath(currentVersion.getZKPath()).toString(),ZooKeeperDataApi.class);
        List<ZNode> zNodeList = zooKeeperService.getZNodeListByPath(currentVersion.getZKPath());
        if(zNodeList.size() == 0){
            return confirm(currentVersion.getId(),nextVersion.getId());
        }

        int queryNumber = 0;
        while (true){
            if(isReady(currentZKData,currentVersion.getZKPath())){
                break;
            }
            try {
                TimeUnit.MILLISECONDS.sleep(RETRY_SLEEP_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            queryNumber ++;
            if(queryNumber >= retryNumber){
                break;
            }
        }
        return confirm(currentVersion.getId(),nextVersion.getId());
    }

    public CommonResult batchPrepareAndConfirm(List<ConfigVersion> nextVersionList,int retryNumber){
        CommonResult commonResult = new CommonResult(0,CommonResult.OPERATE_SUCCESS);
        for (ConfigVersion nextVersion : nextVersionList) {
            if(!prepare(nextVersion)){
                commonResult.setCode(-1);
                commonResult.setMsg("服务器内部错误,请稍后重试!");
                return commonResult;
            }
        }
        for (ConfigVersion nextVersion : nextVersionList) {
            ZooKeeperDataApi currentZKData = JSONObject.parseObject(zooKeeperService.getDataByPath(nextVersion.getZKPath()).toString(),ZooKeeperDataApi.class);
            List<ZNode> zNodeList = zooKeeperService.getZNodeListByPath(nextVersion.getZKPath());
            ConfigBean configBean = getConfigByAppIdConfigName(nextVersion.getAppId(),nextVersion.getConfigName());
            if(zNodeList.size() == 0){
                commonResult = confirm(configBean.getId(),nextVersion.getId());
                continue;
            }

            int queryNumber = 0;
            while (true){
                if(isReady(currentZKData,nextVersion.getZKPath())){
                    break;
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(RETRY_SLEEP_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.interrupted();
                }
                queryNumber ++;
                if(queryNumber >= retryNumber){
                    break;
                }
            }
            commonResult = confirm(configBean.getId(),nextVersion.getId());
        }
        return commonResult;
    }

    private boolean isReady(ZooKeeperDataApi currentZKData,String path){
        List<ZNode> machineNodeList = zooKeeperService.getZNodeListByPath(path);
        if(machineNodeList.size() == 0){
            return true;
        }
        int machineReadyNumber = 0;
        for (ZNode machineNode : machineNodeList) {
            ZooKeeperDataApi childData = JSONObject.parseObject(machineNode.getData().toString(),ZooKeeperDataApi.class);
            if(childData != null && childData.getNext() != null && childData.getNext().equals(currentZKData.getNext())){
                machineReadyNumber ++;
            }
        }
        return machineNodeList.size() == machineReadyNumber;
    }

    private void handleConfirm(List<ConfigBean> currentList,long currentConfigId,ConfigVersion nextConfigVersion,int state,String path){
        ZooKeeperDataApi api = new ZooKeeperDataApi();
        updateVersion(currentConfigId,nextConfigVersion.getDataInfo(),nextConfigVersion.getVersion());
        replaceVersion(currentList,nextConfigVersion);
        api.setCurrent(toListConfigApiBean(currentList));
        api.setNext(null);
        api.setState(state);
        zooKeeperService.updateZNodeData(path,api.toJSONString());
    }

    private void replaceVersion(List<ConfigBean> currentList,ConfigVersion nextVersion){
        for (ConfigBean configBean : currentList){
            if(configBean.getConfigName().equals(nextVersion.getConfigName())){
                configBean.setDataInfo(nextVersion.getDataInfo());
                configBean.setVersion(nextVersion.getVersion());
            }
        }
    }

    public List<ConfigApiBean> toListConfigApiBean(List<ConfigBean> configBeanList){
        List<ConfigApiBean> apiBeanList = Lists.newArrayList();
        for (ConfigBean configBean : configBeanList) {
            ConfigApiBean apiBean = new ConfigApiBean(configBean.getConfigName(),configBean.getVersion());
            apiBeanList.add(apiBean);
        }
        return apiBeanList;
    }

}
