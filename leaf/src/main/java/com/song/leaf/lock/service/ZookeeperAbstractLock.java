package com.song.leaf.lock.service;

import com.song.leaf.lock.Lock;
import org.I0Itec.zkclient.ZkClient;

import java.util.concurrent.CountDownLatch;

/**
 * 重构重复代码
 * 将重复代码交给子类
 */
public abstract class ZookeeperAbstractLock implements Lock {

    private static String ADDR = "192.168.120.131:2181";

    protected ZkClient zkClient = new ZkClient(ADDR);
    protected static final String PATH = "/lock";

    // 信号量，阻塞用户程序
    protected CountDownLatch countDownLatch = null;

    @Override
    public void getLock() {
        if (tryLock()) {
            System.out.println("--------获取锁成成功");
        } else {
            // 等待
            waitLock();
            // 重新获取锁
            getLock();
        }

    }

    abstract void waitLock();
    // 是否获取锁成果
    abstract boolean tryLock();

    @Override
    public void unLock() {
        if (zkClient != null) {
            zkClient.close();
        }
    }
}
