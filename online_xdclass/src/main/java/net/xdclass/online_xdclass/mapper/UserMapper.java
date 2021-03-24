package net.xdclass.online_xdclass.mapper;

import java.util.Map;
import net.xdclass.online_xdclass.model.User;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 光影
 * @since 2021-03-22
 */
public interface UserMapper {

  Map<String, Object> findByPhone(@Param("phone") String phone);

  int save(User user);

  User findByPhoneAndPwd(@Param("phone") String phone, @Param("pwd") String pwd);
}
