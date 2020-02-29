package com.song.email.service;

import com.alibaba.fastjson.JSONObject;
import com.song.adapter.MessageAdapter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 处理第三方发送邮件
 */
@Slf4j
@Service
public class MailService implements MessageAdapter {
    @Override
    public void sendMsg(JSONObject body) {
        // 处理发送邮件
        String email=body.getString("email");
        if(StringUtils.isEmpty(email)){
            return ;
        }
        log.info("消息服务平台发送邮件:{}",email);
    }
}
