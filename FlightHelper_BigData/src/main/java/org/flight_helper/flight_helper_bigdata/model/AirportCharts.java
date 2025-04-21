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
 * æœºåœºæŽ’è¡Œæ¦œè¡¨
 * </p>
 *
 * @author xuezhihengg
 * @since 2025-03-12
 */
@Getter
@Setter
@ToString
@TableName("airport_charts")
public class AirportCharts implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * æœºåœºå��
     */
    @TableField("name")
    private String name;

    /**
     * å�žå��é‡�
     */
    @TableField("throughput")
    private Integer throughput;

    /**
     * æ˜¯å�¦ä¸ºæœ€æ–°æ•°æ�®
     */
    @TableField("latest")
    private Byte latest;

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
     * ç»Ÿè®¡ç±»åž‹ï¼ˆæ—¥æ¦œã€�æœˆæ¦œã€�å¹´æ¦œ...ï¼‰
     */
    @TableField("type")
    private String type;
}
