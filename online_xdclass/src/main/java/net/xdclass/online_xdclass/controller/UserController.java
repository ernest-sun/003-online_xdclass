package net.xdclass.online_xdclass.controller;


import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.xdclass.online_xdclass.service.UserService;
import net.xdclass.online_xdclass.util.ResultVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 光影
 * @since 2021-03-22
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/pri/user")
public class UserController {

  private final UserService userService;

  @PostMapping("/register")
  public ResultVO register(@RequestBody Map<String, String> userInfo) {
    try {
      int rows = userService.save(userInfo);
      return  ResultVO.succeed(rows, "注册成功");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ResultVO.failed(e.getMessage());
    }
  }

  @PostMapping("/login")
  public ResultVO<String> login(@RequestBody Map<String, Object> loginUser) {
    try {
      String token = userService.findByPhoneAndPwd(loginUser);
      return ResultVO.succeed(token, "登录成功");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ResultVO.failed(e.getMessage());
    }

  }

  @GetMapping("/find_by_phone")
  public ResultVO<Map<String, Object>> findByPhone(@RequestParam(value = "phone", required = true) String phone) {
    try {
      Map<String, Object> resultMap = userService.findByPhone(phone);
      return ResultVO.succeed(resultMap);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ResultVO.failed(500, e.getMessage());
    }
  }
}

