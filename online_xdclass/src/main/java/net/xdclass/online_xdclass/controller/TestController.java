package net.xdclass.online_xdclass.controller;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.xdclass.online_xdclass.mapper.TestMapper;
import net.xdclass.online_xdclass.model.User;
import net.xdclass.online_xdclass.util.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName:TestController Package:net.xdclass.online_xdclass.controller Description:
 *
 * @Date:2021/5/11 16:21
 * @Author:sunzheng@beiming.com
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/test")
public class TestController {

  @Autowired
  private TestMapper testMapper;

  @PostMapping("/testFindUserByMap")
  public ResultVO<User> findUserByMap(@RequestBody Map<String, String> map) {
    return ResultVO.succeed(testMapper.findByPhoneAndPwd(map), "查询成功");
  }

}
