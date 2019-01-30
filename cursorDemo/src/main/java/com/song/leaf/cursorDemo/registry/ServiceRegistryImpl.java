package com.song.leaf.cursorDemo.registry;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

public class ServiceRegistryImpl implements ServiceRegistry {

    private CuratorFramework curatorFramework = null;
    private final String REGISTRY_ROOT = "/registry";

    {
        curatorFramework = CuratorFrameworkFactory.builder()
                .connectString("172.29.8.67:2181").sessionTimeoutMs(5000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 10))
                .build();
        curatorFramework.start();
    }

    @Override
    public void registryService(String serviceName, String serviceAddr) throws Exception {
        String servicePath = REGISTRY_ROOT + "/" + serviceName;
        if (curatorFramework.checkExists().forPath(servicePath) == null) {
            curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT)
                    .forPath(servicePath, "0".getBytes());
        }

        String addressPath = servicePath + "/" + serviceAddr;
        curatorFramework.create().withMode(CreateMode.EPHEMERAL)
                .forPath(addressPath, "0".getBytes());
        System.out.println(serviceName + "服务注册成功" + serviceAddr);
    }

    public static void main(String[] args) {
        ServiceRegistry serviceRegistry = new ServiceRegistryImpl();
        try {
            serviceRegistry.registryService("risk-service", "172.29.8.67:12001");
            serviceRegistry.registryService("risk-service", "172.29.8.67:12002");
            serviceRegistry.registryService("risk-service", "172.29.8.67:12003");

            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
