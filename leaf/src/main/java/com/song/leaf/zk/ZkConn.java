package com.song.leaf.zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

// 封装zk链接
public class ZkConn implements Watcher {
    private static String ADDR = "192.168.120.131:2181";
    private static int session_out_time = 2000;
    ZooKeeper zk;

    // 信号量，阻塞用户程序
    private static final CountDownLatch countDownLatch = new CountDownLatch(1);

    /**
     * ZK创建链接
     */
    public void createConnection(String addr, int sessionOutTime){
        try {
            zk = new ZooKeeper(ADDR, session_out_time, this);
            countDownLatch.await();
            System.out.println("--ZK创建链接--");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建持久化节点
     * @param path
     * @param data
     * @return
     */
    public boolean createNode(String path, String data) {
        try {
            Stat s = exists(path, true);
            if (s != null){
                System.out.println("节点" + path + "已经存在!");
            } else {
                String node = zk.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                System.out.println("新增节点" + path + "成功, data:" + data);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 事件通知
     * @param watchedEvent
     */
    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println("---BEGIN:监听事件----");
        // 1. 获取事件的状态
        Event.KeeperState state =  watchedEvent.getState();
        // 获取节点
        String path = watchedEvent.getPath();
        // 3. 获取事件类型
        Event.EventType eventType = watchedEvent.getType();

        System.out.println("###进入方法###");
        System.out.println("###state###" + state);
        System.out.println("###path###" + path);
        System.out.println("###eventType###" + eventType);
        if (state == Event.KeeperState.SyncConnected) {
            // 同步链接, 建立链接
            if (eventType == Event.EventType.None) {
                // 完成一个子线程
                countDownLatch.countDown();
                System.out.println(" ----------------- zk开始启动链接 ---------------- ");
            } else if (eventType == Event.EventType.NodeCreated) {
                System.out.println(" ----------------- 获取事件通知,节点[" + path + "]创建成功 ---------------- ");
            } else if (eventType == Event.EventType.NodeDataChanged) {
                System.out.println(" ----------------- 获取事件通知,节点[" + path + "]数据修改 ---------------- ");
            }
        }
        System.out.println("---EDN:监听事件----");
    }

    public void close() {
        if (zk != null) {
            try {
                zk.close();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 创建节点前，判断是否要通知
     * @param path
     * @param isWatch
     * @return
     */
    public Stat exists(String path, boolean isWatch) {
        try {
            return zk.exists(path, isWatch);
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {
        ZkConn zkConn = new ZkConn();
        zkConn.createConnection(ADDR, session_out_time);
        zkConn.createNode("/song", "002");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        zkConn.close();
    }
}
