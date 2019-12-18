package com.guoyw.oa.task_statistical.service;

import com.guoyw.oa.task_statistical.entity.Task;
import com.guoyw.oa.task_statistical.repository.TaskRepository;
import com.guoyw.oa.task_statistical.utils.UuidUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author: guoyw
 * create: 2019-12-18 11:19
 **/
@Service
@Slf4j
public class TaskService {
  
  @Autowired
  private TaskRepository taskRepository;
  
  public Task getByUuid(String uuid){
    return taskRepository.findOneByUuid(uuid);
  }
  
  public Task create(Task task){
    if(StringUtils.isEmpty(task.getUuid())){
      task.setUuid(UuidUtils.getUUID16());
    }
    if(StringUtils.isEmpty(task.getCreatedAt())){
      task.setCreatedAt(new Date().getTime());
    }
    if(StringUtils.isEmpty(task.getUpdatedAt())){
      task.setUpdatedAt(new Date().getTime());
    }
    return taskRepository.save(task);
  }
  
  public Task update(Task task){
    task.setUpdatedAt(new Date().getTime());
    return taskRepository.save(task);
  }
  
  
  public List<Task> getMyCurrentTaskList(String userUuid){
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    String time = df.format(new Date());
  
    long newTime = 0;
    try{
      newTime = df.parse(time).getTime()/1000;
    }catch(ParseException e){
      e.printStackTrace();
    }
    return taskRepository.getMyCurrentTaskList(userUuid,false,newTime);
  }
  
  public List<Task> getByUserUuidAndAchieve(String userUuid,boolean achieve){
   return taskRepository.findByUserUuidAndAchieve(userUuid,achieve);
  }
}
