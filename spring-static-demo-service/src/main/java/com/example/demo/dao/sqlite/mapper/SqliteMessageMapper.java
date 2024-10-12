package com.example.demo.dao.sqlite.mapper;

import java.util.List;

import org.apache.ibatis.annotations.*;

import com.example.demo.dao.bo.MessageInfo;

@Mapper
public interface SqliteMessageMapper {


  /**
   * CREATE TABLE message_info ( session_id Text PRIMARY KEY, role TEXT NOT NULL, content TEXT );
   * DROP TABLE IF EXISTS message_info;
   * CREATE TABLE message_info ( session_id Text KEY, role TEXT NOT NULL, content TEXT );
   */
  @Select("select session_id, role, content " + "from message_info "
      + "where session_id = #{sessionId}")
  @Results(id = "messageInfo",
      value = {@Result(property = "sessionId", column = "session_id"),
          @Result(property = "role", column = "role"),
          @Result(property = "content", column = "content")})
  List<MessageInfo> selectList(@Param("sessionId") String sessionId);

  @Insert("insert into message_info(session_id,role,content)"
      + "values(#{sessionId}, #{role}, #{content})")
  Long insert(MessageInfo messageInfo);

  @Delete("delete from message_info where session_id = #{sessionId}")
  Long delete(@Param("sessionId") String sessionId);
}
