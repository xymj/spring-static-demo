package com.example.demo.dao.flow.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.demo.dao.bo.flow.UserTb;

@Mapper
public interface UserTbMapper {
    @Select("SELECT * FROM user_tb")
    List<UserTb> getUserTbs();
}
