package com.song.mq;

import com.alibaba.fastjson.JSONObject;
import com.song.adapter.MessageAdapter;
import com.song.constants.Constants;
import com.song.email.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Slf4j
public class ConsumerDistribute {
    @Autowired
    private MailService mailService;

    @Autowired
    private MessageAdapter messageAdapter;
    // 监听消息
    @JmsListener(destination = "messages_queue")
    public void distribute(String json) {
        log.info("----------------- 消费服务平台接收消息内容:" + json);
        if (StringUtils.isEmpty(json)) {
            return;
        }
        JSONObject jsonObject = new JSONObject().parseObject(json);
        JSONObject header = jsonObject.getJSONObject("header");
        String interfaceType = header.getString("interfaceType");

        if (StringUtils.isEmpty(interfaceType)) {
            return;
        }
        if (interfaceType.equals(Constants.SMS_MAIL)) {
            messageAdapter = mailService;
        }
        if (messageAdapter == null) {
            return;
        }
        JSONObject body = jsonObject.getJSONObject("content");
        messageAdapter.sendMsg(body);
    }
}
