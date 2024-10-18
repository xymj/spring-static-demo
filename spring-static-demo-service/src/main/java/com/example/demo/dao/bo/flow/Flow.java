package com.example.demo.dao.bo.flow;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

@Data
public class Flow {
    private UUID id;
    private String name;
    private String description;
    private String icon;
    @JsonProperty("icon_bg_color")
    private String iconBgColor;

    @JsonRawValue  // 告诉 Jackson 处理该字段时保留原始的 JSON 格式
    @JsonDeserialize(using = JsonStringDeserializer.class) // 告诉 Jackson 原始的 JSON 格式转化为String
    private String data;

    @JsonProperty("is_component")
    private Boolean isComponent;
    @JsonProperty("updated_at")
    private Date updatedAt;
    private Boolean webhook;
    @JsonProperty("endpoint_name")
    private String endpointName;
    @JsonProperty("user_id")
    private UUID userId;
    @JsonProperty("folder_id")
    private UUID folderId;
}