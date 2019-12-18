package com.guoyw.oa.task_statistical.biz;

import com.guoyw.oa.task_statistical.dto.TaskDto;
import com.guoyw.oa.task_statistical.dto.TaskStatusDto;
import com.guoyw.oa.task_statistical.entity.Task;
import com.guoyw.oa.task_statistical.entity.User;
import com.guoyw.oa.task_statistical.exception.UserException;
import com.guoyw.oa.task_statistical.service.TaskService;
import com.guoyw.oa.task_statistical.service.UserService;
import com.guoyw.oa.task_statistical.utils.IpUtils;
import com.guoyw.oa.task_statistical.vo.TaskVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author: guoyw
 * create: 2019-12-18 14:11
 **/
@Component
@Slf4j
public class TaskBiz{
  
  @Autowired
  private UserService userService;
  @Autowired
  private TaskService taskService;
  @Autowired
  private Environment environment;
  
  @Transactional
  public User getUserInfo(){
    String clientIp = IpUtils.getClientIpAddress();
    User dbUser = userService.getByIp(clientIp);
    if(dbUser == null)
      throw new UserException("UNREGISTERED");
    
    this.updateLastLoginTime(dbUser.getUuid());
    return dbUser;
  }
  
  @Transactional
  public User getUserInfoByIdName(String idName){
    User dbUser = userService.getByIdName(idName);
    if(dbUser == null)
      throw new UserException("USER-ERROR-002");
    if(!dbUser.isIdNameLogin())
      throw new UserException("USER-ERROR-003");
    this.updateLastLoginTime(dbUser.getUuid());
    return dbUser;
  }
  
  private void updateLastLoginTime(String userUuid){
    User dbUser = userService.getByUuid(userUuid);
    dbUser.setLastLoginTime(new Date().getTime());
    userService.update(dbUser);
  }
  
  @Transactional
  public User createUser(String idName){
    String clientIp = IpUtils.getClientIpAddress();
    
    User dbUser = userService.getByIp(clientIp);
    if(dbUser != null)
      throw new UserException("USER-EXISTS-001");
    
    User varUser = new User();
    varUser.setIdName(idName);
    varUser.setIpAddress(clientIp);
    return userService.create(varUser);
  }
  
  @Transactional
  public Task replaceTask(TaskDto taskDto){
    if(taskDto == null)
      throw new UserException("PARAM-002");
    if(taskDto.getUserUuid() == null)
      throw new UserException("PARAM-003");
    
    Task resultTask = null;
    Task varTask = new Task();
    if(taskDto.getTaskUuid() != null){
      Task dbTack = taskService.getByUuid(taskDto.getTaskUuid());
      if(dbTack == null)
        throw new UserException("PARAM-005");
      if(dbTack.isLocking())
        throw new UserException("PARAM-004");
      
      BeanUtils.copyProperties(dbTack,varTask);
      BeanUtils.copyProperties(taskDto, varTask);
      varTask.setUuid(taskDto.getTaskUuid());
      resultTask = taskService.update(varTask);
      
    }else{
      BeanUtils.copyProperties(taskDto, varTask);
      resultTask = taskService.create(varTask);
    }
    
    return resultTask;
  }
  
  @Transactional
  public void changeTaskStatus(TaskStatusDto taskStatusDto){
    if(taskStatusDto == null)
      throw new UserException("PARAM-002");
    if(taskStatusDto.getTaskUuid() == null)
      throw new UserException("PARAM-005");
    
    Task dbTask = taskService.getByUuid(taskStatusDto.getTaskUuid());
    
    Task varTask = new Task();
    BeanUtils.copyProperties(dbTask,varTask);
    varTask.setUuid(taskStatusDto.getTaskUuid());
    varTask.setAchieve(taskStatusDto.isAchieve());
    if(taskStatusDto.isAchieve())
      varTask.setLocking(true);
    else {
      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
      String time = df.format(new Date());
      
      long newTime = 0;
      try{
        newTime = df.parse(time).getTime()/1000;
      }catch(ParseException e){
        e.printStackTrace();
      }
      
      if(newTime - dbTask.getTaskEndTime() <= 0)
        varTask.setLocking(false);
    }
    
    taskService.update(varTask);
  }
  
  
  public List<TaskVo> getCurrentTaskList(){
    String administratorIp = environment.getProperty("yioks.task.administrator.ip");
    String[] administratorIps = administratorIp.split(",");
    String clientIp = IpUtils.getClientIpAddress();
    log.info("clientIp: "+clientIp);
    if(!Arrays.asList(administratorIps).contains(clientIp))
      throw new UserException("PERMISSION");
    
    List<User> dbUsers = userService.getAll();
    List<TaskVo> taskVos = new ArrayList<>();
    for(User user : dbUsers){
      TaskVo varTaskVo = new TaskVo();
      List<Task> taskList = taskService.getMyCurrentTaskList(user.getUuid());
      varTaskVo.setUser(user);
      varTaskVo.setTasks(taskList);
      taskVos.add(varTaskVo);
    }
    return taskVos;
  }
  
  
  public List<Task> getMyTaskList(String userUuid){
    User dbUser = userService.getByUuid(userUuid);
    if(dbUser == null )
      throw new UserException("LOGIN-TIMEOUT");
    
    String clientIp = IpUtils.getClientIpAddress();
    if(!dbUser.getIpAddress().contains(clientIp) && !dbUser.isIdNameLogin() )
      throw new UserException("LOGIN-TIMEOUT");
    
    this.updateLastLoginTime(dbUser.getUuid());
    return taskService.getMyCurrentTaskList(userUuid);
  }
  
}
