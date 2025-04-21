package org.flight_helper.flight_helper_bigdata.dao;

import org.apache.ibatis.annotations.Update;
import org.flight_helper.flight_helper_bigdata.model.WeeklyStatistics;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;


/**
 * <p>
 * ä¸€å‘¨å†…æ¯�å¤©çš„ç»Ÿè®¡æ•°æ�® Mapper 接口
 * </p>
 *
 * @author xuezhihengg
 * @since 2025-03-12
 */
public interface WeeklyStatisticsMapper extends BaseMapper<WeeklyStatistics> {

    @Update("UPDATE weekly_statistics SET latest = 0 WHERE latest = 1")
    int updateLatestToZero();

}

