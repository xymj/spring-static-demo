package com.example.demo.dao.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * CREATE TABLE COMPANY(
 *    ID INT PRIMARY KEY     NOT NULL,
 *    NAME           TEXT    NOT NULL,
 *    AGE            INT     NOT NULL,
 *    ADDRESS        CHAR(50),
 *    SALARY         REAL,
 *    JOIN_DATE      DATE
 * );
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Company {
  private Integer id;
  private String name;
  private Integer age;
  private String address;
  private Double salary;
  private Date joinDate;
}
