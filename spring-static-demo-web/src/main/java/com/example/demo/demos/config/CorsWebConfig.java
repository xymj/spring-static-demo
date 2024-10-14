package com.example.demo.demos.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * 在开发过程中，React 前端项目和 Java 后端项目通常会运行在不同的端口上，例如 React 可能在 http://localhost:3000 运行，而 Java Web 项目可能在 http://localhost:8080 运行。
 * 由于浏览器的同源策略，这种跨域请求默认情况下会被阻止。
 *
 * 在Java 后端项目中配置 CORS (Cross-Origin Resource Sharing) 允许特定的前端域进行访问
 *
 * 在配置 CORS (Cross-Origin Resource Sharing) 时，可以使用 allowedMethods 来指定允许的 HTTP 方法。以下是所有标准的 HTTP 方法的枚举：
 * GET - 用于获取数据。
 * POST - 用于提交数据。
 * PUT - 用于更新资源。
 * DELETE - 用于删除资源。
 * PATCH - 用于对资源进行部分更新。
 * OPTIONS - 用于获取服务器支持的 HTTP 方法。
 * HEAD - 类似于 GET，但不返回响应体。
 * TRACE - 回显服务器收到的请求，用于测试或诊断。
 * CONNECT - 用于将请求连接转换为透明的 TCP/IP 隧道，通常用于 HTTPS。
 */
@Configuration
public class CorsWebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS", "HEAD", "TRACE", "CONNECT")
                .allowCredentials(true);
    }
}