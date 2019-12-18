package com.guoyw.oa.task_statistical.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import javax.persistence.Column;
import java.io.Serializable;

/**
 * @author: guoyw
 * create: 2019-12-18 11:35
 **/
@Data
@Accessors(chain = true)
public class TaskDto implements Serializable{
  
  @ApiModelProperty("任务uuid")
  private String taskUuid;
  
  @ApiModelProperty("用户uuid")
  private String userUuid;
  
  @ApiModelProperty("任务内容")
  private String taskContent;
  
  @ApiModelProperty("任务开始时间")
  @Column
  private long taskStartTime;
  
  @ApiModelProperty("任务结束时间")
  @Column
  private long taskEndTime;
}
