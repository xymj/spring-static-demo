package com.example.demo.service;


import com.example.demo.dao.bo.flow.UserTb;
import com.example.demo.dao.flow.mapper.UserTbMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class UserTbOperateService {

  @Autowired
  private UserTbMapper userTbMapper;

  public List<UserTb> getUserTbs() {
    return userTbMapper.getUserTbs();
  }

  public UserTb getUserTb(UUID uuid) {
    return userTbMapper.getUserTb(uuid);
  }
}
