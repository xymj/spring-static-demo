package com.example.demo.service;


import com.example.demo.dao.bo.Company;
import com.example.demo.dao.bo.UserInfo;
import com.example.demo.dao.postgresql.mapper.CompanyMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PostgreSqlOperateService {
  @Autowired
  private CompanyMapper companyMapper;

  public Company getCompany(Integer id) {
    Company company = companyMapper.getCompany(id);
    log.info("company:{}", company);
    return company;
  }
}
