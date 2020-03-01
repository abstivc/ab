package com.song.email.service;

import com.alibaba.fastjson.JSONObject;
import com.song.adapter.MessageAdapter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * 处理第三方发送邮件
 */
@Slf4j
@Service
public class MailService implements MessageAdapter {
    @Value("${msg.subject}")
    private String subject;
    @Value("${msg.text}")
    private String text;
    @Value("${spring.mail.host}")
    private String mail;
    @Autowired
    private JavaMailSender javaMailSender;
    @Override
    public void sendMsg(JSONObject body) {
        // 处理发送邮件
        String email = body.getString("mail");
        if (StringUtils.isEmpty(email)) {
            return;
        }
        log.info("消息服务平台发送邮件:{}开始", email);
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        // 来自账号 自己发自己
        simpleMailMessage.setFrom(mail);
        // 发送账号
        simpleMailMessage.setTo(email);
        // 标题
        simpleMailMessage.setSubject(subject);
        // 内容
        simpleMailMessage.setText(text.replace("{}", email));
        // 发送邮件
        javaMailSender.send(simpleMailMessage);
        log.info("消息服务平台发送邮件:{}完成", email);
    }
}
