package com.song.leaf.cursorDemo.registry;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.ArrayList;
import java.util.List;

public class ServiceDiscovery {
    private CuratorFramework curatorFramework = null;
    private final String REGISTRY_ROOT = "/registry";

    {
        curatorFramework = CuratorFrameworkFactory.builder()
                .connectString("172.29.8.67:2181").sessionTimeoutMs(5000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 10))
                .build();
        curatorFramework.start();
    }

    List<String> serviceRepos = new ArrayList<>();

    public void init(String serviceName) {
        String path = REGISTRY_ROOT + "/" + serviceName;
        try {
            serviceRepos = curatorFramework.getChildren().forPath(path);
            registryWatch(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void registryWatch(String path) throws Exception {
        PathChildrenCache pathChildrenCache = new PathChildrenCache(curatorFramework, path, true);
        PathChildrenCacheListener pathChildrenCacheListener = new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                serviceRepos = curatorFramework.getChildren().forPath(path);
            }
        };
        pathChildrenCache.getListenable().addListener(pathChildrenCacheListener);
        pathChildrenCache.start();
    }

    public String getServiceEndPoint() {
        return new RandomStrategy().doSelect(serviceRepos);
    }

    public static void main(String[] args) {
        ServiceDiscovery serviceDiscovery = new ServiceDiscovery();
        serviceDiscovery.init("risk-service");
        while (true) {
            System.err.println(serviceDiscovery.getServiceEndPoint());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
