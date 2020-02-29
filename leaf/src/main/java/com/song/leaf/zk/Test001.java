package com.song.leaf.zk;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class Test001 {
    private static String ADDR = "192.168.120.131:2181";
    private static int session_out_time = 2000;

    // 信号量，阻塞用户程序
    private static final CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        ZooKeeper zk = new ZooKeeper(ADDR, session_out_time, new Watcher() {
            // 事件通知
            @Override
            public void process(WatchedEvent watchedEvent) {
                // 1. 获取事件的状态
                Event.KeeperState state =  watchedEvent.getState();
                if (state == Event.KeeperState.SyncConnected) {
                    // 同步链接, 建立链接
                    // 3. 获取事件类型
                    Event.EventType eventType = watchedEvent.getType();
                    if (eventType == Event.EventType.None) {
                        // 完成一个子线程
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        countDownLatch.countDown();
                        System.out.println(" ----------------- zk开始启动链接 ---------------- ");
                    }
                }
            }
        });
        countDownLatch.await();
        // 往 zookeeper 创建节点信息
        // 创建持久类型，节点开放权限
        String node = zk.create("/song1", "lasting".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("新增节点:" + node);

        zk.close();
    }
}
