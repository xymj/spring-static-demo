package com.example.demo.demos.web.flow;


import java.util.List;
import java.util.UUID;

import javax.websocket.server.PathParam;

import com.example.demo.dao.bo.flow.FolderFlows;
import com.example.demo.demos.web.execption.FlowNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dao.bo.flow.Folder;
import com.example.demo.service.FolderOperateService;

@RestController
@RequestMapping("/api/v1/")
public class FolderController {

  @Autowired
  private FolderOperateService folderOperateService;

  @GetMapping("folders/")
  public List<Folder> getFolderByUserId() {
    UUID userId = UUID.fromString("f178f98a-f3e3-445f-8507-5d32f8aeeeca");
    return folderOperateService.getFolderByUserId(userId);
  }

  @GetMapping("folders/{folder_id}")
  public FolderFlows getFolderById(@PathVariable(value = "folder_id") String folderId) {
    return folderOperateService.getFolderById(UUID.fromString(folderId));
  }

  @PostMapping("folders/")
  public Folder createFolder(@RequestBody Folder folder) {
    return folderOperateService.createFolder(folder);
  }

  @PatchMapping("folders/{folder_id}")
  public Folder updateFolder(@PathVariable(value = "folder_id") String folder_id, @RequestBody Folder folder) {
    folder.setId(UUID.fromString(folder_id));
    Folder newFolder = folderOperateService.updateFolder(folder);
    if (newFolder == null) {
      throw new FlowNotFoundException("Folder not found");
    }
    return newFolder;
  }

}
