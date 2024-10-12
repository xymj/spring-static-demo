package com.example.demo.demos.web.sqlite;


import com.example.demo.dao.bo.MessageInfo;
import com.example.demo.dao.bo.UserInfo;
import com.example.demo.service.SqliteOperateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SqliteOperateController {

  @Autowired
  private SqliteOperateService sqliteOperateService;


  @PostMapping("/sqlite/insertUserInfo")
  public Long insertUserInfo(@RequestBody UserInfo userInfo) {
    return sqliteOperateService.insertUserInfo(userInfo);
  }

  /**
   * 在 Spring Boot 中，@PatchMapping 注解用于处理 HTTP PATCH 请求。PATCH 请求通常用于对资源进行部分更新，而不是像 PUT 请求那样进行整个资源的替换。
   * @param userId
   * @param userInfo
   * @return
   */
  @PatchMapping("/sqlite/updateUserInfo/{userId}")
  public UserInfo updateUserInfo(@PathVariable String userId, @RequestBody UserInfo userInfo) {
    return sqliteOperateService.updateUserInfo(userId, userInfo);
  }

  @GetMapping("/sqlite/getUserInfo")
  public UserInfo getUserInfo(String userId) {
    return sqliteOperateService.getUserInfo(userId);
  }

  @PostMapping("/sqlite/insertMessage")
  public Long insertMessage(@RequestBody MessageInfo messageInfo) {
    return sqliteOperateService.insertMessage(messageInfo);
  }

  @GetMapping("/sqlite/deleteMessage")
  public Long deleteMessage(String sessionId) {
    return sqliteOperateService.deleteMessage(sessionId);
  }

  @GetMapping("/sqlite/selectMessage")
  public List<MessageInfo> selectMessage(String sessionId) {
    return sqliteOperateService.selectMessage(sessionId);
  }

}
