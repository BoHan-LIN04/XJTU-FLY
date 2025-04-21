package org.flight_helper.flight_helper_bigdata.dao;

import org.apache.ibatis.annotations.Update;
import org.flight_helper.flight_helper_bigdata.model.FestivalStatistics;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xuezhihengg
 * @since 2025-03-12
 */
public interface FestivalStatisticsMapper extends BaseMapper<FestivalStatistics> {

    @Update("UPDATE festival_statistics SET latest = 0 WHERE latest = 1")
    int updateLatestToZero();


}

