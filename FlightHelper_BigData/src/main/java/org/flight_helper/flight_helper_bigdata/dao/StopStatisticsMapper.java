package org.flight_helper.flight_helper_bigdata.dao;

import org.apache.ibatis.annotations.Update;
import org.flight_helper.flight_helper_bigdata.model.StopStatistics;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;


/**
 * <p>
 * ç»�å�œæ¬¡æ•°ç»Ÿè®¡ Mapper 接口
 * </p>
 *
 * @author xuezhihengg
 * @since 2025-03-12
 */
public interface StopStatisticsMapper extends BaseMapper<StopStatistics> {

    @Update("UPDATE stop_statistics SET latest = 0 WHERE latest = 1")
    int updateLatestToZero();

}

