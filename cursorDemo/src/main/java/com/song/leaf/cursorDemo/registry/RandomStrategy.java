package com.song.leaf.cursorDemo.registry;

import java.util.List;
import java.util.Random;

public class RandomStrategy extends LoadBalanceStrategyAbstract {
    @Override
    protected String doSelect(List<String> invokers) {
        int length = invokers.size();
        Random random = new Random();
        return invokers.get(random.nextInt(length));
    }
}
