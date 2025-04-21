package org.flight_helper.flight_helper_bigdata.dbService.impl;

import lombok.extern.slf4j.Slf4j;
import org.flight_helper.flight_helper_bigdata.dao.AirlineChartsMapper;
import org.flight_helper.flight_helper_bigdata.model.AirportCharts;
import org.flight_helper.flight_helper_bigdata.dao.AirportChartsMapper;
import org.flight_helper.flight_helper_bigdata.dbService.IAirportChartsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * æœºåœºæŽ’è¡Œæ¦œè¡¨ 服务实现类
 * </p>
 *
 * @author xuezhihengg
 * @since 2025-03-12
 */
@Slf4j
@Service
public class AirportChartsServiceImpl extends ServiceImpl<AirportChartsMapper, AirportCharts> implements IAirportChartsService {

    @Autowired
    private AirportChartsMapper airportChartsMapper;

    public void updateLatestToZero() {
        // 调用Mapper中的方法更新数据
        int rowsAffected = airportChartsMapper.updateLatestToZero();
//        log.info("AirportCharts旧数据标记完成");

    }
}
