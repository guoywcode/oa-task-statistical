package com.guoyw.oa.task_statistical.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author: guoyw
 * create: 2019-12-18 14:28
 **/
public class IpUtils{
  public static String getClientIpAddress() {
    ServletRequestAttributes servletRequestAttributes =(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    HttpServletRequest httpServletRequest = servletRequestAttributes.getRequest();
    String ipAddress = httpServletRequest.getHeader("x-forwarded-for");
    if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
      ipAddress = httpServletRequest.getHeader("Proxy-Client-IP");
    }
    if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
      ipAddress = httpServletRequest.getHeader("WL-Proxy-Client-IP");
    }
    if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
      ipAddress = httpServletRequest.getRemoteAddr();
      if (ipAddress.equals("127.0.0.1")) {
        // 根据网卡取本机配置的IP
        ipAddress = getServerIpAddress();
      }
    }
    // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
    if (ipAddress != null && ipAddress.contains(",")) {
      ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
    }
    return ipAddress;
  }
  
  public static String getServerIpAddress() {
    try {
      InetAddress inet = InetAddress.getLocalHost();
      return inet.getHostAddress();
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
    return "";
  }
}
