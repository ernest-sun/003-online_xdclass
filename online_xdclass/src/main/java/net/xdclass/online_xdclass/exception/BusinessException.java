package net.xdclass.online_xdclass.exception;

/**
 * @Description: TODO
 * @Author: M1NGc
 * @Date: 2021-03-10 15:46
 */
public class BusinessException extends RuntimeException {

  private static final long serialVersionUID = 6610083281801529147L;

  public BusinessException(String message) {
    super(message);
  }
}
