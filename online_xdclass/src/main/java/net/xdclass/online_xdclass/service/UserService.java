package net.xdclass.online_xdclass.service;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 光影
 * @since 2021-03-22
 */
public interface UserService{
  Map<String, Object> findByPhone(String phone);

  int save(Map<String, String> userInfo);

}
