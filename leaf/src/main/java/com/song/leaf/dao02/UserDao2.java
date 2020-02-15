package com.song.leaf.dao02;

import com.song.leaf.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDao2 {
    User selectByPrimaryKey(@Param("id") String id);
}
