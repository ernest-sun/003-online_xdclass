package net.xdclass.online_xdclass.controller;


import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.xdclass.online_xdclass.service.VideoOrderService;
import net.xdclass.online_xdclass.util.ResultVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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
@RequestMapping("/api/v1/pri/Order")
@RequiredArgsConstructor
public class VideoOrderController {

  private final VideoOrderService videoOrderService;

  @PostMapping("/save")
  public ResultVO<Integer> save(@RequestBody Map<String, Object> videoOrder, HttpServletRequest request) {
    try {
      Integer userId = (Integer) request.getAttribute("user_id");
      Integer videoId = (Integer) videoOrder.get("video_id");
      Integer rows = videoOrderService.save(userId, videoId);
      return ResultVO.succeed(rows, "下单成功");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ResultVO.failed(500, e.getMessage());
    }
  }
}

