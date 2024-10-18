package com.example.demo.service;


import com.example.demo.dao.bo.flow.Flow;
import com.example.demo.dao.bo.flow.Folder;
import com.example.demo.dao.bo.flow.FolderFlows;
import com.example.demo.dao.flow.mapper.FolderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class FolderOperateService {
  @Autowired
  private FolderMapper folderMapper;
  @Autowired
  private FlowOperateService flowOperateService;

  public List<Folder> getFolderByUserId(UUID userId) {
    return folderMapper.getFolderByUserId(userId);
  }

  public FolderFlows getFolderById(UUID uuid) {
    Folder folder = folderMapper.getFolderById(uuid);
    List<Flow> flows = flowOperateService.getFlowByFolderId(uuid);
    FolderFlows folderFlows = FolderFlows.of();
    folderFlows.setId(folder.getId());
    folderFlows.setName(folder.getName());
    folderFlows.setDescription(folder.getDescription());
    folderFlows.setParentId(folder.getParentId());
    folderFlows.setUserId(folder.getUserId());
    folderFlows.setFlows(flows);
    return folderFlows;
  }

  public Folder createFolder(Folder folder) {
    folderMapper.insertFolder(folder);
    return folder;
  }

  @Transactional(transactionManager = "flowRelateTransactionManager")
  public Folder updateFolder(Folder folder) {
    Folder folderDb = getFolderById(folder.getId());
    if (folderDb == null) {
      return null;
    }
    folderMapper.updateFolder(folder);
    return folder;
  }
}
