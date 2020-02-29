package com.song.leaf.lock;

/**
 * 自定义分布式锁
 */
public interface Lock {
    //获取锁
    void getLock();

    //释放锁
    void unLock();

}
