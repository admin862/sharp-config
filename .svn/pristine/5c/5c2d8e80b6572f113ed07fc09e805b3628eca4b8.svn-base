package com.dafy.config.domain;

import com.dafy.config.constants.ZooKeeperConstant;
import lombok.Data;

import java.util.Date;

/**
 * App信息
 * @author yanfeng
 * @create 2017-04-06 11:27
 **/
@Data
public class AppInfo {
    /** id **/
    private long id;
    /** 所属环境id **/
    private long environmentId;
    /** 所属环境id名称 **/
    private String environmentName;
    /** 所属环境路径 **/
    private String environmentPath;
    /** 组id**/
    private long groupId;
    /** 所属组 **/
    private GroupInfo groupInfo;
    /** 组名称 **/
    private String groupName;
    /** App名称 **/
    private String appName;
    /**　软删除标示 0-未删除 1-已删除 **/
    private int isDelete;
    /** 备注 **/
    private String remark;
    /** 创建时间 **/
    private Date createTime;
    /** 修改时间 **/
    private Date modifyTime;

    public String getZKPath(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ZooKeeperConstant.ZK_ROOT_PATH);
        stringBuilder.append("/");
        stringBuilder.append(this.environmentPath);
        stringBuilder.append("/");
        stringBuilder.append(this.groupName);
        stringBuilder.append("/");
        stringBuilder.append(this.appName);
        return stringBuilder.toString();
    }

}
