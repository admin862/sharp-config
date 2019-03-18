package com.dafy.config.controller.api;

import com.dafy.config.domain.CommonResult;
import com.dafy.config.domain.ConfigBean;
import com.dafy.config.domain.ConfigVersion;
import com.dafy.config.service.ConfigService;
import com.dafy.config.service.ConfigVersionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yanfeng
 * @create 2017-03-31 18:16
 **/
@RestController
@RequestMapping("/api/config")
public class ConfigController {

    @Autowired
    private ConfigVersionService configVersionService;

    @Autowired
    private ConfigService configService;

    @RequestMapping(value = "{environmentPath}/{groupName}/{appName}/{configName}/{version}",method = {RequestMethod.GET})
    public String getConfig(@PathVariable("environmentPath") String environmentPath,@PathVariable("groupName") String groupName,@PathVariable("appName") String appName,@PathVariable("configName") String configName, @PathVariable("version") Integer version){
        ConfigVersion configVersion = configVersionService.getConfigVersionBy(environmentPath,groupName,appName,configName,version);
        String result = configVersion.getDataInfo();
        return result;
    }

    @RequestMapping(value = "prepareAndConfirm",method = RequestMethod.POST)
    public CommonResult prepareAndConfirm(String envPath,String groupName,String appName,String configName,int prepareVersion){
        CommonResult commonResult = new CommonResult(-1,"fail");
        ConfigBean currentVersion = configService.getCurrentConfigByName(envPath,groupName,appName,configName);
        if(currentVersion == null){
            commonResult.setMsg("服务端查不到改配置");
            return commonResult;
        }
        if(currentVersion.getVersion() == prepareVersion){
            commonResult.setCode(10);
            commonResult.setMsg("当前版本已经是"+prepareVersion);
            return commonResult;
        }
        ConfigVersion nextVersion = configVersionService.getConfigVersionBy(envPath,groupName,appName,configName,prepareVersion);
        if(nextVersion == null){
            commonResult.setMsg("查不到该版本!");
            return commonResult;
        }
        return configService.prepareAndConfirm(currentVersion,nextVersion,100);
    }

    @RequestMapping(value = "batchPrepareConfirmByGroup",method = RequestMethod.POST)
    public CommonResult batchPrepareConfirmByGroup(String envPath,String groupName,String configName){
        CommonResult commonResult = new CommonResult(0,CommonResult.OPERATE_SUCCESS);
        List<ConfigBean> configBeanList = configService.getConfigListByEnvGroupConfig(envPath,groupName,configName);
        if(configBeanList.size() == 0){
            commonResult.setCode(1);
            commonResult.setMsg("没有找到相应的配置文件!");
            return commonResult;
        }
        if(!configVersionService.canPrepareConfirmByGroup(configBeanList)){
            commonResult.setCode(2);
            commonResult.setMsg("改组下的某个配置文件只有一个版本,请确保每个配置文件至少有两个版本!");
            return commonResult;
        }
        List<ConfigVersion> nextVersionList = configVersionService.getNextVersionList(configBeanList);

         return configService.batchPrepareAndConfirm(nextVersionList,100);
    }

    @RequestMapping(value = "addVersion",method = RequestMethod.POST)
    public CommonResult add(String envPath,String groupName,String appName,String configName,String dataInfo,String remark){
        CommonResult commonResult = new CommonResult(-1,"fail");
        ConfigBean currentVersion = configService.getCurrentConfigByName(envPath,groupName,appName,configName);
        if(currentVersion == null){
            commonResult.setMsg("服务端查不到改配置");
            return commonResult;
        }
        ConfigVersion maxConfigVersion = configVersionService.getMaxConfigVersionByAppIdAndConfigName(currentVersion.getAppId(),currentVersion.getConfigName());
        maxConfigVersion.setVersion(maxConfigVersion.getVersion() + 1);
        maxConfigVersion.setDataInfo(dataInfo);
        maxConfigVersion.setRemark(remark);
        if(configVersionService.save(maxConfigVersion)){
            commonResult.setCode(0);
            commonResult.setMsg("添加成功");
            commonResult.setData(maxConfigVersion.getVersion());
        }
        return commonResult;
    }

    @RequestMapping(value = "getCurrentConfigBy" , method = RequestMethod.GET)
    public CommonResult getCurrentConfigBy(String envPath,String groupName,String appName,String configName){
        CommonResult commonResult = CommonResult.newResult(-1,"fail");
        ConfigBean currentVersion = configService.getCurrentConfigByName(envPath,groupName,appName,configName);
        if(currentVersion == null){
            commonResult.setMsg("服务端查不到改配置");
            return commonResult;
        }
        commonResult.setCode(0);
        commonResult.setData(currentVersion);
        return commonResult;
    }

    @RequestMapping(value = "getConfigListBy" , method = RequestMethod.GET)
    public CommonResult getConfigListBy(String envPath,String groupName,String appName,String configName){
        CommonResult commonResult = CommonResult.newResult(-1,"fail");
        List<ConfigVersion> result = configVersionService.getConfigVersionBy(envPath,groupName,appName,configName);
        if(result == null){
            commonResult.setMsg("服务端查不到改配置");
            return commonResult;
        }
        commonResult.setCode(0);
        commonResult.setData(result);
        return commonResult;
    }

}
