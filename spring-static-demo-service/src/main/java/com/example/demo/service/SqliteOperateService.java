package com.example.demo.service;


import com.example.demo.dao.bo.MessageInfo;
import com.example.demo.dao.bo.UserInfo;
import com.example.demo.dao.sqlite.mapper.SqliteMessageMapper;
import com.example.demo.dao.sqlite.mapper.SqliteUserInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Service
public class SqliteOperateService {

  @Autowired
  private SqliteUserInfoMapper sqliteUserInfoMapper;

  @Autowired
  private SqliteMessageMapper sqliteMessageMapper;


  public Long insertUserInfo(UserInfo userInfo) {
    return sqliteUserInfoMapper.insertUserInfo(userInfo);
  }

  public UserInfo getUserInfo(String userId) {
    UserInfo userInfo = sqliteUserInfoMapper.getUserInfo(userId);
    log.info("userInfo:{}", userInfo);
    return userInfo;
  }

  public Long insertMessage(MessageInfo messageInfo) {
    return sqliteMessageMapper.insert(messageInfo);
  }

  public Long deleteMessage(String sessionId) {
    return sqliteMessageMapper.delete(sessionId);
  }

  public List<MessageInfo> selectMessage(String sessionId) {
    return sqliteMessageMapper.selectList(sessionId);
  }

  @Transactional(rollbackFor = Exception.class)
  public UserInfo updateUserInfo(String userId, UserInfo userInfo) {
    UserInfo bo = sqliteUserInfoMapper.getUserInfo(userId);
    if (bo != null) {
      bo.setUserName(userInfo.getUserName());
      sqliteUserInfoMapper.updateUserInfo(userId, userInfo);
    }
    return bo;
  }
}
