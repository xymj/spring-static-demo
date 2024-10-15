package com.example.demo.dao.flow.mapper;


import com.example.demo.dao.bo.flow.Flow;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.UUID;

@Mapper
public interface FlowMapper {
  @Select("SELECT * FROM flow WHERE id = #{id}")
  Flow getFlowById(@Param("id") UUID id);

  @Select("SELECT * FROM flow WHERE user_id = #{user_id} and name = #{name} limit 1")
  Flow getFlowByName(@Param("user_id") UUID userId, @Param("name") String name);

  @Select("SELECT * FROM flow WHERE user_id = #{user_id} and name like CONCAT('', #{name}, ' (%')")
  List<Flow> getFlowByNameLike(@Param("user_id") UUID userId, @Param("name") String name);

  @Select("SELECT * FROM flow WHERE user_id = #{user_id} and endpoint_name = #{endpoint_name} limit 1")
  Flow getFlowByEndpointName(@Param("user_id") UUID userId, @Param("endpoint_name") String endpointName);

  @Select("SELECT * FROM flow WHERE user_id = #{user_id} and endpoint_name like CONCAT('', #{endpoint_name}, '-%')")
  Flow getFlowByEndpointNameLike(@Param("user_id") UUID userId, @Param("endpoint_name") String endpointName);

  @Select("SELECT * FROM flow")
  List<Flow> getAllFlows();

  @Insert("INSERT INTO flow (id, name, description, icon, icon_bg_color, data, is_component, updated_at, webhook, endpoint_name, user_id, folder_id) "
      + "VALUES (#{id}, #{name}, #{description}, #{icon}, #{iconBgColor}, #{data}, #{isComponent}, #{updatedAt}, #{webhook}, #{endpointName}, #{userId}, #{folderId})")
  void insertFlow(Flow flow);

  @Update("UPDATE flow SET name = #{name}, description = #{description}, icon = #{icon}, icon_bg_color = #{iconBgColor}, data = #{data}, is_component = #{isComponent}, "
      + "updated_at = #{updatedAt}, webhook = #{webhook}, endpoint_name = #{endpointName}, user_id = #{userId}, folder_id = #{folderId} WHERE id = #{id}")
  void updateFlow(Flow flow);

  @Delete("DELETE FROM flow WHERE id = #{id}")
  void deleteFlow(@Param("id") UUID id);
}
