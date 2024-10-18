package com.example.demo.demos.web.flow;

import com.example.demo.dao.bo.flow.Flow;
import com.example.demo.demos.web.execption.FlowNotFoundException;
import com.example.demo.service.FlowOperateService;
import com.example.demo.service.FlowStartProjectsInitService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;


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
@Slf4j
@RestController
@RequestMapping("/api/v1/")
public class FlowController {

  @Autowired
  private FlowOperateService flowOperateService;

  @Autowired
  private FlowStartProjectsInitService flowStartProjectsInitService;

  @Autowired
  private ObjectMapper objectMapper;

  @GetMapping("auto_login")
  public JsonNode autoLogin() throws IOException {
    String data = "{\n"
        + "    \"access_token\": \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI1NjAyMWE0Yy0xYWZmLTRlMGQtYWZlYy02NWUwZGNlZGJkMmMiLCJ0eXBlIjoiYWNjZXNzIiwiZXhwIjoxNzYwMjU5MTUyfQ.sckNzbPo9fTvrx3FgLms00K4LrP0mMtQGmWXDRoDZVk\",\n"
        + "    \"refresh_token\": null,\n" + "    \"token_type\": \"bearer\"\n" + "}";
    return objectMapper.readTree(data.getBytes(StandardCharsets.UTF_8));
  }

  @GetMapping("health")
  public JsonNode health() throws IOException {
    String data = "{\"status\":\"ok\"}";
    return objectMapper.readTree(data.getBytes(StandardCharsets.UTF_8));
  }


  @GetMapping("variables")
  public JsonNode variables() throws IOException {
    String data = "[]";
    return objectMapper.readTree(data.getBytes(StandardCharsets.UTF_8));
  }


  @PostMapping("flows")
  @ResponseStatus(HttpStatus.CREATED)
  public Flow createFlow(@RequestBody Flow flowVo) throws IOException {
//    log.info("flowCreate flowVo: {}", flowVo);
    Flow flow = flowOperateService.createFlow(flowVo);
    log.info("flowCreate: {}", flow);
    return flow;
  }

  @PostMapping("flows/batch")
  @ResponseStatus(HttpStatus.CREATED)
  public List<Flow> createFlows(@RequestBody List<Flow> flowVos) throws IOException {
    List<Flow> flows = flowOperateService.createFlows(flowVos);
    log.info("flowCreates: {}", flows);
    return flows;
  }

  @PostMapping("flows/upload")
  public List<Flow> uploadFile(@RequestParam("file") MultipartFile file) {
    List<Flow> flows = Lists.newArrayList();
    if (file.isEmpty()) {
      return flows;
    }

    try {
      // 将文件内容解析为String
      String content = new String(file.getBytes(), StandardCharsets.UTF_8);
      JsonNode rootNode = objectMapper.readTree(content.getBytes(StandardCharsets.UTF_8));
      boolean flowsFlag = rootNode.has("flows");

      if (flowsFlag) {
        JsonNode flowNodes = rootNode.path("flows");
        flows.addAll(objectMapper.readValue(flowNodes.toString(), new TypeReference<List<Flow>>() {
        }));
      } else {
        flows.add(objectMapper.readValue(content, Flow.class));
      }
      return flowOperateService.createFlows(flows);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return flows;
  }

  @GetMapping("flows/download")
  public Map<String, List<Flow>> downloadFlows() {
    List<Flow> flows = flowOperateService.getAllFlows();
    log.info("downloadFlows: {}", flows);
    HashMap<String, List<Flow>> data = Maps.newHashMap();
    data.put("flows", flows);
    return data;
  }
  @GetMapping("flows/{flow_id}")
  public Flow getFlow(@PathVariable(value = "flow_id") String flowId) {
    UUID uuid = UUID.fromString(flowId);
    log.info("getFlow: {}", uuid);
    Flow flowById = flowOperateService.getFlowById(uuid);
    if (flowById == null) {
      log.error("Flow not found flowId: {}", uuid);
      throw new FlowNotFoundException("Flow not found");
    }
    return flowById;
  }

  @GetMapping("flows")
  public List<Flow> readFlows() {
    List<Flow> flows = flowOperateService.getAllFlows();
    log.info("readFlows: {}", flows);
    return flows;
  }

  @PatchMapping("flows/{flow_id}")
  public Flow updateFlow(@PathVariable(value = "flow_id") String flowId, @RequestBody Flow flowVo) {
    UUID uuid = UUID.fromString(flowId);
    flowVo.setId(uuid);
    log.info("updateFlow: {}", flowVo);
    Flow flow = flowOperateService.updateFlow(flowVo);
    if (flow == null) {
      throw new FlowNotFoundException("Flow not found");
    }
    return flow;
  }

  @DeleteMapping("flows/")
  public JsonNode deleteFlow(@RequestBody List<String> flowIds) throws IOException {
    List<UUID> ids = flowIds.stream().map(UUID::fromString).collect(Collectors.toList());
    log.info("deleteFlow ids: {}", ids);
    Boolean res = flowOperateService.deleteFlow(ids);
    if (res == null || !res) {
      throw new FlowNotFoundException("Flow not found");
    }
    String data = "{\"message\": \"Flow deleted successfully\"}";
    return objectMapper.readTree(data.getBytes(StandardCharsets.UTF_8));
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
  public JsonNode getAll(@RequestParam(defaultValue = "false") boolean force_refresh)
      throws IOException {
    JsonNode jsonNode =
        objectMapper.readTree(this.getClass().getClassLoader().getResourceAsStream("all.json"));
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

  @GetMapping("init_starter_projects")
  public void initStarterProjects() {
    flowStartProjectsInitService.init();
  }
}
