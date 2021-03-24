package net.xdclass.online_xdclass.service.impl;


import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.xdclass.online_xdclass.exception.BusinessException;
import net.xdclass.online_xdclass.mapper.UserMapper;
import net.xdclass.online_xdclass.model.User;
import net.xdclass.online_xdclass.service.UserService;
import net.xdclass.online_xdclass.util.CommonUtils;
import net.xdclass.online_xdclass.util.JWTUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 光影
 * @since 2021-03-22
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserMapper userMapper;

  /**
   * 根据电话号码查找用户
   *
   * @param phone
   * @return
   */
  @Override
  public Map<String, Object> findByPhone(String phone) {
    return Optional.ofNullable(userMapper.findByPhone(phone))
        .orElseThrow(() -> new BusinessException("用户不存在"));
  }

  /**
   * 用户新增
   *
   * @param userInfo
   * @return
   */
  @Override
  public int save(Map<String, String> userInfo) {
    Optional.ofNullable(userInfo)
        .filter(u -> u.containsKey("name") && u.containsKey("pwd") && u.containsKey("phone"))
        .orElseThrow(() -> new BusinessException("用户信息不完整"));
    Optional.ofNullable(userInfo)
        .filter(u -> userMapper.findByPhone(u.get("phone")) == null)
        .orElseThrow(() -> new BusinessException("电话号码不能重复"));
    return userMapper.save(parseToUser(userInfo));
  }

  @Override
  public String findByPhoneAndPwd(Map<String, Object> loginUser) {
    Optional.ofNullable(loginUser)
        .filter(u -> u.containsKey("phone") && u.containsKey("pwd"))
        .orElseThrow(() -> new BusinessException("电话号码和密码不能为空"));
    User user = userMapper.findByPhoneAndPwd(loginUser.get("phone").toString(),
        CommonUtils.MD5(loginUser.get("pwd").toString()));
    Optional.ofNullable(user)
        .orElseThrow(() -> new BusinessException("密码错误，登录失败"));
    //返回token
    return JWTUtils.geneJsonWebToken(user);
  }

  /**
   * 解析user对象
   *
   * @param userInfo
   * @return
   */
  private User parseToUser(Map<String, String> userInfo) {
    User user = new User();
    user.setName(userInfo.get("name"));
    user.setPhone(userInfo.get("phone"));
    user.setHeadImg(getRandomImg());
    user.setCreateTime(new Date());
    String pwd = userInfo.get("pwd");
    //MD5加密
    user.setPwd(CommonUtils.MD5(pwd));
    return user;
  }

  /**
   * 放在CDN的随机用户头像
   */
  private static final String[] headImg = {
      "https://xd-video-pc-img.oss-cn-beijing.aliyuncs.com/xdclass_pro/default/head_img/12.jpeg",
      "https://xd-video-pc-img.oss-cn-beijing.aliyuncs.com/xdclass_pro/default/head_img/11.jpeg",
      "https://xd-video-pc-img.oss-cn-beijing.aliyuncs.com/xdclass_pro/default/head_img/13.jpeg",
      "https://xd-video-pc-img.oss-cn-beijing.aliyuncs.com/xdclass_pro/default/head_img/14.jpeg",
      "https://xd-video-pc-img.oss-cn-beijing.aliyuncs.com/xdclass_pro/default/head_img/15.jpeg"
  };

  /**
   * 随机获取一个默认的头像
   *
   * @return
   */
  private String getRandomImg() {
    int size = headImg.length;
    Random random = new Random();
    int index = random.nextInt(size);
    return headImg[index];
  }
}
