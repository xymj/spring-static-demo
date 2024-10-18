package com.example.demo.dao.bo.flow;


import lombok.Data;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

@Data
public class UserTb {
    private UUID id;
    private String username;
    private String password;
    private String profileImage;
    private boolean isActive = true;
    private boolean isSuperuser = true;
    private String storeApiKey;
    private Date updatedAt;
    private Date createAt;
    private Date lastLoginAt;
}
