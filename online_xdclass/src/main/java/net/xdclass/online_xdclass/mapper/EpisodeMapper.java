package net.xdclass.online_xdclass.mapper;

import net.xdclass.online_xdclass.model.Episode;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 光影
 * @since 2021-03-22
 */
public interface EpisodeMapper{

  Episode findFirstEpisodeByVideoId(@Param("videoId") int videoId);

}
