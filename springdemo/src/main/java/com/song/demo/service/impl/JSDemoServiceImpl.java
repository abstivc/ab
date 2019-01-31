package com.song.demo.service.impl;

import com.song.demo.service.JSDemoService;
import com.song.framework.annotation.JSService;

@JSService("jSDemoService")
public class JSDemoServiceImpl implements JSDemoService {
    @Override
    public String get(String key) {
        return "name is: " + key;
    }
}
