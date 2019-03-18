package com.dafy.config.controller.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.dafy.config.controller.BaseController;
import com.dafy.config.domain.CommonResult;
import com.dafy.config.domain.Environment;
import com.dafy.config.domain.GroupInfo;
import com.dafy.config.service.EnvironmentService;
import com.dafy.config.service.GroupInfoService;
import com.google.common.base.Strings;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.LinkedList;
import java.util.List;

/**
 * @author yanfeng
 * @create 2017-04-06 11:18
 **/
@Controller
@RequestMapping("/admin/groupInfo")
public class GroupInfoController extends BaseController {

    @Autowired
    private EnvironmentService environmentService;

    @Autowired
    private GroupInfoService groupService;

    @RequestMapping("addUI")
    public ModelAndView addUI(){
        ModelAndView modelAndView = this.getModelAndView();
        modelAndView.setViewName("setting/groupInfo/add");
        return modelAndView;
    }

    @RequestMapping(value = "add",method = {RequestMethod.POST})
    @ResponseBody
    public String add(GroupInfo groupInfo){
        Environment environment = environmentService.getEnvironmentById(groupInfo.getEnvironmentId());
        groupInfo.setEnvironmentName(environment.getName());
        groupInfo.setEnvironmentPath(environment.getPath());
        if(!groupService.save(groupInfo)){
          return CommonResult.OPERATE_FAIL;
        }
        return CommonResult.OPERATE_SUCCESS;
    }

    @RequestMapping("list")
    public ModelAndView list(String groupName){
        ModelAndView modelAndView = this.getModelAndView();
        modelAndView.setViewName("setting/groupInfo/list");
        List<GroupInfo> groupInfoList;
        if(Strings.isNullOrEmpty(groupName)){
            groupInfoList = groupService.getUndeleteAllGroupInfoList();
        } else {
            groupInfoList = groupService.getUndeleteGroupInfoListByGroupName(groupName);
        }
        modelAndView.addObject("groupInfoList",groupInfoList);
        return modelAndView;
    }

    @RequestMapping("getGroupInfoByEnvId")
    @ResponseBody
    public String getGroupInfoByEnvId(long envId){
        List<GroupInfo> groupInfoList = groupService.getGroupInfoListByEnvId(envId);
        return JSON.toJSONString(groupInfoList);
    }

    @RequestMapping("deleteGroupById")
    @ResponseBody
    public CommonResult deleteGroupById(long id){
        CommonResult commonResult = new CommonResult(0,"成功");
        if(!groupService.deleteGroupById(id)){
            commonResult.setCode(2);
            commonResult.setMsg("当前路径下仍旧有使用的进程,不能删除!");
        }
        return commonResult;
    }

}
