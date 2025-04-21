package org.flight_helper.flight_helper_bigdata.dao;

import org.apache.ibatis.annotations.Update;
import org.flight_helper.flight_helper_bigdata.model.SeasonStatistics;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;


/**
 * <p>
 * æ·¡æ—ºå­£æ•°æ�®åˆ†æž�è¡¨ Mapper 接口
 * </p>
 *
 * @author xuezhihengg
 * @since 2025-03-12
 */
public interface SeasonStatisticsMapper extends BaseMapper<SeasonStatistics> {

    @Update("UPDATE season_statistics SET latest = 0 WHERE latest = 1")
    int updateLatestToZero();

}

