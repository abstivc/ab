package com.song.api;

import com.song.base.ResponseBase;
import com.song.entity.UserEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("member")
public interface MemberService {

    // userId
    @RequestMapping("/findUserById")
    ResponseBase findUserById(Long userId);

    @RequestMapping("/registerUser")
    ResponseBase registerUser(@RequestBody UserEntity userEntity);

    @RequestMapping("/login")
    ResponseBase login(@RequestBody UserEntity userEntity);

    // 使用token进行登陆
    @RequestMapping("/findUserByToken")
    ResponseBase findUserByToken(String token);
}
