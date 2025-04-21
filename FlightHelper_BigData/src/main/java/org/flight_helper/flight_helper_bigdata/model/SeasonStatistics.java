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
 * æ·¡æ—ºå­£æ•°æ�®åˆ†æž�è¡¨
 * </p>
 *
 * @author xuezhihengg
 * @since 2025-03-12
 */
@Getter
@Setter
@ToString
@TableName("season_statistics")
public class SeasonStatistics implements Serializable {

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
     * æ·¡å­£ï¼ˆ1ï¼‰ã€�å¹³å­£ï¼ˆ2ï¼‰ã€�æ—ºå­£ï¼ˆ3ï¼‰
     */
    @TableField("type")
    private Integer type;

    /**
     * æŸ�ä¸ªæœˆå¹³å�‡ç¥¨ä»·æ»¡è¶³æ—ºå­£ï¼ˆè¶…è¿‡å¹´å¹³å�‡ç¥¨ä»·25%ï¼‰ã€�å¹³å­£ï¼ˆåœ¨å¹´å¹³å�‡ç¥¨ä»·æ­£è´Ÿ10%ï¼‰ã€�æ·¡å­£ï¼ˆå°‘äºŽå¹´å¹³å�‡ç¥¨ä»·20%ï¼‰çš„æ�¡ä»¶çš„æœˆä»½ä¸ªæ•°
     */
    @TableField("count")
    private Integer count;

    /**
     * å¹³å�‡ä»·æ ¼
     */
    @TableField("avg_price")
    private Integer avgPrice;

    /**
     * æœ€ä½Žä»·æ ¼
     */
    @TableField("lowest_price")
    private Integer lowestPrice;

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
