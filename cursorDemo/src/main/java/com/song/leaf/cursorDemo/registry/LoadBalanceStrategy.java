package com.song.leaf.cursorDemo.registry;

import java.util.List;

public interface LoadBalanceStrategy {
    String selectHost(List<String> serviceRepos);
}
