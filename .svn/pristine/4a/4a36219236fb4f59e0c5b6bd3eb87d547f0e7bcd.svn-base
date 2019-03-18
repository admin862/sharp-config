package com.dafy.config.domain;

import com.dafy.config.constants.ZooKeeperConstant;
import lombok.Data;

import java.util.Date;

/**
 * 组信息
 * @author yanfeng
 * @create 2017-04-06 11:24
 **/
@Data
public class GroupInfo {
    /** id **/
    private long id;
    /** 所属环境id **/
    private long environmentId;
    /** 所属环境id名称 **/
    private String environmentName;
    /** 所属环境路径 **/
    private String environmentPath;
    /** 名称 **/
    private String groupName;
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
        return stringBuilder.toString();
    }
}
