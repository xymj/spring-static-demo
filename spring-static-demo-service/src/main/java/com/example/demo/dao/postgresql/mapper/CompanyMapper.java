package com.example.demo.dao.postgresql.mapper;


import com.example.demo.dao.bo.Company;
import com.example.demo.dao.bo.UserInfo;
import org.apache.ibatis.annotations.*;

@Mapper
public interface CompanyMapper {
    @Select("select * from company where id = #{id}")
    @Results(id = "company", value = {@Result(column = "id", property = "id"),
        @Result(column = "name", property = "name"),
        @Result(column = "age", property = "age"),
        @Result(column = "address", property = "address"),
        @Result(column = "salary", property = "salary"),
        @Result(column = "join_date", property = "joinDate")})
    Company getCompany(@Param("id") Integer id);
}
