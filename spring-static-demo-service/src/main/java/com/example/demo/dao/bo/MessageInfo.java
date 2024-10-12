package com.example.demo.dao.bo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageInfo {
  private String sessionId;

  private String role;

  private String content;
}
