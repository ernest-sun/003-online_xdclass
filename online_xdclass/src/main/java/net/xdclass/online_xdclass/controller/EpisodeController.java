package net.xdclass.online_xdclass.controller;


import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.xdclass.online_xdclass.service.EpisodeService;
import net.xdclass.online_xdclass.util.ResultVO;
import org.springframework.web.bind.annotation.GetMapping;
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
@Api(tags = "页面详细信息")
@RequestMapping("/episode")
@RequiredArgsConstructor
public class EpisodeController {

  private final EpisodeService episodeService;

  /**
   * 页面详细信息
   */
  @ApiOperation("页面详细")
  @GetMapping("/all")
  public ResultVO<Map<String, Object>> episode() {
    Map<String, Object> resultMap = new ConcurrentHashMap<>();
    CountDownLatch countDownLatch = new CountDownLatch(1);
    try {
      CompletableFuture.supplyAsync(episodeService::getEpisodeAll)
          .thenAccept(result->{
            countDownLatch.countDown();
            resultMap.put("episodeAll", result);
          })
          .exceptionally(ex->{
            countDownLatch.countDown();
            log.error(ex.getMessage(), ex);
            resultMap.put("episodeAll", Maps.newConcurrentMap());
            return null;
          });

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      ResultVO.failed(e.getMessage());
    }
    try {
      countDownLatch.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return ResultVO.succeed(resultMap);
  }
}

