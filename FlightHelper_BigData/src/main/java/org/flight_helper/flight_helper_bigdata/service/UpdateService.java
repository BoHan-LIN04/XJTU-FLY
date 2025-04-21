package org.flight_helper.flight_helper_bigdata.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateService {

    @Autowired
    ChartsService chartsService;
    @Autowired
    StatisticsService statisticsService;

    public void updateAll(String startDate, String endDate){
        // update charts
        chartsService.updateAirlineCharts(startDate, endDate);
        chartsService.updateAirportCharts(startDate, endDate);
        chartsService.updateCityCharts(startDate, endDate);

        // update statistic
        statisticsService.updateWeeklyStatistics(startDate, endDate);
        statisticsService.updateMonthlyStatistics(startDate, endDate);
        statisticsService.updateWeeklyHourStatistics(startDate, endDate);
        statisticsService.updateTimePeriodStatistics(startDate, endDate);
        statisticsService.updateFestivalStatistics(startDate, endDate);
        statisticsService.updateSeasonStatistics(startDate, endDate);
        statisticsService.updateStopStatistics(startDate, endDate);
    }
}
