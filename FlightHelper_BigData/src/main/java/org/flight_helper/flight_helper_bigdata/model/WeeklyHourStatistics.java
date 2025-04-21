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
 * æ˜ŸæœŸä¸Žæ—¶é—´æ®µå…±å�Œå†³å®šçš„ç»Ÿè®¡ä¿¡æ�¯
 * </p>
 *
 * @author xuezhihengg
 * @since 2025-03-12
 */
@Getter
@Setter
@ToString
@TableName("weekly_hour_statistics")
public class WeeklyHourStatistics implements Serializable {

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
     * æ˜ŸæœŸå‡ 
     */
    @TableField("day_of_week")
    private Integer dayOfWeek;

    /**
     * å°�æ—¶æ®µ
     */
    @TableField("hour")
    private Integer hour;

    /**
     * æœ€ä½Žç¥¨ä»·
     */
    @TableField("lowest_price")
    private Integer lowestPrice;

    /**
     * å¹³å�‡ç¥¨ä»·
     */
    @TableField("avg_price")
    private Integer avgPrice;

    /**
     * å�žå��é‡�
     */
    @TableField("throughput")
    private Integer throughput;

    /**
     * å®¢æµ�é‡�
     */
    @TableField("passenger_flow")
    private Integer passengerFlow;

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
