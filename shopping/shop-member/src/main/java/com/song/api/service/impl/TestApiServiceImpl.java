package com.song.api.service.impl;

import com.song.api.TestApiService;
import com.song.base.BaseApiController;
import com.song.base.BaseRedisService;
import com.song.base.ResponseBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TestApiServiceImpl extends BaseApiController implements TestApiService {

    @Autowired
    public BaseRedisService baseRedisService;

    @Override
    public Map<String, Object> test(Integer id, String name) {
        Map<String, Object> result = new HashMap<>();
        result.put("respCode", "200");
        result.put("respDesc", "success");
        result.put("data", "id=" + id + " name=" + name);
        return result;
    }

    @Override
    public ResponseBase testRes() {
        return setResultSuccess();
    }

    @Override
    public ResponseBase setTestRedis(String key, String value) {
        baseRedisService.set(key, value);
        return setResultSuccess();
    }

    @Override
    public ResponseBase getTestRedis(String key) {
        return setResultSuccess(baseRedisService.get(key));
    }
}
