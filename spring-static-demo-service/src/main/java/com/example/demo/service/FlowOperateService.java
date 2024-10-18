package com.example.demo.service;


import com.example.demo.dao.bo.flow.Flow;
import com.example.demo.dao.flow.mapper.FlowMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class FlowOperateService {

  //  在需要使用事务管理器的地方（例如，服务类或配置类），通过 @Qualifier 注解明确指定使用哪个事务管理器
  @Autowired
  @Qualifier("flowRelateTransactionManager")
  private PlatformTransactionManager flowRelateTransactionManager;

  @Autowired
  private FlowMapper flowMapper;


  public Flow getFlowById(UUID id) {
    return flowMapper.getFlowById(id);
  }

  public List<Flow> getFlowByFolderId(UUID id) {
    return flowMapper.getFlowByFolderId(id);
  }

  @Transactional(transactionManager = "flowRelateTransactionManager")
  public Flow createFlow(Flow flowVo) {
    flowVo.setUserId(UUID.fromString("f178f98a-f3e3-445f-8507-5d32f8aeeeca"));
    Flow flowByName = flowMapper.getFlowByName(flowVo.getUserId(), flowVo.getName());
    if (flowByName != null) {
      List<Flow> flowByNameLike =
          flowMapper.getFlowByNameLike(flowVo.getUserId(), flowVo.getName());
      if (CollectionUtils.isNotEmpty(flowByNameLike)) {
        Integer num = flowByNameLike.stream().map(Flow::getName)
            .map(name -> Integer.parseInt(name.split("\\(")[1].split("\\)")[0]))
            .max(Integer::compare).orElse(0);
        flowVo.setName(flowVo.getName() + " (" + (num + 1) + ")");
      } else {
        flowVo.setName(flowVo.getName() + " (1)");
      }
    }

    Flow flowByEndpointName =
        flowMapper.getFlowByEndpointName(flowVo.getUserId(), flowVo.getEndpointName());
    if (flowByEndpointName != null) {
      List<Flow> flowByEndpointNameLike =
          flowMapper.getFlowByEndpointNameLike(flowVo.getUserId(), flowVo.getEndpointName());
      if (CollectionUtils.isNotEmpty(flowByEndpointNameLike)) {
        Integer num = flowByEndpointNameLike.stream().map(Flow::getEndpointName)
            .map(name -> Integer.parseInt(name.split("-")[-1])).max(Integer::compare).orElse(0);
        flowVo.setEndpointName(flowVo.getEndpointName() + "-" + (num + 1));
      } else {
        flowVo.setEndpointName(flowVo.getEndpointName() + "-1");
      }
    }
    flowVo.setUpdatedAt(new Date());
    flowVo.setFolderId(UUID.fromString("f3d0b3ea-bc59-41c6-9e0a-acadbc77d6ce"));
    log.info("");
    flowMapper.insertFlow(flowVo);
    return flowVo;
  }

  public List<Flow> getAllFlows() {
    return flowMapper.getAllFlows();
  }

  @Transactional(transactionManager = "flowRelateTransactionManager")
  public Flow updateFlow(Flow flow) {
    log.info("updateFlow: {}", flow.getId());
    Flow flowById = getFlowById(flow.getId());
    if (flowById == null) {
      return null;
    }
    flow.setUpdatedAt(new Date());
    flowMapper.updateFlow(flow);
    return flow;
  }

  @Transactional(transactionManager = "flowRelateTransactionManager")
  public Boolean deleteFlow(List<UUID> flowIds) {
    return flowMapper.deleteFlows(flowIds) > 0;
  }

  @Transactional(transactionManager = "flowRelateTransactionManager")
  public List<Flow> createFlows(List<Flow> flowVos) {
    flowMapper.insertFlows(flowVos);
    return flowMapper.getInsertedFlows(flowVos);
  }
}
