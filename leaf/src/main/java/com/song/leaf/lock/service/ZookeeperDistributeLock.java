package com.song.leaf.lock.service;

import org.I0Itec.zkclient.IZkDataListener;

import java.util.concurrent.CountDownLatch;

public class ZookeeperDistributeLock extends ZookeeperAbstractLock {
    @Override
    void waitLock() {
        // 使用事件的监听，获取节点被删除的时候，重新唤醒
        IZkDataListener iZkDataListener = new IZkDataListener() {
            // 当节点发生改变
            @Override
            public void handleDataChange(String s, Object o) throws Exception {

            }

            // 当节点被删除
            @Override
            public void handleDataDeleted(String s) throws Exception {
                if (countDownLatch!= null) {
                    countDownLatch.countDown();
                }
            }
        };

        // 订阅监听
        zkClient.subscribeDataChanges(PATH, iZkDataListener);
        if (zkClient.exists(PATH)) {
            System.out.println(Thread.currentThread().getName() + "锁住");
            // 创建信号量
            countDownLatch = new CountDownLatch(1);
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 取消订阅
        zkClient.unsubscribeDataChanges(PATH, iZkDataListener);
    }

    @Override
    boolean tryLock() {
        try {
            zkClient.createEphemeral(PATH);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
