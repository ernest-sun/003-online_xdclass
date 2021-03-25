package net.xdclass.online_xdclass.service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 光影
 * @since 2021-03-22
 */
public interface VideoService {

  List<Map<String, Object>> listVideo();

  List<Map<String, Object>> listBanner();

  List<Map<String, Object>> findDetailById(int videoId);
}
