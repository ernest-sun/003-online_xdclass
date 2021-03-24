package net.xdclass.online_xdclass.mapper;

import java.util.List;
import java.util.Map;
import net.xdclass.online_xdclass.model.Video;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 光影
 * @since 2021-03-22
 */
public interface VideoMapper {

  List<Map<String, Object>> listVideo();

  List<Map<String, Object>> listBanner();

  List<Map<String, Object>> findDetailById(@Param("videoId") int videoId);

  Video findById(@Param("video_id") Integer videoId);
}
