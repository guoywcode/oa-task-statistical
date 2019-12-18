package com.guoyw.oa.task_statistical.utils;

import java.util.UUID;

/**
 * @author: guoyw
 * create: 2019-12-18 10:58
 **/

public class UuidUtils{
  
  public static String getUuid() {
    return UUID.randomUUID().toString();
  }
  
  public static String getInseparateUuid() {
    return UUID.randomUUID().toString().replace("-", "");
  }
  
  public static String getUUID16() {
    UUID uuid = UUID.randomUUID();
    return alignRight(Long.toHexString(uuid.getMostSignificantBits()), 16, '0')
      + alignRight(Long.toHexString(uuid.getLeastSignificantBits()), 16, '0');
  }
  
  // 右对齐操作
  private static String alignRight(Object o, int width, char c) {
    if (null == o) {
      return null;
    }
    String s = o.toString();
    int len = s.length();
    if (len >= width) {
      return s;
    }
    return new StringBuilder().append(dup(c, width - len)).append(s).toString();
  }
  
  // 补全操作
  private static String dup(char c, int num) {
    if (c == 0 || num < 1) {
      return "";
    }
    StringBuilder sb = new StringBuilder(num);
    for (int i = 0; i < num; i++){
      sb.append(c);
    }
    return sb.toString();
  }
}
