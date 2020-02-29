package com.leaf.springbootdubboserver.service;


import com.alibaba.dubbo.config.annotation.Service;

@Service
public class DemoApiImpl implements DemoApi {
    public String sayHello(String name) {
        return "Hello," + name + " from dubbo Demo2222";
    }
}
