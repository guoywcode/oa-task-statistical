package com.guoyw.oa.task_statistical.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: guoyw
 * create: 2019-12-06 11:35
 **/
@Data
public class TaskStatusDto implements Serializable{
  
  @ApiModelProperty("任务uuid")
  private String taskUuid;
  
  @ApiModelProperty("完成")
  private boolean achieve;
  
}
