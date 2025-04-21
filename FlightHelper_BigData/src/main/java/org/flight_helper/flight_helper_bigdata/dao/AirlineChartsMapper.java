package org.flight_helper.flight_helper_bigdata.dao;

import org.apache.ibatis.annotations.Update;
import org.flight_helper.flight_helper_bigdata.model.AirlineCharts;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;


/**
 * <p>
 * èˆªç�­æŽ’è¡Œæ¦œè¡¨ Mapper 接口
 * </p>
 *
 * @author xuezhihengg
 * @since 2025-03-12
 */
public interface AirlineChartsMapper extends BaseMapper<AirlineCharts> {

    @Update("UPDATE airline_charts SET latest = 0 WHERE latest = 1")
    int updateLatestToZero();

}

