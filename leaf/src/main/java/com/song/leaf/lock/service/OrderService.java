package com.song.leaf.lock.service;

import com.song.leaf.lock.Lock;
import com.song.leaf.lock.OrderNumGenerator;

/**
 * 订单生成调用的业务逻辑
 */
public class OrderService implements Runnable {


    private Lock lock = new ZookeeperDistributeLock();
    @Override
    public void run() {
        try {
            lock.getLock();
            // 模拟用户生成订单号
            OrderService.getNumber();
        } finally {
            lock.unLock();
        }
    }

    public static void  getNumber() {
        String number = OrderNumGenerator.getNumber();
        System.out.println(Thread.currentThread().getName() + "--生成唯一订单号:" + number);
    }

    public static void main(String[] args) {
        System.out.println("模拟生成订单号开始...");
        // OrderService orderService = new OrderService();
        for (int i = 0; i < 100 ; i++) {
            new Thread(new OrderService()).start();
        }
    }
}
