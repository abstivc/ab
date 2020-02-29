package com.song.api.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.song.api.MemberService;
import com.song.api.dao.MemberDao;
import com.song.base.BaseApiController;
import com.song.base.ResponseBase;
import com.song.entity.UserEntity;
import com.song.api.mq.RegisterMailboxProducer;
import com.song.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class MemberServiceImpl extends BaseApiController implements MemberService {

    @Autowired
    MemberDao memberDao;
    @Autowired
    RegisterMailboxProducer registerMailboxProducer;
    @Value("${messages.queue}")
    private String MESSAGES_QUEUE;

    @Override
    public ResponseBase findUserById(Long userId) {
        UserEntity user = memberDao.findByID(userId);
        if (user == null) {
            return setResultError("未查找到该用户！");
        }
        return setResultSuccess(user);
    }

    @Override
    public ResponseBase registerUser(@RequestBody  UserEntity userEntity) {
        String passWord=userEntity.getPassword();
        if (StringUtils.isEmpty(passWord)) {
            return setResultError("用户密码不能为空!");
        }
        String newPassWord= MD5Util.MD5(passWord);
        userEntity.setPassword(newPassWord);
        Integer insertUser = memberDao.insertUser(userEntity);
        if (insertUser <= 0) {
            return setResultError("注册失败!");
        }
        // 采用MQ异步发送邮件
        String email = userEntity.getEmail();
        String emailJson = emailJson(email);
        log.info("######email:{},messAageJson:{}", email, emailJson);
        sendMsg(emailJson);
        return setResultSuccess();
    }

    private void sendMsg(String emailJson) {
        ActiveMQQueue activeMQQueue = new ActiveMQQueue(MESSAGES_QUEUE);
        registerMailboxProducer.sendMsg(activeMQQueue, emailJson);
    }

    private String emailJson(String mail) {
        JSONObject root = new JSONObject();
        JSONObject header = new JSONObject();
        header.put("interfaceType", "sms_mail");
        JSONObject content = new JSONObject();
        content.put("mail", mail);
        root.put("header", header);
        root.put("content", content);
        return root.toJSONString();
    }
}
