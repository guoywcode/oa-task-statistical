package com.guoyw.oa.task_statistical.controller;

import com.guoyw.oa.task_statistical.biz.TaskBiz;
import com.guoyw.oa.task_statistical.dto.TaskDto;
import com.guoyw.oa.task_statistical.dto.TaskStatusDto;
import com.guoyw.oa.task_statistical.entity.Task;
import com.guoyw.oa.task_statistical.entity.User;
import com.guoyw.oa.task_statistical.vo.TaskVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author: guoyw
 * create: 2019-12-18 14:01
 **/
@RestController
@RequestMapping("/api/task")
@Api(tags = "任务管理接口")
public class TaskController{
  
  @Autowired
  private TaskBiz taskBiz;
  
  @ApiOperation("获取用户信息")
  @RequestMapping(path = "/getUserInfo", method = RequestMethod.GET)
  public User getUserInfo(){
    return taskBiz.getUserInfo();
  }
  
  @ApiOperation("获取用户信息-根据用户名称")
  @RequestMapping(path = "/getUserInfoByIdName", method = RequestMethod.GET)
  public User getUserInfoByIdName(String idName){
    return taskBiz.getUserInfoByIdName(idName);
  }
  
  @ApiOperation("添加用户")
  @RequestMapping(path = "/addUser",method = RequestMethod.POST)
  public void addUser(@RequestBody Map<String,String> idNameMap){
    taskBiz.createUser(idNameMap.get("idName"));
  }
  
  @ApiOperation("添加||更新 任务记录")
  @RequestMapping(path = "/replaceTask",method = RequestMethod.PUT)
  public void replaceTask(@RequestBody TaskDto taskDto){
    taskBiz.replaceTask(taskDto);
  }
  
  @ApiOperation("编辑完成状态")
  @RequestMapping(path = "/changeTaskStatus",method = RequestMethod.PUT)
  public void changeTaskStatus(@RequestBody TaskStatusDto taskStatusDto){
    taskBiz.changeTaskStatus(taskStatusDto);
  }
  
  @ApiOperation("获取我的任务列表")
  @RequestMapping(path = "/getMyTaskList", method = RequestMethod.GET)
  public List<Task> getMyTaskList(@RequestParam String userUuid){
    return taskBiz.getMyTaskList(userUuid);
    //return taskService.getMyCurrentTaskList(userUuid);
  }
  
  @ApiOperation("获取任务列表-管理员")
  @RequestMapping(path = "/getCurrentTaskList", method = RequestMethod.GET)
  public List<TaskVo> getCurrentTaskList(){
    return taskBiz.getCurrentTaskList();
  }
  
}
