package net.xdclass.online_xdclass.exception;

import com.alibaba.fastjson.JSONObject;
import java.sql.SQLException;
import lombok.extern.slf4j.Slf4j;
import net.xdclass.online_xdclass.util.ResultVO;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

/**
 * @Description: TODO
 * @Author: M1NGc
 * @Date: 2021-03-10 15:22
 */
@Slf4j
@RestControllerAdvice
public class DefaultExceptionAdvice {

  /**
   * IllegalArgumentException异常处理返回json 返回状态码:400
   */
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler({IllegalArgumentException.class})
  public ResultVO badRequestException(IllegalArgumentException e) {
    return defHandler("参数解析失败", e);
  }

  /**
   * 返回状态码:405
   */
  @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
  @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
  public ResultVO handleHttpRequestMethodNotSupportedException(
      HttpRequestMethodNotSupportedException e) {
    return defHandler("不支持当前请求方法", e);
  }

  /**
   * 返回状态码:415
   */
  @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
  @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
  public ResultVO handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
    return defHandler("不支持当前媒体类型", e);
  }

  /**
   * SQLException sql异常处理 返回状态码:500
   */
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler({SQLException.class})
  public ResultVO handleSQLException(SQLException e) {
    return defHandler("服务运行SQLException异常", e);
  }

  /**
   * BusinessException 业务异常处理 返回状态码:500
   */
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(BusinessException.class)
  public ResultVO handleException(BusinessException e) {
    return defHandler(e.getLocalizedMessage(), e);
  }

  @ResponseStatus(HttpStatus.OK)
  @ExceptionHandler({HttpClientErrorException.class})
  public ResultVO httpClientErrorException(HttpClientErrorException e) {
    log.error("httpClientErrorException", e);
    return JSONObject.parseObject(e.getResponseBodyAsString(), ResultVO.class);
  }

  /**
   * 所有异常统一处理 返回状态码:500
   */
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Exception.class)
  public ResultVO handleException(Exception e) {
    return defHandler("未知异常", e);
  }


  public ResultVO defHandler(String msg, Exception e) {
    log.error(msg, e);
    return ResultVO.failed(msg);
  }

}