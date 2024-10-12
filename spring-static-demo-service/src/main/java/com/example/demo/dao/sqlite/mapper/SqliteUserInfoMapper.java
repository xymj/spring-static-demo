package com.example.demo.dao.sqlite.mapper;


import com.example.demo.dao.bo.UserInfo;
import org.apache.ibatis.annotations.*;

@Mapper
public interface SqliteUserInfoMapper {


  /**
   * CREATE TABLE user_info ( id INTEGER PRIMARY KEY, name TEXT NOT NULL, age INTEGER );
   */

  @Select("select id,name from user_info where id = #{userId}")
  @Results(id = "userInfo", value = {@Result(column = "id", property = "userId"),
      @Result(column = "name", property = "userName")})
  UserInfo getUserInfo(String userId);

  @Insert("insert into user_info(id,name) values(#{userId},#{userName})")
  Long insertUserInfo(UserInfo userInfo);

  @Update("UPDATE user_info SET name = #{userInfo.userName} WHERE id = #{userId}")
  Long updateUserInfo(@Param("userId") String userId, @Param("userInfo") UserInfo userInfo);
}
