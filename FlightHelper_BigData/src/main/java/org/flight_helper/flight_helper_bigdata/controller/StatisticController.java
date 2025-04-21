package org.flight_helper.flight_helper_bigdata.controller;

import org.flight_helper.flight_helper_bigdata.common.ApiResponse;
import org.flight_helper.flight_helper_bigdata.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("statistic")
public class StatisticController {

    @Autowired
    StatisticsService statisticsService;


    @GetMapping("/weekly/{startDate}/{endDate}")
    public ApiResponse<String> updateWeeklyStatistics(@PathVariable String startDate, @PathVariable String endDate) throws ParseException {
        statisticsService.updateWeeklyStatistics(startDate, endDate);
        return ApiResponse.success("成功");
    }

    @GetMapping("/monthly/{startDate}/{endDate}")
    public ApiResponse<String> updateMonthlyStatistics(@PathVariable String startDate, @PathVariable String endDate) throws ParseException {
        statisticsService.updateMonthlyStatistics(startDate, endDate);
        return ApiResponse.success("成功");
    }

    @GetMapping("/timePeriod/{startDate}/{endDate}")
    public ApiResponse<String> updateTimePeriodStatistics(@PathVariable String startDate, @PathVariable String endDate) throws ParseException {
        statisticsService.updateTimePeriodStatistics(startDate, endDate);
        return ApiResponse.success("成功");
    }

    @GetMapping("/weeklyHour/{startDate}/{endDate}")
    public ApiResponse<String> updateWeeklyHourStatistics(@PathVariable String startDate, @PathVariable String endDate) throws ParseException {
        statisticsService.updateWeeklyHourStatistics(startDate, endDate);
        return ApiResponse.success("成功");
    }

    @GetMapping("/stop/{startDate}/{endDate}")
    public ApiResponse<String> updateStopStatistics(@PathVariable String startDate, @PathVariable String endDate) throws ParseException {
        statisticsService.updateStopStatistics(startDate, endDate);
        return ApiResponse.success("成功");
    }

    @GetMapping("/festival/{startDate}/{endDate}")
    public ApiResponse<String> updateFestivalStatistics(@PathVariable String startDate, @PathVariable String endDate) throws ParseException {
        statisticsService.updateFestivalStatistics(startDate, endDate);

        return ApiResponse.success("成功");
    }

    @GetMapping("/season/{startDate}/{endDate}")
    public ApiResponse<String> updateSeasonStatistics(@PathVariable String startDate, @PathVariable String endDate) throws ParseException {
        statisticsService.updateSeasonStatistics(startDate, endDate);
        return ApiResponse.success("成功");
    }
}
