package com.dafy.config.controller.admin;

import com.alibaba.fastjson.JSON;
import com.dafy.config.constants.ZooKeeperConstant;
import com.dafy.config.controller.BaseController;
import com.dafy.config.domain.CommonResult;
import com.dafy.config.service.ZooKeeperService;
import com.dafy.config.domain.ZNode;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.mysql.jdbc.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author yanfeng
 * @date 2017/4/3 22:29.
 */
@Controller
@RequestMapping("/admin/zookeeper")
public class ZooKeeperController extends BaseController {

    @Autowired
    private ZooKeeperService zooKeeperService;

    @RequestMapping("show")
    public ModelAndView show(){
        ModelAndView modelAndView = this.getModelAndView();
        modelAndView.setViewName("zookeeper/treeview/list");
        return modelAndView;
    }

    @RequestMapping("getTreeList")
    @ResponseBody
    public String getTreeList(final String path){
        if(StringUtils.isNullOrEmpty(path)){
            return "";
        }
        return JSON.toJSONString(zooKeeperService.getZNodeListByPath(path));
    }

    @RequestMapping("getDataByPath")
    @ResponseBody
    public String getDataByPath(String path){
        Object result = zooKeeperService.getDataByPath(path);
        return JSON.toJSONString(result);
    }

    @RequestMapping("getChildDataByPath")
    @ResponseBody
    public String getChildDataByPath(String parentPath){
        Object resultFromZK1 = zooKeeperService.getZNodeListByPathFromZK1(parentPath);
        Object resultFromZK2 = zooKeeperService.getZNodeListByPathFromZK2(parentPath);
        return "resultFromZK1:"+JSON.toJSONString(resultFromZK1) + "\n  resultFromZK2:" + resultFromZK2;
    }

    @RequestMapping("getDataView")
    public ModelAndView getDataView(){
        ModelAndView modelAndView = this.getModelAndView();
        modelAndView.setViewName("zookeeper/manage/view");
        return modelAndView;
    }

    @RequestMapping("updateZKData")
    @ResponseBody
    public String updateZKData(String path,String zookeeperData){
        if(StringUtils.isNullOrEmpty(path)){
            return CommonResult.PARAMETER_ERROR;
        }
        if(zookeeperData == null){
            zookeeperData = "";
        }
        if(!zooKeeperService.existZNode(path)){
            return "路径不存在节点";
        }
        zooKeeperService.updateZNodeData(path,zookeeperData);
        return CommonResult.OPERATE_SUCCESS;
    }

    @RequestMapping("deleteZKData")
    @ResponseBody
    public String deleteZKData(String path){
        if(StringUtils.isNullOrEmpty(path)){
            return CommonResult.PARAMETER_ERROR;
        }
        if(!zooKeeperService.existZNode(path)){
            return "路径不存在节点";
        }
        zooKeeperService.deleteZNodeByPath(path);
        return CommonResult.OPERATE_SUCCESS;
    }

}
