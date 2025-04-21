package org.flight_helper.flight_helper_bigdata.dao;

import org.apache.ibatis.annotations.Update;
import org.flight_helper.flight_helper_bigdata.model.AirportCharts;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;


/**
 * <p>
 * æœºåœºæŽ’è¡Œæ¦œè¡¨ Mapper 接口
 * </p>
 *
 * @author xuezhihengg
 * @since 2025-03-12
 */
public interface AirportChartsMapper extends BaseMapper<AirportCharts> {

    @Update("UPDATE airport_charts SET latest = 0 WHERE latest = 1")
    int updateLatestToZero();


}

