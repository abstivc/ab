package com.song.api.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.song.api.MemberService;
import com.song.api.dao.MemberDao;
import com.song.base.BaseApiController;
import com.song.base.BaseRedisService;
import com.song.base.ResponseBase;
import com.song.constants.Constants;
import com.song.entity.UserEntity;
import com.song.api.mq.RegisterMailboxProducer;
import com.song.utils.MD5Util;
import com.song.utils.TokenUtils;
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
    @Autowired
    BaseRedisService baseRedisService;
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
        log.info("######email:{},messageAndJson:{}", email, emailJson);
        sendMsg(emailJson);
        return setResultSuccess();
    }

    @Override
    public ResponseBase login(@RequestBody  UserEntity user) {
        // 1.验证参数
        String username = user.getUsername();
        if (StringUtils.isEmpty(username)) {
            return setResultError("用戶名称不能为空!");
        }
        String password = user.getPassword();
        if (StringUtils.isEmpty(password)) {
            return setResultError("密码不能为空!");
        }
        // 2.数据库查找账号密码是否正确
        String newPassword = MD5Util.MD5(password);
        UserEntity userEntity = memberDao.login(username, newPassword);
        if (userEntity == null) {
            return setResultError("账号或者密码不能正确");
        }
        // 3.如果账号密码正确，对应生成token
        String memberToken = TokenUtils.getMemberToken();
        // 4.存放在redis中，key为token value 为 userid
        Integer userId = userEntity.getId();
        log.info("####用户信息token存放在redis中... key为:{},value", memberToken, userId);
        baseRedisService.set(memberToken, userId + "", Constants.TOKEN_MEMBER_TIME);
        // 5.直接返回token
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("memberToken", memberToken);
        return setResultSuccess(jsonObject);
    }

    @Override
    public ResponseBase findUserByToken(String token) {
        // 1.验证参数
        if (StringUtils.isEmpty(token)) {
            return setResultError("token不能为空!");
        }
        // 2.从redis中 使用token 查找对应 userid
        String strUserId = (String) baseRedisService.get(token);
        if (StringUtils.isEmpty(strUserId)) {
            return setResultError("token无效或者已经过期!");
        }
        // 3.使用userid数据库查询用户信息返回给客户端
        Long userId = Long.parseLong(strUserId);
        UserEntity userEntity = memberDao.findByID(userId);
        if (userEntity == null) {
            return setResultError("为查找到该用户信息");
        }
        userEntity.setPassword(null);
        return setResultSuccess(userEntity);
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
