package org.flight_helper.flight_helper_bigdata.dbService.impl;

import lombok.extern.slf4j.Slf4j;
import org.flight_helper.flight_helper_bigdata.dao.WeeklyHourStatisticsMapper;
import org.flight_helper.flight_helper_bigdata.model.WeeklyStatistics;
import org.flight_helper.flight_helper_bigdata.dao.WeeklyStatisticsMapper;
import org.flight_helper.flight_helper_bigdata.dbService.IWeeklyStatisticsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * ä¸€å‘¨å†…æ¯�å¤©çš„ç»Ÿè®¡æ•°æ�® 服务实现类
 * </p>
 *
 * @author xuezhihengg
 * @since 2025-03-12
 */
@Slf4j
@Service
public class WeeklyStatisticsServiceImpl extends ServiceImpl<WeeklyStatisticsMapper, WeeklyStatistics> implements IWeeklyStatisticsService {


    @Autowired
    private WeeklyStatisticsMapper weeklyStatisticsMapper;

    public void updateLatestToZero() {
        // 调用Mapper中的方法更新数据
        int rowsAffected = weeklyStatisticsMapper.updateLatestToZero();
//        log.info("WeeklyStatistics旧数据标记完成");

    }

}
