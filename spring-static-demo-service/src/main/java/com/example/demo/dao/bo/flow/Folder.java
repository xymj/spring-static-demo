package com.example.demo.dao.bo.flow;


import lombok.Data;

import java.util.UUID;

@Data
public class Folder {
  private UUID id;
  private String name;
  private String description;
  private UUID parentId;
  private UUID userId;
}
