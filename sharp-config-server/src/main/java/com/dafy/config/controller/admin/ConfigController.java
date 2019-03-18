package com.dafy.config.controller.admin;

import com.dafy.config.constants.ZooKeeperConstant;
import com.dafy.config.controller.BaseController;
import com.dafy.config.domain.*;
import com.dafy.config.service.*;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

/**
 * @author yanfeng
 * @create 2017-03-31 18:16
 **/
@Controller("adminConfigController")
@RequestMapping("/admin/config")
public class ConfigController extends BaseController{

    @Autowired
    private EnvironmentService environmentService;

    @Autowired
    private GroupInfoService groupInfoService;

    @Autowired
    private AppInfoService appInfoService;

    @Autowired
    private ZooKeeperService zooKeeperService;

    @Autowired
    private ConfigService configService;

    @Autowired
    private ConfigVersionService configVersionService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private AuditTaskService auditTaskService;

    @RequestMapping("addUI")
    public ModelAndView addUI(long envId){
        ModelAndView modelAndView = this.getModelAndView();
        Environment environment = environmentService.getEnvironmentById(envId);
        List<GroupInfo> groupInfoList = groupInfoService.getGroupInfoListByEnvId(envId);
        modelAndView.setViewName("config/add");
        modelAndView.addObject("environment",environment);
        modelAndView.addObject("groupInfoList",groupInfoList);
        return modelAndView;
    }

    @RequestMapping("add")
    @ResponseBody
    public String add(ConfigBean configBean){
        if(configBean == null || Strings.isNullOrEmpty(configBean.getConfigName())){
            return CommonResult.PARAMETER_ERROR;
        }
        AppInfo appInfo = appInfoService.getAppInfoById(configBean.getAppId());
        if(appInfo == null){
            return CommonResult.PARAMETER_ERROR;
        }
        configBean.setConfigName(configBean.getConfigName().trim());
        configBean.setEnvironmentId(appInfo.getEnvironmentId());
        configBean.setEnvironmentName(appInfo.getEnvironmentName());
        configBean.setEnvironmentPath(appInfo.getEnvironmentPath());
        configBean.setGroupId(appInfo.getGroupId());
        configBean.setGroupName(appInfo.getGroupName());
        configBean.setAppName(appInfo.getAppName());
        configBean.setVersion(1);
        if(!configService.save(configBean)){
            return CommonResult.OPERATE_FAIL;
        }
        return CommonResult.OPERATE_SUCCESS;
    }

    @RequestMapping("updateUI")
    public ModelAndView updateUI(long appId,String configName,Principal principal){
        ConfigBean currentVersion = configService.getConfigByAppIdConfigName(appId,configName);
        ModelAndView modelAndView = this.getModelAndView();
        modelAndView.setViewName("config/update");
        if(!isGrantAuth(principal.getName(),currentVersion.getGroupId())){
            modelAndView.addObject("hasAuth",0);
            return modelAndView;
        }
        modelAndView.addObject("hasAuth",1);

        List<ConfigVersion> configVersionList = configVersionService.getConfigVersionByAppIdAndConfigName(currentVersion.getAppId(),currentVersion.getConfigName());
        String path = ZooKeeperConstant.ZK_ROOT_PATH + "/"+currentVersion.getEnvironmentPath()+"/" + currentVersion.getGroupName() + "/" + currentVersion.getAppName() ;
        modelAndView.addObject("appZKData",zooKeeperService.getDataByPath(path));
        modelAndView.addObject("currentConfig",currentVersion);
        modelAndView.addObject("configVersionList",configVersionList);
        modelAndView.addObject("auditList",auditTaskService.getAuditTaskListBy(currentVersion.getId()));
        modelAndView.setViewName("config/update");
        return modelAndView;
    }

