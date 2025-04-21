package org.flight_helper.flight_helper_bigdata.dbService.impl;

import lombok.extern.slf4j.Slf4j;
import org.flight_helper.flight_helper_bigdata.dao.MonthlyStatisticsMapper;
import org.flight_helper.flight_helper_bigdata.model.SeasonStatistics;
import org.flight_helper.flight_helper_bigdata.dao.SeasonStatisticsMapper;
import org.flight_helper.flight_helper_bigdata.dbService.ISeasonStatisticsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * æ·¡æ—ºå­£æ•°æ�®åˆ†æž�è¡¨ 服务实现类
 * </p>
 *
 * @author xuezhihengg
 * @since 2025-03-12
 */
@Slf4j
@Service
public class SeasonStatisticsServiceImpl extends ServiceImpl<SeasonStatisticsMapper, SeasonStatistics> implements ISeasonStatisticsService {

    @Autowired
    private SeasonStatisticsMapper seasonStatisticsMapper;

    public void updateLatestToZero() {
        // 调用Mapper中的方法更新数据
        int rowsAffected = seasonStatisticsMapper.updateLatestToZero();
//        log.info("SeasonStatistics旧数据标记完成");

    }


}
