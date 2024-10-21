package com.example.demo.demos.web.chat;

import com.example.demo.demos.vo.InputMessage;
import com.example.demo.demos.vo.OutputMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

  @PostMapping("/api/chat")
  public OutputMessage chat(@RequestBody InputMessage inputMessage) {
    String botReply = "You said: " + inputMessage.getMessage();
    return new OutputMessage(botReply);
  }
}
