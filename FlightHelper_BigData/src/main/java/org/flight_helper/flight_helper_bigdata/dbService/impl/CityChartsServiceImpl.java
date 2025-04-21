package org.flight_helper.flight_helper_bigdata.dbService.impl;

import lombok.extern.slf4j.Slf4j;
import org.flight_helper.flight_helper_bigdata.dao.AirportChartsMapper;
import org.flight_helper.flight_helper_bigdata.model.CityCharts;
import org.flight_helper.flight_helper_bigdata.dao.CityChartsMapper;
import org.flight_helper.flight_helper_bigdata.dbService.ICityChartsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * åŸŽå¸‚æŽ’è¡Œæ¦œè¡¨ 服务实现类
 * </p>
 *
 * @author xuezhihengg
 * @since 2025-03-12
 */
@Slf4j
@Service
public class CityChartsServiceImpl extends ServiceImpl<CityChartsMapper, CityCharts> implements ICityChartsService {

    @Autowired
    private CityChartsMapper cityChartsMapper;

    public void updateLatestToZero() {
        // 调用Mapper中的方法更新数据
        int rowsAffected = cityChartsMapper.updateLatestToZero();
//        log.info("CityChartsService旧数据标记完成");

    }

}
