package com.example.demo.demos.web.flow;


import com.example.demo.dao.bo.flow.UserTb;
import com.example.demo.service.UserTbOperateService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users/")
public class UserTbController {

  @Autowired
  private UserTbOperateService userTbOperateService;
  @Autowired
  private ObjectMapper objectMapper;
  @GetMapping("whoami")
  public UserTb whoami() throws IOException {
    return userTbOperateService.getUserTb(UUID.fromString("f178f98a-f3e3-445f-8507-5d32f8aeeeca"));
//    String data = "{\n" + "    \"id\": \"f178f98a-f3e3-445f-8507-5d32f8aeeeca\",\n"
//        + "    \"username\": \"langflow\",\n" + "    \"profile_image\": null,\n"
//        + "    \"is_active\": true,\n" + "    \"is_superuser\": true,\n"
//        + "    \"create_at\": \"2024-07-02T07:55:27.284790\",\n"
//        + "    \"updated_at\": \"2024-10-12T08:52:32.258509\",\n"
//        + "    \"last_login_at\": \"2024-10-12T08:52:32.254521\"\n" + "}";
//    return objectMapper.readTree(data.getBytes(StandardCharsets.UTF_8));
  }

  @GetMapping("auto_login")
  public JsonNode autoLogin() throws IOException {
    String data = "{\"access_token\":\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIwMzg5ZmIyOS1kYWE2LTQwOGMtYjhjYi1iOGZmOGQxNzM0M2EiLCJleHAiOjE3NDA2NjY3NTB9.ef5W5jwNOeVzU3JZ7ylLYf2MLEJcVxC4-fF7EK9Ecdc\",\"refresh_token\":null,\"token_type\":\"bearer\"}";
    return objectMapper.readTree(data.getBytes(StandardCharsets.UTF_8));
  }
  @GetMapping("")
  public List<UserTb> getUser() {
    return userTbOperateService.getUserTbs();
  }

  @PostMapping("users")
  public UserTb createUser(@RequestBody UserTb userTb) {
    return null;
  }
}
