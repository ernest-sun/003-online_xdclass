package net.xdclass.online_xdclass.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author 光影
 * @since 2021-03-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("episode")
public class Episode implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 集标题
     */
    private String title;

    /**
     * 第几集,全局顺序
     */
    private Integer num;

    /**
     * 顺序，章里面的顺序
     */
    private Integer ordered;

    /**
     * 播放地址
     */
    private String playUrl;

    /**
     * 章节主键id
     */
    private Integer chapterId;

    /**
     * 0表示免费，1表示首付
     */
    private Integer free;

    /**
     * 视频id
     */
    private Integer videoId;

    /**
     * 创建时间
     */
    private Date createTime;


}
