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
import net.xdclass.online_xdclass.service.ChapterService;
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
@Api(tags = "章节信息")
@RequestMapping("/chapter")
@RequiredArgsConstructor
public class ChapterController {

  private final ChapterService chapterService;

  @ApiOperation("章节信息")
  @GetMapping("/all")
  public ResultVO<Map<String, Object>> chapter() {
    Map<String, Object> resultMap = new ConcurrentHashMap<>();
    CountDownLatch countDownLatch = new CountDownLatch(1);
    try {
      CompletableFuture.supplyAsync(chapterService::getChapterAll)
        .thenAccept(result->{
          countDownLatch.countDown();
          resultMap.put("chapterAll", result);
        })
        .exceptionally(ex->{
          countDownLatch.countDown();
          log.error(ex.getMessage(), ex);
          resultMap.put("chapterAll", Maps.newConcurrentMap());
          return null;
        });
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ResultVO.failed(e.getMessage());
    }
    try {
      countDownLatch.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return ResultVO.succeed(resultMap);
  }
}

