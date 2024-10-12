//package com.example.demo.dao.sqlite.config;
//
//import org.springframework.data.relational.core.dialect.*;
//import org.springframework.data.relational.core.dialect.OrderByNullPrecedence;
//import org.springframework.data.relational.core.sql.render.SqlRenderer;
//import org.springframework.data.relational.core.sql.render.DefaultSqlRenderer;
//
//public class SQLiteDialect implements Dialect {
//
//    public static final SQLiteDialect INSTANCE = new SQLiteDialect();
//
//    @Override
//    public LimitClause limit() {
//        return MySqlDialect.INSTANCE.limit();
//    }
//
//    @Override
//    public LockClause lock() {
//        return LockClause.NONE;
//    }
//
//    @Override
//    public OrderByNullPrecedence orderByNullPrecedence() {
//        return OrderByNullPrecedence.LAST;
//    }
//
//    @Override
//    public RenderContextFactory renderContextFactory() {
//        return new RenderContextFactory();
//    }
//
//    @Override
//    public SqlRenderer getSqlRenderer() {
//        return new DefaultSqlRenderer(renderContextFactory());
//    }
//}