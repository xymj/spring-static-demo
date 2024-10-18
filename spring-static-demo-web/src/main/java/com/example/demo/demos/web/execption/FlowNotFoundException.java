package com.example.demo.demos.web.execption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


//在 Spring Boot 中，你可以使用 @ResponseStatus 注解来标记自定义异常类，
// 以便在异常抛出时自动返回相应的 HTTP 状态码和消息。这样可以使你的异常处理更加简洁和可读。
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Flow not found")
public class FlowNotFoundException extends RuntimeException {
    public FlowNotFoundException(String message) {
        super(message);
    }
}