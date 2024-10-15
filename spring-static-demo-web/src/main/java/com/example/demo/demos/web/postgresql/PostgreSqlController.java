package com.example.demo.demos.web.postgresql;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.bo.Company;
import com.example.demo.service.PostgreSqlOperateService;

@RestController
@RequestMapping("/postgresql/")
public class PostgreSqlController {
  @Autowired
  private PostgreSqlOperateService postgreSqlOperateService;

  @GetMapping("getCompany")
  @ResponseStatus(value = org.springframework.http.HttpStatus.OK)
  public Company getCompany(Integer id) {
    return postgreSqlOperateService.getCompany(id);
  }

}
