package org.plane_helper.plane_helper.model.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FlightSearchResponse {

    private List<FlightSearchResponseItem> items;
    private FlightSearchResponseHeader header;

    public FlightSearchResponse() {
        this.header = new FlightSearchResponseHeader();
        this.header.setArrivalAirport(new AirPort());
        this.header.setDepartureAirport(new AirPort());
        this.items = new ArrayList<>();
    }

    @Data
    public static class FlightSearchResponseItem {
        private String airline;
        private String arrivalTime;
        private String departureTime;
        private String flightNumber;
    }

    @Data
    public static class FlightSearchResponseHeader {
        private String date;
        private String title;
        private AirPort arrivalAirport;
        private AirPort departureAirport;
    }

    @Data
    public static class AirPort {
        private String city;
        private String country;
        private String iata;
        private String name;
        private String state;
        private String timeZoneRegionName;
    }
}
