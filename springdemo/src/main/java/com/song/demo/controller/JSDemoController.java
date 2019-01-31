package com.song.demo.controller;

import com.song.demo.service.JSDemoService;
import com.song.framework.annotation.JSAutowired;
import com.song.framework.annotation.JSController;
import com.song.framework.annotation.JSRequestMapping;
import com.song.framework.annotation.JSRequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@JSController
@JSRequestMapping("/demo")
public class JSDemoController {

    @JSAutowired
    public JSDemoService jSDemoService;

    @JSRequestMapping("/query")
    public void query(HttpServletRequest req, HttpServletResponse resp, @JSRequestParam("name") String key) {
        String result = jSDemoService.get(key);
        try {
            resp.getWriter().write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
