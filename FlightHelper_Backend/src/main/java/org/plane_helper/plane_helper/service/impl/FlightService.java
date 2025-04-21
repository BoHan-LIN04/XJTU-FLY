package org.plane_helper.plane_helper.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.plane_helper.plane_helper.common.CustomException;
import org.plane_helper.plane_helper.model.dto.FlightSearchRequest;
import org.plane_helper.plane_helper.model.dto.FlightSearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
public class FlightService {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    ObjectMapper objectMapper;

    public FlightSearchResponse search(FlightSearchRequest request) throws CustomException {
        FlightSearchResponse response = new FlightSearchResponse();

        String departureAirport = request.getDepartureAirport();
        String arrivalAirport = request.getArrivalAirport();
        String date = request.getDate();
        String airline = request.getAirline();

        // 构造URL
        String url;

        if (stringIsNotNullOrEmpty(departureAirport) && stringIsNotNullOrEmpty(arrivalAirport)) {
            String baseUrl = "https://www.flightstats.com/v2/api-next/flight-tracker/route/%s/%s/%s?carrierCode=%s&numHours=24&hour=0";
            url = String.format(baseUrl, departureAirport, arrivalAirport, date, airline);
        } else if (stringIsNotNullOrEmpty(departureAirport)) {
            String baseUrl = "https://www.flightstats.com/v2/api-next/flight-tracker/dep/%s/%s/0?carrierCode=%s&numHours=12";
            url = String.format(baseUrl, departureAirport, date, airline);
        } else if (stringIsNotNullOrEmpty(arrivalAirport)) {
            String baseUrl = "https://www.flightstats.com/v2/api-next/flight-tracker/arr/%s/%s/0?carrierCode=%s&numHours=12";
            url = String.format(baseUrl, arrivalAirport, date, airline);
        } else {
            // 不允许departureAirport与arrivalAirport均为空
            throw new CustomException("40001", "Please enter a departure airport and/or arrival airport");
        }

        // 请求机票查询
        String result = restTemplate.getForObject(url, String.class);
        // 解析查询响应
        JsonNode rootNode;
        try {
            rootNode = objectMapper.readTree(result);
        } catch (JsonProcessingException e) {
            throw new CustomException("40002", e.getMessage());
        }

        // 构造header
        FlightSearchResponse.FlightSearchResponseHeader header = response.getHeader();
        JsonNode headerNode = rootNode.path("data").path("header");
        header.setDate(headerNode.path("date").asText());
        header.setTitle(headerNode.path("mobileTitle").asText());
        if (stringIsNotNullOrEmpty(departureAirport)){
            // 构造departureAirport
            FlightSearchResponse.AirPort departureAirportHeader = header.getDepartureAirport();
            JsonNode departureAirportNode = headerNode.path("departureAirport");
            airPortSetHelper(departureAirportHeader, departureAirportNode);
        }
        if (stringIsNotNullOrEmpty(arrivalAirport)){
            // 构造arrivalAirport
            FlightSearchResponse.AirPort arrivalAirportHeader = header.getArrivalAirport();
            JsonNode arrivalAirportNode = headerNode.path("arrivalAirport");
            airPortSetHelper(arrivalAirportHeader, arrivalAirportNode);
        }

        // 构造flights
        List<FlightSearchResponse.FlightSearchResponseItem> items = response.getItems();
        JsonNode flights = rootNode.path("data").path("flights");

        for (JsonNode flight : flights) {
            FlightSearchResponse.FlightSearchResponseItem item = new FlightSearchResponse.FlightSearchResponseItem();
            JsonNode carrierNode = flight.path("carrier");
            item.setAirline(carrierNode.path("name").asText());
            item.setFlightNumber(carrierNode.path("fs").asText() + carrierNode.path("flightNumber").asText());
            item.setArrivalTime(flight.path("arrivalTime").path("timeAMPM").asText());
            item.setDepartureTime(flight.path("departureTime").path("timeAMPM").asText());
            items.add(item);
        }
        return response;
    }


    boolean stringIsNotNullOrEmpty(String string) {
        return string != null && !string.isEmpty();
    }

    void airPortSetHelper(FlightSearchResponse.AirPort airportHeader, JsonNode airportNode) {
        airportHeader.setIata(airportNode.path("iata").asText());
        airportHeader.setName(airportNode.path("name").asText());
        airportHeader.setCity(airportNode.path("city").asText());
        airportHeader.setState(airportNode.path("state").asText());
        airportHeader.setCountry(airportNode.path("country").asText());
        airportHeader.setTimeZoneRegionName(airportNode.path("timeZoneRegionName").asText());
    }


}
