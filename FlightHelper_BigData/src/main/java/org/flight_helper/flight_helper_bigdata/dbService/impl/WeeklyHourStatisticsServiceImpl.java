package org.flight_helper.flight_helper_bigdata.dbService.impl;

import lombok.extern.slf4j.Slf4j;
import org.flight_helper.flight_helper_bigdata.dao.TimePeriodStatisticsMapper;
import org.flight_helper.flight_helper_bigdata.model.WeeklyHourStatistics;
import org.flight_helper.flight_helper_bigdata.dao.WeeklyHourStatisticsMapper;
import org.flight_helper.flight_helper_bigdata.dbService.IWeeklyHourStatisticsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * æ˜ŸæœŸä¸Žæ—¶é—´æ®µå…±å�Œå†³å®šçš„ç»Ÿè®¡ä¿¡æ�¯ 服务实现类
 * </p>
 *
 * @author xuezhihengg
 * @since 2025-03-12
 */
@Slf4j
@Service
public class WeeklyHourStatisticsServiceImpl extends ServiceImpl<WeeklyHourStatisticsMapper, WeeklyHourStatistics> implements IWeeklyHourStatisticsService {

    @Autowired
    private WeeklyHourStatisticsMapper weeklyHourStatisticsMapper;

    public void updateLatestToZero() {
        // 调用Mapper中的方法更新数据
        int rowsAffected = weeklyHourStatisticsMapper.updateLatestToZero();
//        log.info("WeeklyHourStatistics旧数据标记完成");

    }

}
