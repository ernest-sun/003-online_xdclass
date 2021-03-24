package net.xdclass.online_xdclass.controller;


import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.xdclass.online_xdclass.service.VideoService;
import net.xdclass.online_xdclass.util.ResultVO;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/api/v1/pub/video")
public class VideoController {

  private final VideoService videoService;

  @GetMapping("/all")
  public ResultVO<Map<String, Object>> video() {
    Map<String, Object> resultMap = new ConcurrentHashMap<>();
    CountDownLatch countDownLatch = new CountDownLatch(2);
    try {
      CompletableFuture.supplyAsync(videoService::listVideo)
          .thenAccept(result->{
            countDownLatch.countDown();
            resultMap.put("listVideo", result);
          })
          .exceptionally(ex->{
            countDownLatch.countDown();
            log.error(ex.getMessage(), ex);
            resultMap.put("listVideo", Maps.newConcurrentMap());
            return null;
          });
      CompletableFuture.supplyAsync(videoService::listBanner)
          .thenAccept(result->{
            countDownLatch.countDown();
            resultMap.put("listBanner", result);
          })
          .exceptionally(ex->{
            countDownLatch.countDown();
            log.error(ex.getMessage(), ex);
            resultMap.put("listBanner", Maps.newConcurrentMap());
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

  /**
   * 根据id查找视频详情,包含章，集信息
   * @param videoId
   * @return
   */
  @GetMapping("/find_detail_by_id")
  public ResultVO<List<Map<String, Object>>> findDetailById(@RequestParam(value = "video_id", required = true) int videoId){
    try {
      List<Map<String, Object>> resultList = videoService.findDetailById(videoId);
      return ResultVO.succeed(resultList);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ResultVO.failed(500, e.getMessage());
    }
  }
}

