package com.guoyw.oa.task_statistical.repository;

import com.guoyw.oa.task_statistical.entity.Task;
import com.guoyw.oa.task_statistical.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: guoyw
 * create: 2019-12-18 11:51
 **/
@Repository
public interface TaskRepository extends JpaRepository<Task,Long>, CrudRepository<Task,Long>{
  
  Task findOneByUuid(String uuid);
  
  // 根据添加时间查询最后一条
  Task findFirstByUserUuidOrderByCreatedAtDesc(String userUuid);
  
  @Query(value = "select t.* from task t where t.user_uuid=?1 and (t.achieve=?2 or t.task_end_time>=?3) order by t.achieve ,t.task_end_time asc ",nativeQuery=true)
  List<Task> getMyCurrentTaskList(String userUuid, boolean achieve, long newTime);
  
  List<Task> findByUserUuidAndAchieve(String userUuid,boolean achieve);
}
