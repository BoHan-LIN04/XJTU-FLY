package org.flight_helper.flight_helper_bigdata.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * <p>
 * ç»�å�œæ¬¡æ•°ç»Ÿè®¡
 * </p>
 *
 * @author xuezhihengg
 * @since 2025-03-12
 */
@Getter
@Setter
@ToString
@TableName("stop_statistics")
public class StopStatistics implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * å‡ºå�‘åŸŽå¸‚
     */
    @TableField("departure_city")
    private String departureCity;

    /**
     * åˆ°è¾¾åŸŽå¸‚
     */
    @TableField("arrival_city")
    private String arrivalCity;

    /**
     * ç»�å�œæ¬¡æ•°
     */
    @TableField("times")
    private Integer times;

    /**
     * æ€»è®¡
     */
    @TableField("count")
    private Integer count;

    /**
     * æ•°æ�®ç»Ÿè®¡å¼€å§‹æ—¶é—´
     */
    @TableField("start_time")
    private LocalDateTime startTime;

    /**
     * æ•°æ�®ç»Ÿè®¡ç»“æ�Ÿæ—¶é—´
     */
    @TableField("end_time")
    private LocalDateTime endTime;

    /**
     * æ›´æ–°æ—¶é—´
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

    /**
     * æ˜¯å�¦ä¸ºæœ€æ–°æ•°æ�®
     */
    @TableField("latest")
    private Byte latest;
}
