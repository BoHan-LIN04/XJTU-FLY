package org.plane_helper.plane_helper.model.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class FlightSearchRequest {
    private String departureAirport;
    private String arrivalAirport;
    @NonNull
    private String date;
    @NonNull
    private String airline;
}
