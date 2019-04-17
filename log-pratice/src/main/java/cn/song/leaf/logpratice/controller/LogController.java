package cn.song.leaf.logpratice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LogController {

    private static final Logger logger = LoggerFactory.getLogger("LogController");

    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        logger.info("This is a test {}", "Albert");
        logger.error("测试!");
        return "123";
    }
}
