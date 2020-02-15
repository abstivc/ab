package com.song.leaf.controller;

import com.song.leaf.bean.User;
import com.song.leaf.dao01.UserDao1;
import com.song.leaf.dao02.UserDao2;
import com.song.leaf.producer.Producer;
import com.song.leaf.util.RedisUtil;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/testController")
public class TestController {
    @Autowired
    UserDao1 userDao1;

    @Autowired
    UserDao2 userDao2;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    Producer producer;

    @RequestMapping("/t01")
    @ResponseBody
    public String t01(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setStatus(302);
        resp.setHeader("Location","/testController/t02");
        return "t01";
    }

    @RequestMapping("/t02")
    @ResponseBody
    public String t02(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (redisUtil.get("ass") == null) {
            redisUtil.set("ass", "2");
            return "1";
        } else {
            redisUtil.set("ass", "a");
            return redisUtil.get("ass").toString();
        }
    }

    @RequestMapping("/getUser")
    @ResponseBody
    public String getUSer(String id) {
        try {
            User user1 = userDao1.selectByPrimaryKey("1");
            User user2 = userDao2.selectByPrimaryKey("1");
            return user1.getName() + user2.getName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @RequestMapping("/pubMsg")
    @ResponseBody
    public String pushMsg(String msg) {
        try {
            Destination destination = new ActiveMQQueue("mytest.queue");
            producer.sendMessage(destination, msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "发布成功";
    }
}
