package com.guoyw.oa.task_statistical.vo;

import com.guoyw.oa.task_statistical.entity.Task;
import com.guoyw.oa.task_statistical.entity.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

/**
 * @author: guoyw
 * create: 2019-12-06 12:00
 **/
@Data
public class TaskVo implements Serializable{
  
  @ApiModelProperty("用户信息")
  private User user;
  
  @ApiModelProperty("进行中的任务")
  private List<Task> tasks;

}
