package org.flight_helper.flight_helper_bigdata.dao;

import org.apache.ibatis.annotations.Update;
import org.flight_helper.flight_helper_bigdata.model.MonthlyStatistics;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;


/**
 * <p>
 * ä¸€å¹´å†…æ¯�æœˆçš„ç»Ÿè®¡æ•°æ�® Mapper 接口
 * </p>
 *
 * @author xuezhihengg
 * @since 2025-03-12
 */
public interface MonthlyStatisticsMapper extends BaseMapper<MonthlyStatistics> {

    @Update("UPDATE monthly_statistics SET latest = 0 WHERE latest = 1")
    int updateLatestToZero();

}

