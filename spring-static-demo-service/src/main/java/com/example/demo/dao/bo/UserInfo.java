package com.example.demo.dao.bo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(staticName = "of")
public class UserInfo {
    private String userId;
    private String userName;
}
