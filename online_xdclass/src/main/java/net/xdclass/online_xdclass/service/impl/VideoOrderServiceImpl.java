package net.xdclass.online_xdclass.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.xdclass.online_xdclass.exception.BusinessException;
import net.xdclass.online_xdclass.mapper.EpisodeMapper;
import net.xdclass.online_xdclass.mapper.PlayRecordMapper;
import net.xdclass.online_xdclass.mapper.VideoMapper;
import net.xdclass.online_xdclass.mapper.VideoOrderMapper;
import net.xdclass.online_xdclass.model.Episode;
import net.xdclass.online_xdclass.model.PlayRecord;
import net.xdclass.online_xdclass.model.Video;
import net.xdclass.online_xdclass.model.VideoOrder;
import net.xdclass.online_xdclass.service.VideoOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

  private final EpisodeMapper episodeMapper;

  private final PlayRecordMapper playRecordMapper;

  /**
   * 下单操作
   * @param userId
   * @param videoId
   * @return
   */
  @Override
  @Transactional
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
    Optional.of(rows).filter(u->u == 1).orElseThrow(()->new BusinessException("下单失败"));

    Episode episode = episodeMapper.findFirstEpisodeByVideoId(videoId);
    Optional.ofNullable(episode).orElseThrow(()->new BusinessException("视频没有集信息,请运营人员检查"));
    PlayRecord playRecord = new PlayRecord();
    playRecord.setCreateTime(new Date());
    playRecord.setEpisodeId(episode.getId());
    playRecord.setCurrentNum(episode.getNum());
    playRecord.setUserId(userId);
    playRecord.setVideoId(videoId);

    int rowsPlay = playRecordMapper.save(playRecord);

    Optional.of(rowsPlay).filter(u->u == 1).orElseThrow(()->new BusinessException("记录保存失败"));

    return rows;
  }

  @Override
  public List<Map<String, Object>> findListByUserId(Integer userId) {
    Optional.ofNullable(userId).orElseThrow(()->new BusinessException("user_id为空"));
    List<Map<String, Object>> orderList = videoOrderMapper.findListByUserId(userId);
    Optional.ofNullable(orderList).orElseThrow(()->new BusinessException("订单列表为空，查询失败"));
    return orderList;
  }
}
