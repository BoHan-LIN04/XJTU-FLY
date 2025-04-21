package org.flight_helper.flight_helper_bigdata.dbService.impl;

import lombok.extern.slf4j.Slf4j;
import org.flight_helper.flight_helper_bigdata.dao.StopStatisticsMapper;
import org.flight_helper.flight_helper_bigdata.model.TimePeriodStatistics;
import org.flight_helper.flight_helper_bigdata.dao.TimePeriodStatisticsMapper;
import org.flight_helper.flight_helper_bigdata.dbService.ITimePeriodStatisticsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * ä¸€å¤©ä¸�å�Œæ—¶é—´æ®µçš„ç»Ÿè®¡ä¿¡æ�¯ 服务实现类
 * </p>
 *
 * @author xuezhihengg
 * @since 2025-03-12
 */
@Slf4j
@Service
public class TimePeriodStatisticsServiceImpl extends ServiceImpl<TimePeriodStatisticsMapper, TimePeriodStatistics> implements ITimePeriodStatisticsService {

    @Autowired
    private TimePeriodStatisticsMapper timePeriodStatisticsMapper;

    public void updateLatestToZero() {
        // 调用Mapper中的方法更新数据
        int rowsAffected = timePeriodStatisticsMapper.updateLatestToZero();
//        log.info("TimePeriodStatistics旧数据标记完成");

    }

}
