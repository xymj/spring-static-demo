package com.example.demo.dao.bo.flow;


import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class UserTb {
    private UUID id;
    private String username;
    private String password;
    private String profileImage;
    private boolean isActive;
    private boolean isSuperuser;
    private String storeApiKey;
    private Date updatedAt;
    private Date createdAt;
    private Date lastLoginAt;
}
