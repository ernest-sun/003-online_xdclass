package net.xdclass.online_xdclass.service.impl;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.xdclass.online_xdclass.exception.BusinessException;
import net.xdclass.online_xdclass.mapper.VideoMapper;
import net.xdclass.online_xdclass.mapper.VideoOrderMapper;
import net.xdclass.online_xdclass.model.Video;
import net.xdclass.online_xdclass.model.VideoOrder;
import net.xdclass.online_xdclass.service.VideoOrderService;
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
public class VideoOrderServiceImpl implements VideoOrderService {

  private final VideoOrderMapper videoOrderMapper;

  private final VideoMapper videoMapper;

  /**
   * 下单操作
   * @param userId
   * @param videoId
   * @return
   */
  @Override
  public Integer save(Integer userId, Integer videoId) {
    Optional.ofNullable(userId).orElseThrow(()->new BusinessException("user_id为空"));
    Optional.ofNullable(videoId).orElseThrow(()->new BusinessException("video_id为空"));
    VideoOrder videoOrder = videoOrderMapper.findByUserIdAndVideoIdAndState(userId, videoId, 1);

    Optional.ofNullable(videoOrder).ifPresent(u->{
      throw new BusinessException("订单已经存在");
    });

    Video video = videoMapper.findById(videoId);

    VideoOrder newVideoOrder = new VideoOrder();
    newVideoOrder.setCreateTime(new Date());
    newVideoOrder.setOutTradeNo(UUID.randomUUID().toString());
    newVideoOrder.setState(1);
    newVideoOrder.setTotalFee(video.getPrice());
    newVideoOrder.setUserId(userId);
    newVideoOrder.setVideoId(videoId);
    newVideoOrder.setVideoImg(video.getCoverImg());
    newVideoOrder.setVideoTitle(video.getTitle());

    int rows = videoOrderMapper.saveOrder(newVideoOrder);
    return rows;
  }
}
