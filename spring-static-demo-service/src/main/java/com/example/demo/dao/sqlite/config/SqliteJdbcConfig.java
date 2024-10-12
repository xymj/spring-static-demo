//package com.example.demo.dao.sqlite.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
//import org.springframework.data.relational.core.dialect.Dialect;
//import org.springframework.data.relational.core.dialect.DialectResolver;
//import org.springframework.jdbc.core.JdbcOperations;
//
//@Configuration
//public class SqliteJdbcConfig extends AbstractJdbcConfiguration {
//
//    @Bean
//    public Dialect jdbcDialect(JdbcOperations operations) {
//        return SQLiteDialect.INSTANCE;
//    }
//}