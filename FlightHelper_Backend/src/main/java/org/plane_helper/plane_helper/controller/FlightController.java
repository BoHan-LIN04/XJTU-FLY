package org.plane_helper.plane_helper.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.plane_helper.plane_helper.common.ApiResponse;
import org.plane_helper.plane_helper.model.dto.FlightSearchRequest;
import org.plane_helper.plane_helper.model.dto.FlightSearchResponse;
import org.plane_helper.plane_helper.service.impl.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/flight")
public class FlightController {
    @Autowired
    FlightService flightService;

    @PostMapping("search")
    public ApiResponse<FlightSearchResponse> search(@RequestBody @Validated FlightSearchRequest request){
        FlightSearchResponse response = flightService.search(request);

        return ApiResponse.success(response);
    }
}
