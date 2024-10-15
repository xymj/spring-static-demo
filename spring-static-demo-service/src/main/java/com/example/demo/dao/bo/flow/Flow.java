package com.example.demo.dao.bo.flow;

import java.util.Date;
import java.util.UUID;

import lombok.Data;

@Data
public class Flow {
    private UUID id;
    private String name;
    private String description;
    private String icon;
    private String iconBgColor;
    private String data;
    private Boolean isComponent;
    private Date updatedAt;
    private Boolean webhook;
    private String endpointName;
    private UUID userId;
    private UUID folderId;
}