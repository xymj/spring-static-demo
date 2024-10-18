package com.example.demo.dao.flow.config;


import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@Configuration
@MapperScan(value = "com.example.demo.dao.flow.mapper",
    sqlSessionFactoryRef = "flowRelateSqlSessionFactory")
public class FlowRelateConfig {

  @Bean(name = "flowRelateDataSource")
  public DataSource getDataSource() {
    return DataSourceBuilder.create().driverClassName("org.postgresql.Driver")
        .url("jdbc:postgresql://localhost:5432/flow_relate").username("postgres")
        .password("admin").build();
  }


  /**
   * 如果项目中有多个 TransactionManager，
   * 你可以通过 @Primary 注解来指定主事务管理器，这样 Spring 在自动装配时会使用带有 @Primary 注解的事务管理器。
   * @return
   */
  @Bean(name = "flowRelateTransactionManager")
  @Primary
  public DataSourceTransactionManager getTransactionManager() {
    return new DataSourceTransactionManager(getDataSource());
  }

  @Bean(name = "flowRelateSqlSessionFactory")
  public SqlSessionFactory getSqlSessionFactory() throws Exception {
    SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
    sessionFactory.setDataSource(getDataSource());
    // Set MyBatis log implementation
    sessionFactory.getObject().getConfiguration().setLogImpl(org.apache.ibatis.logging.stdout.StdOutImpl.class);

    // Set the TypeHandler
    sessionFactory.setTypeHandlersPackage("com.example.demo.dao.flow.config");
    SqlSessionFactory sqlSessionFactory = sessionFactory.getObject();
    TypeHandlerRegistry typeHandlerRegistry = sqlSessionFactory.getConfiguration().getTypeHandlerRegistry();
    typeHandlerRegistry.register(UUID.class, UUIDTypeHandler.class);

    // 字段下划线转驼峰
    sqlSessionFactory.getConfiguration().setMapUnderscoreToCamelCase(true);
    return sqlSessionFactory;
  }
}
