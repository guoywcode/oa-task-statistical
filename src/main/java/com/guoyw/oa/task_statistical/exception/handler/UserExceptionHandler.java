package com.guoyw.oa.task_statistical.exception.handler;
import com.guoyw.oa.task_statistical.exception.UserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;


/**
  * @author: guoyw
  * created: 2019-12-18 17:36
  */

@RestControllerAdvice
public class UserExceptionHandler{
  @Autowired
  private MessageSource messageSource;
  
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private final String codeName;
  private final String massage;
  
  public UserExceptionHandler(){
    this.codeName = "code";
    this.massage = "massage";
  }
  
  @ExceptionHandler({UserException.class})
  @ResponseBody
  ResponseEntity<String> UserException(UserException throwable){
    Map<String, Object> result = new HashMap();
    this.logger.warn(throwable.getClass().getName());
    this.logger.warn(throwable.getMessage());
    
    String code = throwable.getMessage();
    String msgKey = throwable.getClass().getSimpleName() + "." + code;
//    String msg = this.messageSource.getMessage(msgKey, new Object[]{throwable.getArg1(),throwable.getArg2()}, code, Locale.getDefault());
    String msg = null;
    if (this.messageSource != null) {
      msg = this.messageSource.getMessage(msgKey, (Object[])null, code, LocaleContextHolder.getLocale());
    } else {
      msg = code;
    }
  
    result.put(this.codeName, code);
    result.put(this.massage, msg);
    
    return new ResponseEntity(result, HttpStatus.OK);
  }
  
}
