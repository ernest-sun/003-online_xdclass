package net.xdclass.online_xdclass.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.xdclass.online_xdclass.config.CacheKeyManager;
import net.xdclass.online_xdclass.exception.BusinessException;
import net.xdclass.online_xdclass.mapper.VideoMapper;
import net.xdclass.online_xdclass.service.VideoService;
import net.xdclass.online_xdclass.util.BaseCache;
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
public class VideoServiceImpl implements VideoService {

  private final VideoMapper videoMapper;

  private final BaseCache baseCache;

  /**
   * 视频列表
   *
   * @return
   */
  @Override
  public List<Map<String, Object>> listVideo() {
    try {
      Object cacheObj = baseCache.getTenMinuteCache().get(CacheKeyManager.INDEX_VIDEO_LIST,
          videoMapper::listVideo);
      Optional.ofNullable(cacheObj).orElseThrow(() -> new BusinessException("视频列表不存在"));
      return (List<Map<String, Object>>) cacheObj;
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    return null;
  }

  /**
   * 轮播图列表
   *
   * @return
   */
  @Override
  public List<Map<String, Object>> listBanner() {
    try {
      Object cacheObj = baseCache.getTenMinuteCache().get(CacheKeyManager.INDEX_BANNER_KEY, () -> {
        List<Map<String, Object>> listBanner = videoMapper.listBanner();
        return listBanner;
      });
      Optional.ofNullable(cacheObj).orElseThrow(() -> new BusinessException("轮播图列表不存在"));
      return (List<Map<String, Object>>) cacheObj;
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    return null;
  }

  @Override
  public List<Map<String, Object>> findDetailById(int videoId) {
    String videoCacheKey = String.format(CacheKeyManager.VIDEO_DETAIL, videoId);
    try {
      Object cacheObj = baseCache.getOneHourCache()
          .get(videoCacheKey, () -> videoMapper.findDetailById(videoId));
      Optional.ofNullable(cacheObj).orElseThrow(() -> new BusinessException("视频详情列表不存在"));
      return (List<Map<String, Object>>) cacheObj;
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    return null;
  }
}
