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
 * åŸŽå¸‚æŽ’è¡Œæ¦œè¡¨
 * </p>
 *
 * @author xuezhihengg
 * @since 2025-03-12
 */
@Getter
@Setter
@ToString
@TableName("city_charts")
public class CityCharts implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * åŸŽå¸‚å��
     */
    @TableField("name")
    private String name;

    /**
     * èˆªç�­æ•°
     */
    @TableField("flights")
    private Integer flights;

    /**
     * æœºåœºæ•°
     */
    @TableField("airports")
    private Integer airports;

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
     * ç»Ÿè®¡ç±»åž‹ï¼ˆæ—¥æ¦œã€�æœˆæ¦œã€�å¹´æ¦œï¼‰
     */
    @TableField("type")
    private String type;
}
