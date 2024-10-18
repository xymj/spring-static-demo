package com.example.demo.dao.flow.mapper;


import java.util.List;
import java.util.UUID;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.demo.dao.bo.flow.UserTb;

@Mapper
public interface UserTbMapper {
    @Select("SELECT * FROM user_tb")
    List<UserTb> getUserTbs();

    @Select("SELECT * FROM user_tb where id=#{id}")
    UserTb getUserTb(@Param("id") UUID uuid);
}
