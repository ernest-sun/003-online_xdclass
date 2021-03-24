package net.xdclass.online_xdclass.util;

import cn.hutool.http.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.xdclass.online_xdclass.enums.ResultEnum;


/**
 * @Description: TODO
 * @Author: M1NGc
 * @Date: 2021-03-10 15:22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultVO<T> {

  private T data;
  private Integer code;
  private String msg;

  public static <T> ResultVO<T> succeed(String msg) {
    return of(null, HttpStatus.HTTP_OK, msg);
  }

  public static <T> ResultVO<T> succeed(T data, String msg) {
    return of(data, HttpStatus.HTTP_OK, msg);
  }

  public static <T> ResultVO<T> succeed(T data) {
    return of(data, HttpStatus.HTTP_OK, "");
  }

  public static <T> ResultVO<T> of(T data, Integer code, String msg) {
    return new ResultVO(data, code, msg);
  }

  public static <T> ResultVO<T> failed(String msg) {
    return of(null, HttpStatus.HTTP_INTERNAL_ERROR, msg);
  }

  public static <T> ResultVO<T> failed(T model, String msg) {
    return of(model, HttpStatus.HTTP_INTERNAL_ERROR, msg);
  }

  public static <T> ResultVO<T> failed(Integer code, String msg) {
    return of(null, code, msg);
  }

  public static ResultVO error(ResultEnum resultEnum) {
    return new ResultVOBuilder().code(resultEnum.getCode()).msg(resultEnum.getMsg()).build();
  }
}
