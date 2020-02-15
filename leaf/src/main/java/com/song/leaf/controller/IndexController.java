package com.song.leaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/indexController")
public class IndexController {
    @RequestMapping("/indexPage")
    public String index(Map<String, Object> result) {
        result.put("name", "test");
        return "index";
    }
}
