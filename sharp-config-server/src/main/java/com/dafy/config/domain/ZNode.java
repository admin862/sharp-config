package com.dafy.config.domain;

import lombok.Data;

import java.util.List;
import java.util.Random;

/**
 * @author yanfeng
 * @date 2017/4/3 0003.
 */
@Data
public class ZNode {

    private Random random = new Random();

    /** id **/
    private long id = random.nextInt();
    /** 路径 **/
    private String path;
    /** 层级 **/
    private int level;
    /** 数据信息 **/
    private Object data;
    /** 父节点 **/
    private ZNode parent;
    /** 子节点 **/
    private List<ZNode> children;

    public ZNode(String path ,Object data){
        this.path = path;
        this.data = data;
    }

    @Override
    public String toString() {
        return "ZNode{" +
                "path='" + path + '\'' +
                ", data=" + data +
                '}';
    }
}