    @RequestMapping("addNewVersionUI")
    public ModelAndView addNewVersionUI(long configVersionId){
        ModelAndView modelAndView = this.getModelAndView();
        modelAndView.setViewName("config/addVersion");
        ConfigVersion configVersion = configVersionService.getConfigVersionById(configVersionId);
        ConfigBean configBean = configService.getConfigByAppIdConfigName(configVersion.getAppId(),configVersion.getConfigName());
        modelAndView.addObject("configVersion",configVersion);
        modelAndView.addObject("currentConfig",configBean);
        return modelAndView;
    }

    @RequestMapping("addversion")
    @ResponseBody
    public String addVersion(long currentConfigId,String dataInfo,String remark,Principal principal){
        ConfigBean currentVersion = configService.getConfigById(currentConfigId);
        if(!isGrantAuth(principal.getName(),currentVersion.getGroupId())){
           return "无操作权限";
        }
        ConfigVersion maxConfigVersion = configVersionService.getMaxConfigVersionByAppIdAndConfigName(currentVersion.getAppId(),currentVersion.getConfigName());
        maxConfigVersion.setVersion(maxConfigVersion.getVersion() + 1);
        maxConfigVersion.setDataInfo(dataInfo);
        maxConfigVersion.setRemark(remark);
        configVersionService.save(maxConfigVersion);
        return CommonResult.OPERATE_SUCCESS;
    }

    @RequestMapping("{environmentPath}")
    public ModelAndView list(@PathVariable("environmentPath") String environmentPath,String appName){
        ModelAndView modelAndView = this.getModelAndView();
        Environment environment = environmentService.getEnvironmentByPath(environmentPath);
        List<ConfigBean> configBeanList;
        if(Strings.isNullOrEmpty(appName)){
            configBeanList = configService.getConfigListByEnvironmentPath(environmentPath);
        } else {
            configBeanList = configService.getConfigByEnvIdAndAppName(environment.getId(),appName);
        }
        modelAndView.addObject("environment",environment);
        modelAndView.addObject("configBeanList",configBeanList);
        modelAndView.setViewName("config/list");
        return modelAndView;
    }

    @RequestMapping("audit")
    @ResponseBody
    public String audit(long currentConfigId,long prepareVersionId,String data,Principal principal){
        ConfigBean configBean = configService.getConfigById(currentConfigId);
        if(!isGrantAuth(principal.getName(),configBean.getGroupId())){
            return "无操作权限";
        }
        ConfigVersion prepareVersion = configVersionService.getConfigVersionById(prepareVersionId);
        if(configBean.getVersion() == prepareVersion.getVersion()){
            return "当前版本已经是 " + configBean.getVersion();
        }
        if(auditTaskService.getAuditTaskListBy(currentConfigId).size() >= 1){
            return "该配置文件已提交审核!";
        }

        ConfigVersion currentVersion = configVersionService.getConfigVersionBy(configBean.getEnvironmentPath(),configBean.getGroupName(),configBean.getAppName(),configBean.getConfigName(),configBean.getVersion());

        AuditTask auditTask = new AuditTask();
        auditTask.setConfigId(currentConfigId);
        auditTask.setFromVersion(currentVersion.getVersion());
        auditTask.setToVersion(prepareVersion.getVersion());
        auditTask.setFromConfigId(currentVersion.getId());
        auditTask.setToConfigId(prepareVersionId);
        auditTask.setState(AuditTask.AuditState.WAIT_AUDIT.getValue());
        auditTask.setSubmitUsername(principal.getName());
        auditTask.setEnvName(currentVersion.getEnvironmentName());
        auditTask.setGroupName(currentVersion.getGroupName());
        auditTask.setAppName(currentVersion.getAppName());
        auditTask.setConfigName(currentVersion.getConfigName());
        auditTask.setRemark(data);
        auditTaskService.save(auditTask);
        return "OK";
    }

