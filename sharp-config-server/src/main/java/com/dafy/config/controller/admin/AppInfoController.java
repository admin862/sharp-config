package com.dafy.config.controller.admin;

import com.alibaba.fastjson.JSON;
import com.dafy.config.controller.BaseController;
import com.dafy.config.domain.AppInfo;
import com.dafy.config.domain.CommonResult;
import com.dafy.config.domain.GroupInfo;
import com.dafy.config.service.AppInfoService;
import com.dafy.config.service.GroupInfoService;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author yanfeng
 * @create 2017-04-06 11:18
 **/
@Controller
@RequestMapping("/admin/appInfo")
public class AppInfoController extends BaseController {

    @Autowired
    private AppInfoService appInfoService;

    @Autowired
    private GroupInfoService groupInfoService;

    @RequestMapping("addUI")
    public ModelAndView addUI(){
        ModelAndView modelAndView = this.getModelAndView();
        List<GroupInfo> groupInfoList = groupInfoService.getAllGroupInfoList();
        modelAndView.addObject("groupInfoList",groupInfoList);
        modelAndView.setViewName("setting/appinfo/add");
        return modelAndView;
    }

    @RequestMapping("add")
    @ResponseBody
    public String add(AppInfo appInfo){
        if(appInfo == null || appInfo.getGroupId() == -1 || Strings.isNullOrEmpty(appInfo.getAppName())){
            return CommonResult.PARAMETER_ERROR;
        }
        GroupInfo groupInfo = groupInfoService.getGroupInfoById(appInfo.getGroupId());
        if(groupInfo == null){
            return "请选择正确的组";
        }
        appInfo.setEnvironmentId(groupInfo.getEnvironmentId());
        appInfo.setEnvironmentPath(groupInfo.getEnvironmentPath());
        appInfo.setEnvironmentName(groupInfo.getEnvironmentName());
        appInfo.setGroupId(groupInfo.getId());
        appInfo.setGroupName(groupInfo.getGroupName());
        if(!appInfoService.save(appInfo)){
           return CommonResult.OPERATE_FAIL;
        }
        return CommonResult.OPERATE_SUCCESS;
    }

    @RequestMapping("list")
    public ModelAndView list(String appName){
        ModelAndView modelAndView = this.getModelAndView();
        modelAndView.setViewName("setting/appinfo/list");
        if(Strings.isNullOrEmpty(appName)){
            return modelAndView;
        }
        modelAndView.addObject("appInfoList",appInfoService.getUndeleteAppInfoListByAppName(appName));
        return modelAndView;
    }

    @RequestMapping("getGroupInfoByEnvId")
    @ResponseBody
    public String getAppInfoByEnvIdAndGroupId(long groupId){
        List<AppInfo> groupInfoList = appInfoService.getAppInfoListByGroupId(groupId);
        return JSON.toJSONString(groupInfoList);
    }

    @RequestMapping("deleteAppById")
    @ResponseBody
    public CommonResult deleteAppById(long id){
        CommonResult commonResult = new CommonResult(0,"成功");
        if(!appInfoService.deleteAppByAppId(id)){
            commonResult.setCode(2);
            commonResult.setMsg("当前路径下仍旧有使用的进程,不能删除!");
        }
        return commonResult;
    }

}
