package org.flight_helper.flight_helper_bigdata.controller;

import org.flight_helper.flight_helper_bigdata.common.ApiResponse;
import org.flight_helper.flight_helper_bigdata.service.UpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("update")
public class updateController {

    @Autowired
    UpdateService updateService;

    @GetMapping("/all/{startDate}/{endDate}")
    public ApiResponse<String> updateAll(@PathVariable String startDate, @PathVariable String endDate) throws ParseException {
        updateService.updateAll(startDate, endDate);
        return ApiResponse.success("成功");
    }
}
