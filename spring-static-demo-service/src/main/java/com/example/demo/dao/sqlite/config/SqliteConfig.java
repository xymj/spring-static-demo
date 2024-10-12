package com.example.demo.dao.sqlite.config;


import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.sqlite.SQLiteDataSource;


@Slf4j
@Configuration
@MapperScan(value = "com.example.demo.dao.sqlite.mapper",
    sqlSessionFactoryRef = "sqliteSqlSessionFactory")
public class SqliteConfig {

  @Bean(name = "sqliteDataSource")
  public DataSource getDataSource() {
    String path = SqliteConfig.class.getClassLoader().getResource("sqlite/test.db").getPath();
    String url = "jdbc:sqlite:" + path;
    log.info("sqlite path:{}, url:{}", path, url);
    SQLiteDataSource dataSource = new SQLiteDataSource();
    dataSource.setUrl(url);
    return dataSource;
  }


  @Bean(name = "sqliteTransactionManager")
  public DataSourceTransactionManager getTransactionManager() {
    return new DataSourceTransactionManager(getDataSource());
  }

  @Bean(name = "sqliteSqlSessionFactory")
  public SqlSessionFactory getSqlSessionFactory() throws Exception {
    SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
    bean.setDataSource(getDataSource());
    return bean.getObject();
  }

}
