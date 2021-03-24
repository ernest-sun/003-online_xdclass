package net.xdclass.online_xdclass.service.impl;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.xdclass.online_xdclass.mapper.VideoMapper;
import net.xdclass.online_xdclass.service.VideoService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 光影
 * @since 2021-03-22
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {

  private final VideoMapper videoMapper;

  /**
   * 视频列表
   * @return
   */
  @Override
  public List<Map<String, Object>> listVideo() {
    return videoMapper.listVideo();
  }

  /**
   * 轮播图列表
   * @return
   */
  @Override
  public List<Map<String, Object>> listBanner() {
    return videoMapper.listBanner();
  }

  @Override
  public List<Map<String, Object>> findDetailById(int videoId) {
    return videoMapper.findDetailById(videoId);
  }
}
