package com.example.demo.demos.web.flow;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;


/**
 * 需适配接口：
 *  addFlow：/api/v1/flows  POST
 *  saveFlow: /api/v1/flows/{flow_id}  PATCH
 *  refreshFlows: /api/v1/flows  GET
 *  getFlow: /api/v1/flows/{flow_id}  GET
 *  deleteFlow: /api/v1/flows/{flow_id}  DELETE
 *  batch_delete_flows: /api/v1/flows/  DELETE
 *
 *  uploadFlows: /api/v1/flows/upload  POST
 *  downloadFlow: /api/v1/flows/download/  GET
 *
 * store接口：
 *  Saves a new flow to the database.
 *      /api/v1/store/components/ POST
 *
 *  节点类型：
 *     getAll
 *     /api/v1/all?force_refresh=${force_refresh}
 */
@RestController
@RequestMapping("/api/v1/")
public class FlowController {

    @Autowired
    private ObjectMapper objectMapper;
    @GetMapping("auto_login")
    public JsonNode autoLogin() throws IOException {
        String data = "{\n" +
            "    \"access_token\": \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI1NjAyMWE0Yy0xYWZmLTRlMGQtYWZlYy02NWUwZGNlZGJkMmMiLCJ0eXBlIjoiYWNjZXNzIiwiZXhwIjoxNzYwMjU5MTUyfQ.sckNzbPo9fTvrx3FgLms00K4LrP0mMtQGmWXDRoDZVk\",\n" +
            "    \"refresh_token\": null,\n" +
            "    \"token_type\": \"bearer\"\n" +
            "}";
        return objectMapper.readTree(data.getBytes(StandardCharsets.UTF_8));
    }

    @GetMapping("health")
    public JsonNode health() throws IOException {
        String data = "{\"status\":\"ok\"}";
        return objectMapper.readTree(data.getBytes(StandardCharsets.UTF_8));
    }

    @GetMapping("users/whoami")
    public JsonNode whoami() throws IOException {
        String data = "{\n" +
            "    \"id\": \"56021a4c-1aff-4e0d-afec-65e0dcedbd2c\",\n" +
            "    \"username\": \"langflow\",\n" +
            "    \"profile_image\": null,\n" +
            "    \"is_active\": true,\n" +
            "    \"is_superuser\": true,\n" +
            "    \"create_at\": \"2024-07-02T07:55:27.284790\",\n" +
            "    \"updated_at\": \"2024-10-12T08:52:32.258509\",\n" +
            "    \"last_login_at\": \"2024-10-12T08:52:32.254521\"\n" +
            "}";
        return objectMapper.readTree(data.getBytes(StandardCharsets.UTF_8));
    }

    @GetMapping("folders")
    public JsonNode folders() throws IOException {
        String data = "[\n" +
            "    {\n" +
            "        \"name\": \"My Projects\",\n" +
            "        \"description\": \"Manage your own projects. Download and upload folders.\",\n" +
            "        \"id\": \"1f968b68-91c0-4bae-83cb-c802fd76340d\",\n" +
            "        \"parent_id\": null\n" +
            "    },\n" +
            "    {\n" +
            "        \"name\": \"Starter Projects\",\n" +
            "        \"description\": \"Starter projects to help you get started in Langflow.\",\n" +
            "        \"id\": \"8b9eb34e-2ec8-4a6d-9b1f-7abe71fdc9ed\",\n" +
            "        \"parent_id\": null\n" +
            "    }\n" +
            "]";
        return objectMapper.readTree(data.getBytes(StandardCharsets.UTF_8));
    }

    @GetMapping("folders/{path}")
    public JsonNode folders(@PathVariable(value = "path", required = false) String path) throws IOException {
        JsonNode jsonNode = objectMapper.readTree(this.getClass().getClassLoader().getResourceAsStream("folders.json" ));
        return jsonNode;
    }

    @GetMapping("variables")
    public JsonNode variables() throws IOException {
        String data = "[]";
        return objectMapper.readTree(data.getBytes(StandardCharsets.UTF_8));
    }


    @PostMapping("flows")
    public JsonNode flows() throws IOException {
        JsonNode jsonNode = objectMapper.readTree(this.getClass().getClassLoader().getResourceAsStream("flows.json" ));
        return jsonNode;
    }

    @GetMapping("store/check")
    public JsonNode check() throws IOException {
        String data = "{\"enabled\":true}";
        return objectMapper.readTree(data.getBytes(StandardCharsets.UTF_8));
    }

    @GetMapping("store/check/api_key")
    public JsonNode api_key() throws IOException {
        String data = "{\"has_api_key\":false,\"is_valid\":false}";
        return objectMapper.readTree(data.getBytes(StandardCharsets.UTF_8));
    }

    @RequestMapping(value = "all", method = RequestMethod.GET)
    public JsonNode all(@RequestParam(defaultValue = "false") boolean force_refresh) throws IOException {
        JsonNode jsonNode = objectMapper.readTree(this.getClass().getClassLoader().getResourceAsStream("all.json" ));
        return jsonNode;
    }

    @GetMapping("config")
    public JsonNode config() throws IOException {
        String data = "{\"frontend_timeout\":0}";
        return objectMapper.readTree(data.getBytes(StandardCharsets.UTF_8));
    }

    @GetMapping("version")
    public JsonNode version() throws IOException {
        String data = "{\"version\":\"1.0.5\",\"package\":\"Langflow\"}";
        return objectMapper.readTree(data.getBytes(StandardCharsets.UTF_8));
    }
}
