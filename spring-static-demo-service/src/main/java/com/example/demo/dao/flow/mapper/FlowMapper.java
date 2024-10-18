package com.example.demo.dao.flow.mapper;


import com.example.demo.dao.bo.flow.Flow;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.UUID;

@Mapper
public interface FlowMapper {
  @Select("SELECT * FROM flow WHERE id = #{id}")
  Flow getFlowById(@Param("id") UUID id);

  @Select("SELECT * FROM flow WHERE folder_id = #{folder_id}")
  List<Flow> getFlowByFolderId(@Param("folder_id") UUID folder_id);

  @Select("SELECT * FROM flow WHERE user_id = #{user_id} and name = #{name} limit 1")
  Flow getFlowByName(@Param("user_id") UUID userId, @Param("name") String name);

  @Select("SELECT * FROM flow WHERE user_id = #{user_id} and name like CONCAT('', #{name}, ' (%')")
  List<Flow> getFlowByNameLike(@Param("user_id") UUID userId, @Param("name") String name);

  @Select("SELECT * FROM flow WHERE user_id = #{user_id} and endpoint_name = #{endpoint_name} limit 1")
  Flow getFlowByEndpointName(@Param("user_id") UUID userId, @Param("endpoint_name") String endpointName);

  @Select("SELECT * FROM flow WHERE user_id = #{user_id} and endpoint_name like CONCAT('', #{endpoint_name}, '-%')")
  List<Flow> getFlowByEndpointNameLike(@Param("user_id") UUID userId, @Param("endpoint_name") String endpointName);

  @Select("SELECT * FROM flow order by updated_at desc")
  List<Flow> getAllFlows();


  @Insert("INSERT INTO flow (name, description, icon, icon_bg_color, data, is_component, updated_at, webhook, endpoint_name, user_id, folder_id) "
      + "VALUES (#{name}, #{description}, #{icon}, #{iconBgColor}, CAST(#{data} AS JSONB), #{isComponent}, #{updatedAt}, #{webhook}, #{endpointName}, #{userId}, #{folderId})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insertFlow(Flow flow);


  @Insert("<script>" +
      "INSERT INTO flow (name, description, icon, icon_bg_color, data, is_component, updated_at, webhook, endpoint_name, user_id, folder_id) VALUES " +
      "<foreach collection='flows' item='flow' separator=','>" +
      "(#{flow.name}, #{flow.description}, #{flow.icon}, #{flow.iconBgColor}, CAST(#{flow.data} AS JSONB), #{flow.isComponent}, #{flow.updatedAt}, #{flow.webhook}, #{flow.endpointName}, #{flow.userId}, #{flow.folderId})" +
      "</foreach>" +
      "</script>")
  //  @Options(useGeneratedKeys = true, keyProperty = "id")  批量不支持
  void insertFlows(@Param("flows") List<Flow> flows);


  @Select("<script>" +
      "SELECT * FROM flow WHERE name IN " +
      "<foreach collection='flows' item='flow' open='(' separator=',' close=')'>" +
      "#{flow.name}" +
      "</foreach>" +
      "</script>")
  List<Flow> getInsertedFlows(@Param("flows") List<Flow> flows);

  @Update("UPDATE flow SET name = #{name}, description = #{description}, icon = #{icon}, icon_bg_color = #{iconBgColor}, data = CAST(#{data} AS JSONB), is_component = #{isComponent}, "
      + "updated_at = #{updatedAt}, webhook = #{webhook}, endpoint_name = #{endpointName}, user_id = #{userId}, folder_id = #{folderId} WHERE id = #{id}")
  void updateFlow(Flow flow);

  @Delete("DELETE FROM flow WHERE id = #{id}")
  void deleteFlow(@Param("id") UUID id);

  @Delete("DELETE FROM flow WHERE folder_id = #{folder_id}")
  void deleteFlowByFolderId(@Param("folder_id") UUID folderId);

  @Delete("<script>" +
      "DELETE FROM flow WHERE id IN " +
      "<foreach item='item' index='index' collection='ids' open='(' separator=',' close=')'>" +
      "#{item}" +
      "</foreach>" +
      "</script>")
  int deleteFlows(@Param("ids") List<UUID> ids);
}
