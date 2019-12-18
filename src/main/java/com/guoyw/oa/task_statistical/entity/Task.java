package com.guoyw.oa.task_statistical.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;


/**
 * @author: guoyw
 * create: 2019-12-18 13:41
 **/
@Entity
@Table(name = "task",
  indexes = {
    @Index(columnList = "uuid", name = "user_uuid", unique = true)
  }
)
@Getter
@Setter
@ApiModel(description = "任务表")
@ToString(callSuper = true)
public class Task {
  
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column()
  @ApiModelProperty("id")
  private long id;
  
  @Column(updatable = false, nullable = false)
  @ApiModelProperty(value = "创建时间戳", example = "0")
  private long createdAt = 0;
  
  @Column(nullable = false)
  @ApiModelProperty(value = "更新时间戳", example = "0")
  private long updatedAt = 0;
  
  @ApiModelProperty("任务uuid")
  @Column(length = 32)
  private String uuid;
  
  @ApiModelProperty("用户uuid")
  @Column(length = 32)
  private String userUuid;
  
  @ApiModelProperty("任务内容")
  @Column(columnDefinition = "text")
  private String taskContent;
  
  @ApiModelProperty("任务开始时间")
  @Column
  private long taskStartTime;
  
  @ApiModelProperty("任务结束时间")
  @Column
  private long taskEndTime;
  
  @ApiModelProperty("任务结束时间")
  @Column(length = 25)
  private String taskStatus;
  
  @ApiModelProperty("完成")
  @Column
  private boolean achieve;
  
  @ApiModelProperty("锁定")
  @Column
  private boolean locking;
}
