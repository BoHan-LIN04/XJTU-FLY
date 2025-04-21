package org.flight_helper.flight_helper_bigdata.controller;

import org.flight_helper.flight_helper_bigdata.common.ApiResponse;
import org.flight_helper.flight_helper_bigdata.service.ChartsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/charts")
public class ChartsController {
    @Autowired
    private ChartsService chartsService;

    @GetMapping("/airline/{startDate}/{endDate}")
    public ApiResponse<String> updateAirlineCharts(@PathVariable String startDate, @PathVariable String endDate) throws ParseException {
        chartsService.updateAirlineCharts(startDate, endDate);
        return ApiResponse.success("成功");
    }

    @GetMapping("/airport/{startDate}/{endDate}")
    public ApiResponse<String> updateAirportCharts(@PathVariable String startDate, @PathVariable String endDate) throws ParseException {
        chartsService.updateAirportCharts(startDate, endDate);
        return ApiResponse.success("成功");
    }

    @GetMapping("/city/{startDate}/{endDate}")
    public ApiResponse<String> updateCityCharts(@PathVariable String startDate, @PathVariable String endDate) throws ParseException {
        chartsService.updateCityCharts(startDate, endDate);
        return ApiResponse.success("成功");
    }


}
