package com.song.leaf.dao01;

import com.song.leaf.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDao1 {
    User selectByPrimaryKey(@Param("id") String id);

    Integer selectCount();
}
