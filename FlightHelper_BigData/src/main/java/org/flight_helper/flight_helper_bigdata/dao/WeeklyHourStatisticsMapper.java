package org.flight_helper.flight_helper_bigdata.dao;

import org.apache.ibatis.annotations.Update;
import org.flight_helper.flight_helper_bigdata.model.WeeklyHourStatistics;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;


/**
 * <p>
 * æ˜ŸæœŸä¸Žæ—¶é—´æ®µå…±å�Œå†³å®šçš„ç»Ÿè®¡ä¿¡æ�¯ Mapper 接口
 * </p>
 *
 * @author xuezhihengg
 * @since 2025-03-12
 */
public interface WeeklyHourStatisticsMapper extends BaseMapper<WeeklyHourStatistics> {

    @Update("UPDATE weekly_hour_statistics SET latest = 0 WHERE latest = 1")
    int updateLatestToZero();

}

