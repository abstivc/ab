package com.song.api;

import com.song.base.ResponseBase;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RequestMapping("/member")
public interface TestApiService {

    @RequestMapping("/test")
    Map<String, Object> test(Integer id, String name);

    @RequestMapping("testRes")
    public ResponseBase testRes();

    @RequestMapping("setTestRedis")
    ResponseBase setTestRedis(String key, String value);

    @RequestMapping("getTestRedis")
    ResponseBase getTestRedis(String key);
}
