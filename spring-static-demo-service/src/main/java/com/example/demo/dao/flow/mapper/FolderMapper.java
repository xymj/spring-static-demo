package com.example.demo.dao.flow.mapper;

import org.apache.ibatis.annotations.*;
import com.example.demo.dao.bo.flow.Folder;

import java.util.List;
import java.util.UUID;

@Mapper
public interface FolderMapper {

    @Insert("INSERT INTO folder (name, description, parent_id, user_id) VALUES (#{name}, #{description}, #{parentId}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertFolder(Folder folder);

    @Update("UPDATE folder SET name = #{name}, description = #{description}, parent_id = #{parentId}, user_id = #{userId} WHERE id = #{id}")
    void updateFolder(Folder folder);

    @Delete("DELETE FROM folder WHERE id = #{id}")
    void deleteFolder(UUID id);

    @Select("SELECT * FROM folder WHERE id = #{id}")
    Folder getFolderById(UUID id);

    @Select("SELECT * FROM folder WHERE name = #{name}")
    Folder getFolderByName(String name);

    @Select("SELECT * FROM folder WHERE user_id = #{user_id} or user_id is null")
    List<Folder> getFolderByUserId(@Param("user_id") UUID userId);

    @Select("SELECT * FROM folder")
    List<Folder> getAllFolders();
}