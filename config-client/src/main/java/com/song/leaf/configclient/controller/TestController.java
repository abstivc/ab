package com.song.leaf.configclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Value("${user.name}")
    String name;
    @Value("${user.age}")
    String age;

    @GetMapping("/getName")
    public String getName() {
        return name + age;
    }
}
