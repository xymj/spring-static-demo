package com.example.demo.dao.postgresql.config;


import com.example.demo.dao.sqlite.config.SqliteConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;

/**
 * postgres=# create databases test_db;
 * postgres=# \l
 * postgres=# \c test_db;
 * test_db=# \d
 * test_db=# drop table company
 * test_db-# CREATE TABLE COMPANY(
 * test_db(#    ID INT PRIMARY KEY     NOT NULL,
 * test_db(#    NAME           TEXT    NOT NULL,
 * test_db(#    AGE            INT     NOT NULL,
 * test_db(#    ADDRESS        CHAR(50),
 * test_db(#    SALARY         REAL,
 * test_db(#    JOIN_DATE      DATE
 * test_db(# );
 * test_db=# \d company
 * test_db=# drop table company;
 * test_db=# INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY,JOIN_DATE) VALUES (1, 'Paul', 32, 'California', 20000.00,'2001-07-13');
 * test_db=# select * from company;
 *
 */
@Slf4j
@Configuration
@MapperScan(value = "com.example.demo.dao.postgresql.mapper",
    sqlSessionFactoryRef = "postgreSqlSessionFactory")
public class PostgreSqlConfig {

  @Bean(name = "postgreDataSource")
  public DataSource getDataSource() {
    return DataSourceBuilder.create().driverClassName("org.postgresql.Driver")
        .url("jdbc:postgresql://localhost:5432/test_db").username("postgres")
        .password("admin").build();
  }


  @Bean(name = "postgreTransactionManager")
  public DataSourceTransactionManager getTransactionManager() {
    return new DataSourceTransactionManager(getDataSource());
  }

  @Bean(name = "postgreSqlSessionFactory")
  public SqlSessionFactory getSqlSessionFactory() throws Exception {
    SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
    bean.setDataSource(getDataSource());
    // Set MyBatis log implementation
    bean.getObject().getConfiguration().setLogImpl(org.apache.ibatis.logging.stdout.StdOutImpl.class);
    return bean.getObject();
  }
}
