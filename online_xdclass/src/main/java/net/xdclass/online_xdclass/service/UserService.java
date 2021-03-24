package net.xdclass.online_xdclass.service;

import java.util.Map;
import net.xdclass.online_xdclass.model.User;

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

  String findByPhoneAndPwd(Map<String, Object> loginUser);

  User findByUserId(Integer userId);
}
