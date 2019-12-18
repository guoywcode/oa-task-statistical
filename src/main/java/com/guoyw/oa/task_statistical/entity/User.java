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
@Table(name = "user",
  indexes = {
    @Index(columnList = "uuid", name = "user_uuid", unique = true),
    @Index(columnList = "ipAddress", name = "user_ipAddress", unique = true)
  }
)
@Getter
@Setter
@ApiModel(description = "用户表")
@ToString(callSuper = true)
public class User {
  
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
  
  @ApiModelProperty("用户IP地址")
  @Column(length = 50)
  private String ipAddress;
  
  @ApiModelProperty("用户名称")
  @Column(length = 25)
  private String idName;
  
  @ApiModelProperty("用户职位")
  @Column(length = 50)
  private String position;
  
  @ApiModelProperty("允许真实姓名登陆")
  @Column()
  private boolean idNameLogin;
  
  @ApiModelProperty("最后登录时间")
  @Column()
  private long lastLoginTime;
}
