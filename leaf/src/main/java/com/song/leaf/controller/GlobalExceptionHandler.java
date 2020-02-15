package com.song.leaf.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public Map<String, Object> resultError() {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("errorCode", "500");
        resultMap.put("errorMsg", "系统错误");
        return  resultMap;
    }
}
