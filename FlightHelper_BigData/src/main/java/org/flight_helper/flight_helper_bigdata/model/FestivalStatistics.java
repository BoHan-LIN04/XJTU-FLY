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
 * 
 * </p>
 *
 * @author xuezhihengg
 * @since 2025-03-12
 */
@Getter
@Setter
@ToString
@TableName("festival_statistics")
public class FestivalStatistics implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("departure_city")
    private String departureCity;

    @TableField("arrival_city")
    private String arrivalCity;

    @TableField("festival")
    private String festival;

    @TableField("period")
    private String period;

    @TableField("throughput")
    private Long throughput;

    @TableField("passenger_flow")
    private Double passengerFlow;

    @TableField("price_increase")
    private Double priceIncrease;

    @TableField("start_time")
    private String startTime;

    @TableField("end_time")
    private String endTime;

    @TableField("update_time")
    private LocalDateTime updateTime;

    @TableField("latest")
    private Integer latest;
}
