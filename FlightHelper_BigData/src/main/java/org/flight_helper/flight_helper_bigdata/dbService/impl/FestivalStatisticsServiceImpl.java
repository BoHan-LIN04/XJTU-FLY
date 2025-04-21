package org.flight_helper.flight_helper_bigdata.dbService.impl;

import lombok.extern.slf4j.Slf4j;
import org.flight_helper.flight_helper_bigdata.dao.CityChartsMapper;
import org.flight_helper.flight_helper_bigdata.model.FestivalStatistics;
import org.flight_helper.flight_helper_bigdata.dao.FestivalStatisticsMapper;
import org.flight_helper.flight_helper_bigdata.dbService.IFestivalStatisticsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xuezhihengg
 * @since 2025-03-12
 */
@Slf4j
@Service
public class FestivalStatisticsServiceImpl extends ServiceImpl<FestivalStatisticsMapper, FestivalStatistics> implements IFestivalStatisticsService {

    @Autowired
    private FestivalStatisticsMapper festivalStatisticsMapper;

    public void updateLatestToZero() {
        // 调用Mapper中的方法更新数据
        int rowsAffected = festivalStatisticsMapper.updateLatestToZero();
//        log.info("FestivalStatistics旧数据标记完成");

    }

}
