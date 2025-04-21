package org.flight_helper.flight_helper_bigdata.dbService.impl;

import lombok.extern.slf4j.Slf4j;
import org.flight_helper.flight_helper_bigdata.dao.FestivalStatisticsMapper;
import org.flight_helper.flight_helper_bigdata.model.MonthlyStatistics;
import org.flight_helper.flight_helper_bigdata.dao.MonthlyStatisticsMapper;
import org.flight_helper.flight_helper_bigdata.dbService.IMonthlyStatisticsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * ä¸€å¹´å†…æ¯�æœˆçš„ç»Ÿè®¡æ•°æ�® 服务实现类
 * </p>
 *
 * @author xuezhihengg
 * @since 2025-03-12
 */
@Slf4j
@Service
public class MonthlyStatisticsServiceImpl extends ServiceImpl<MonthlyStatisticsMapper, MonthlyStatistics> implements IMonthlyStatisticsService {

    @Autowired
    private MonthlyStatisticsMapper monthlyStatisticsMapper;

    public void updateLatestToZero() {
        // 调用Mapper中的方法更新数据
        int rowsAffected = monthlyStatisticsMapper.updateLatestToZero();
//        log.info("MonthlyStatistics旧数据标记完成");

    }

}
