package net.xdclass.online_xdclass.mapper;

import java.util.List;
import java.util.Map;
import net.xdclass.online_xdclass.model.VideoOrder;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 光影
 * @since 2021-03-22
 */
public interface VideoOrderMapper{

  VideoOrder findByUserIdAndVideoIdAndState(@Param("userId") Integer userId, @Param("videoId") Integer videoId, @Param("state") Integer state);

  int saveOrder(VideoOrder videoOrder);

  List<Map<String, Object>> findListByUserId(@Param("userId") Integer userId);
}
