package com.song.leaf.cursorDemo.registry;

import org.springframework.util.CollectionUtils;

import java.util.List;

public abstract class LoadBalanceStrategyAbstract implements LoadBalanceStrategy {

    protected abstract String doSelect(List<String> invokers);
    @Override
    public String selectHost(List<String> serviceRepos) {
        // 模版做些判断
        if (CollectionUtils.isEmpty(serviceRepos)) {
            return null;
        }
        if (serviceRepos.size() == 1) {
            return serviceRepos.get(0);
        }
        return doSelect(serviceRepos);
    }
}