    @RequestMapping("cancelAudit")
    @ResponseBody
    public String cancelAudit(long id,long currentConfigId,Principal principal){
        ConfigBean currentVersion = configService.getConfigById(currentConfigId);
        if(!isGrantAuth(principal.getName(),currentVersion.getGroupId())){
            return "无操作权限";
        }
        auditTaskService.processState(id,AuditTask.AuditState.AUDIT_FAIL,null);
        return "OK";
    }

    @RequestMapping("prepare")
    @ResponseBody
    public String prepare(long currentConfigId,long prepareVersionId,Principal principal){
        ConfigBean currentVersion = configService.getConfigById(currentConfigId);
        if(!isGrantAuth(principal.getName(),currentVersion.getGroupId())){
            return "无操作权限";
        }
        ConfigVersion prepareVersion = configVersionService.getConfigVersionById(prepareVersionId);
        if(currentVersion.getVersion() == prepareVersion.getVersion()){
            return "当前版本已经是 " + currentVersion.getVersion();
        }
        if(groupInfoService.isInWhiteList(currentVersion.getGroupId())){
            configService.prepare(prepareVersion);
        } else {
            if(principal == null || !principal.getName().equals("admin")){
                AuditTask auditTask = auditTaskService.getAuditTaskBy(currentConfigId,currentVersion.getVersion(),prepareVersion.getVersion());
                if(auditTask == null || auditTask.getState() != AuditTask.AuditState.AUDIT_SUCCESS.getValue()){
                    return "审核还未通过!";
                }
            }
            configService.prepare(prepareVersion);
        }
        return CommonResult.OPERATE_SUCCESS;
    }

    @RequestMapping("publish")
    @ResponseBody
    public String publish(long currentConfigId,long prepareVersionId,Principal principal){
        ConfigBean currentVersion = configService.getConfigById(currentConfigId);
        if(!isGrantAuth(principal.getName(),currentVersion.getGroupId())){
            return "无操作权限";
        }
        return configService.confirm(currentConfigId,prepareVersionId).getMsg();
    }

    @RequestMapping("deleteVersionById")
    @ResponseBody
    public CommonResult deleteVersion(long currentConfigId,long configVersionId,Principal principal){
        ConfigBean configBean = configService.getConfigById(currentConfigId);
        if(!isGrantAuth(principal.getName(),configBean.getGroupId())){
            return new CommonResult(1,"无操作权限");
        }
        ConfigVersion configVersion = configVersionService.getConfigVersionById(configVersionId);
        if(configBean.getVersion() == configVersion.getVersion()){
            return new CommonResult(1,"当前版本正在使用,不能删除!");
        }
        CommonResult commonResult = new CommonResult(0,CommonResult.OPERATE_SUCCESS);
        if(!configVersionService.deleteVersionById(configVersionId)){
            commonResult.setCode(1);
            commonResult.setMsg(CommonResult.OPERATE_FAIL);
        }
        return commonResult;
    }

    @RequestMapping("deleteConfigById")
    @ResponseBody
    public CommonResult deleteConfigById(long id,Principal principal){
        CommonResult commonResult = new CommonResult(0,"成功");
        ConfigBean configBean = configService.getConfigById(id);
        if(!isGrantAuth(principal.getName(),configBean.getGroupId())){
            return new CommonResult(2,"无操作权限");
        }
        if(!configService.deleteConfigById(id)){
            commonResult.setCode(2);
            commonResult.setMsg("当前路径下仍旧有使用的进程,不能删除!");
        }
        return commonResult;
    }

    private boolean isGrantAuth(String username,long groupId){
        if(username == null){
            return false;
        }
        User user = userRoleService.getUserByUsername(username);
        if(user != null && user.getRoles().size() >  0){
            for (Role role : user.getRoles()){
                if(role.getRolename().equals("ROLE_ADMIN")){
                    return true;
                }
            }
        }
        for(GroupInfo group : user.getGroups()){
            if(group.getId() == groupId){
                return true;
            }
        }
        return false;
    }

}
