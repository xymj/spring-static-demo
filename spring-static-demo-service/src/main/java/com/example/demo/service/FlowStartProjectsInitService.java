package com.example.demo.service;


import com.example.demo.constant.Constants;
import com.example.demo.dao.bo.flow.Flow;
import com.example.demo.dao.bo.flow.Folder;
import com.example.demo.dao.flow.mapper.FlowMapper;
import com.example.demo.dao.flow.mapper.FolderMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class FlowStartProjectsInitService {

  @Autowired
  private FlowMapper flowMapper;

  @Autowired
  private FolderMapper folderMapper;

  @Autowired
  private ObjectMapper objectMapper;

  public void init() {
    log.info("init start");
    Folder folder = createStartProjects();
    Map<String, Flow> flowMap = loadStarterProjects();
    deleteStartProjects(folder.getId());
    List<Flow> flows = flowMap.values().stream().map(flow -> {
      flow.setFolderId(folder.getId());
      flow.setUpdatedAt(new Date());
      return flow;
    }).collect(Collectors.toList());
    createNewProject(flows);
    log.info("init end");
  }

  public Folder createStartProjects() {
    // 存在返回
    Folder folderByName = folderMapper.getFolderByName(Constants.STARTER_FOLDER_NAME);
    if (folderByName != null && folderByName.getId() != null) {
      return folderByName;
    }
    // 不存在创建
    Folder folder = new Folder();
    folder.setName(Constants.STARTER_FOLDER_NAME);
    folder.setDescription(Constants.STARTER_FOLDER_DESCRIPTION);
    folderMapper.insertFolder(folder);
    return folder;
  }

  public Map<String, Flow> loadStarterProjects() {
    String path = this.getClass().getClassLoader().getResource("starter_projects/").getPath();
    Map<String, Flow> flowMap = Maps.newHashMap();
    try (Stream<Path> filePath = Files.walk(Paths.get(path))) {
      filePath.filter(Files::isRegularFile).forEach(file -> {
        String fileName = file.getFileName().toString();
        try (InputStream inputStream = Files.newInputStream(file)) {
          ByteArrayOutputStream buff = new ByteArrayOutputStream();
          byte[] buffer = new byte[1024];
          int len;
          while ((len = inputStream.read(buffer)) != -1) {
            buff.write(buffer, 0, len);
          }
          String content = new String(buff.toByteArray(), StandardCharsets.UTF_8);
          Flow flow = null;
          try {
            flow = objectMapper.readValue(content, Flow.class);
            flowMap.put(flow.getName(), flow);
          } catch (Exception e) {
            log.error("parse flow error:", e, fileName);
          }
          if (flow != null) {
            log.info("loadStarterProjects {}: {}", fileName, flow);
            flowMap.put(fileName, flow);
          }
        } catch (Exception e) {
          log.error("loadStarterProjects error", e);
        }
      });
    } catch (Exception e) {
      log.error("loadStarterProjects error", e);
    }

    return flowMap;
  }

  public static void main(String[] args) {
    String path = FlowStartProjectsInitService.class.getClassLoader().getResource("starter_projects/").getPath();
    System.out.println(path);
    try (Stream<Path> filePath = Files.walk(Paths.get(path))) {
      filePath.filter(Files::isRegularFile).forEach(file -> {
        File pathFile = new File(file.getFileName().toString());
        try (InputStream inputStream = Files.newInputStream(file)) {
          ByteArrayOutputStream buff = new ByteArrayOutputStream();
          byte[] buffer = new byte[1024];
          int len;
          while ((len = inputStream.read(buffer)) != -1) {
            buff.write(buffer, 0, len);
          }
          String content = new String(buff.toByteArray(), StandardCharsets.UTF_8);
          System.out.println(file.getFileName().toString() + " : " + content);
        } catch (Exception e) {
          log.error("loadStarterProjects error", e);
        }
      });
    } catch (Exception e) {
      log.error("loadStarterProjects error", e);
    }
  }

  public void deleteStartProjects(UUID folderId) {
    flowMapper.deleteFlowByFolderId(folderId);
  }

  public void createNewProject(List<Flow> flows) {
    flowMapper.insertFlows(flows);
  }
}
