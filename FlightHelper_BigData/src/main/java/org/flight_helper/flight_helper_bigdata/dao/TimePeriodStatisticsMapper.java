package org.flight_helper.flight_helper_bigdata.dao;

import org.apache.ibatis.annotations.Update;
import org.flight_helper.flight_helper_bigdata.model.TimePeriodStatistics;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;


/**
 * <p>
 * ä¸€å¤©ä¸�å�Œæ—¶é—´æ®µçš„ç»Ÿè®¡ä¿¡æ�¯ Mapper 接口
 * </p>
 *
 * @author xuezhihengg
 * @since 2025-03-12
 */
public interface TimePeriodStatisticsMapper extends BaseMapper<TimePeriodStatistics> {

    @Update("UPDATE time_period_statistics SET latest = 0 WHERE latest = 1")
    int updateLatestToZero();

}

