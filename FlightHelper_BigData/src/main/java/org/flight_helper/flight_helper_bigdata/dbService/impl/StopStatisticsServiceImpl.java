package org.flight_helper.flight_helper_bigdata.dbService.impl;

import lombok.extern.slf4j.Slf4j;
import org.flight_helper.flight_helper_bigdata.dao.SeasonStatisticsMapper;
import org.flight_helper.flight_helper_bigdata.model.StopStatistics;
import org.flight_helper.flight_helper_bigdata.dao.StopStatisticsMapper;
import org.flight_helper.flight_helper_bigdata.dbService.IStopStatisticsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * ç»�å�œæ¬¡æ•°ç»Ÿè®¡ 服务实现类
 * </p>
 *
 * @author xuezhihengg
 * @since 2025-03-12
 */
@Slf4j
@Service
public class StopStatisticsServiceImpl extends ServiceImpl<StopStatisticsMapper, StopStatistics> implements IStopStatisticsService {

    @Autowired
    private StopStatisticsMapper stopStatisticsMapper;

    public void updateLatestToZero() {
        // 调用Mapper中的方法更新数据
        int rowsAffected = stopStatisticsMapper.updateLatestToZero();
//        log.info("StopStatistics旧数据标记完成");

    }

}
