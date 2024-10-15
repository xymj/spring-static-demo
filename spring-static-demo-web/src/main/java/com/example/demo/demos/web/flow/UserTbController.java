package com.example.demo.demos.web.flow;


import com.example.demo.dao.bo.flow.UserTb;
import com.example.demo.service.UserTbOperateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class UserTbController {

  @Autowired
  private UserTbOperateService userTbOperateService;

  @GetMapping("users")
  public List<UserTb> getUserTbs() {
    return userTbOperateService.getUserTbs();
  }
}
