package com.dafy.config.service;

import com.dafy.config.constants.ZooKeeperConstant;
import com.dafy.config.domain.ZNode;
import com.dafy.config.utils.PropertiesUtil;
import com.google.common.collect.Lists;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

/**
 * @author yanfeng
 * @create 2017-04-05 9:55
 **/
@Service
public class ZooKeeperService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZooKeeperService.class);

    private ZkClient zkClient1;

    private ZkClient zkClient2;

    @PostConstruct
    public void init(){
        LOGGER.info("init zookeeper client");
        String zkHost1 = PropertiesUtil.getValue("zk1.host");
        String zkHost2 = PropertiesUtil.getValue("zk2.host");
        int sessionTimeout = Integer.valueOf(PropertiesUtil.getValue("zk.session.timeout"));
        int connectionTimeout = Integer.valueOf(PropertiesUtil.getValue("zk.connection.timeout"));
        zkClient1 = new ZkClient(zkHost1, sessionTimeout, connectionTimeout);
        if(!zkClient1.exists(ZooKeeperConstant.ZK_ROOT_PATH)){
            zkClient1.createPersistent("/config");
        }
        zkClient2 = new ZkClient(zkHost2, sessionTimeout, connectionTimeout);
        if(!zkClient2.exists(ZooKeeperConstant.ZK_ROOT_PATH)){
            zkClient2.createPersistent("/config");
        }
    }

    @PreDestroy
    public void destroy(){
        LOGGER.info("close zookeeper client : zkClient1 and zkClient2");
        zkClient1.close();
        zkClient2.close();
    }

    public void createPersistentZNode(String path){
        if(!zkClient1.exists(path)){
            zkClient1.createPersistent(path);
        }
        if(!zkClient2.exists(path)){
            zkClient2.createPersistent(path);
        }
    }

    /**
     * 根据path获取ZNodeList 特指获取应用的机器节点
     * @param path
     * @return
     */
    public List<ZNode> getZNodeListByPath(String path){
        List<ZNode> resultList = buildZNodeListFromZK1(path);
        if(zkClient2.exists(path)) {
            List<String> listInZK2 = zkClient2.getChildren(path);
            for (String pathInZK : listInZK2) {
                String selfPath = path + "/" + pathInZK;
                Object data = zkClient2.readData(selfPath);
                ZNode zNode = new ZNode(selfPath, data);
                resultList.add(zNode);
            }
        }
        return resultList;
    }

    public List<ZNode> getZNodeListByPathFromZK1(String path){
        List<ZNode> resultList = Lists.newCopyOnWriteArrayList();
        if(zkClient1.exists(path)){
            List<String> listInZK1 = zkClient1.getChildren(path);
            for (String pathInZK : listInZK1) {
                String selfPath = path + "/" + pathInZK;
                Object data = zkClient1.readData(selfPath);
                ZNode zNode = new ZNode(selfPath,data);
                zNode.setLevel(getLevelFromPath(path));
                zNode.setChildren(getZNodeListByPathFromZK1(selfPath));
                resultList.add(zNode);
            }
        }
        return resultList;
    }

    public List<ZNode> getZNodeListByPathFromZK2(String path){
        List<ZNode> resultList = Lists.newCopyOnWriteArrayList();
        if(zkClient2.exists(path)){
            List<String> listInZK1 = zkClient2.getChildren(path);
            for (String pathInZK : listInZK1) {
                String selfPath = path + "/" + pathInZK;
                Object data = zkClient2.readData(selfPath);
                ZNode zNode = new ZNode(selfPath,data);
                zNode.setLevel(getLevelFromPath(path));
                zNode.setChildren(getZNodeListByPathFromZK2(selfPath));
                resultList.add(zNode);
            }
        }
        return resultList;
    }

    private List<ZNode> buildZNodeListFromZK1(String path){
        List<ZNode> resultList = Lists.newCopyOnWriteArrayList();
        if(zkClient1.exists(path)){
            List<String> listInZK1 = zkClient1.getChildren(path);
            for (String pathInZK : listInZK1) {
                String selfPath = path + "/" + pathInZK;
                Object data = zkClient1.readData(selfPath);
                ZNode zNode = new ZNode(selfPath,data);
                zNode.setLevel(getLevelFromPath(path));
                zNode.setChildren(buildZNodeListFromZK1(selfPath));
                resultList.add(zNode);
            }
        }
        return resultList;
    }

    private int getLevelFromPath(String path){
        return  path.split("/").length - 1;
    }

    public boolean deleteZNodeByPath(String path){
        return zkClient1.delete(path) && zkClient2.delete(path);
    }

    public boolean deleteRecursiveByPath(String path){
        return zkClient1.deleteRecursive(path) && zkClient2.deleteRecursive(path);
    }

    public int countChildren(String path){
        return zkClient1.countChildren(path) + zkClient2.countChildren(path);
    }

    public boolean existZNode(String path){
        return zkClient1.exists(path) && zkClient2.exists(path);
    }

    public Object getDataByPath(String path){
        Object result = zkClient1.readData(path);
        return result;
    }

    public void updateZNodeData(String path,String data){
        zkClient1.writeData(path,data);
        zkClient2.writeData(path,data);
    }

    public static void main(String[] args) throws InterruptedException {
        // 阿里云
        String zkHost = "101.201.56.4:2181";
        int sessionTimeout = Integer.valueOf(PropertiesUtil.getValue("zk.session.timeout"));
        int connectionTimeout = Integer.valueOf(PropertiesUtil.getValue("zk.connection.timeout"));
        ZkClient zkClient = new ZkClient(zkHost, sessionTimeout, connectionTimeout);
        boolean a =  zkClient.delete("/config/online/TradeSystem/BillBusiness2/192.168.108.220-43d90887-90e2-4f32-b835-f42f68b19741");
        boolean b =  zkClient.delete("/config/online/TradeSystem/BillBusiness2/192.168.108.221-2a89cf91-2485-4934-90bc-637e327a7754");
        boolean c =  zkClient.delete("/config/online/TradeSystem/BillBusiness2/192.168.108.222-04d4fc5c-af69-4db3-8326-064ac76c545c");

        System.out.println(zkClient.countChildren("/config/online/TradeSystem/BillBusiness2"));
    }

}
