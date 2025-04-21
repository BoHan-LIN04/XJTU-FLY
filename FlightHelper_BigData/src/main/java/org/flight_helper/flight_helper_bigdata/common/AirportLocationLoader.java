package org.flight_helper.flight_helper_bigdata.common;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


public class AirportLocationLoader implements Serializable {
    private static final String FILE_PATH = "statics/airport_coordinates.csv";
    // airport -> (longitude, latitude)
    private static final Map<String, double[]> airportCoordinatesMap = new HashMap<>();
    // airport -> city
    private static final Map<String, String> airportCityMap = new HashMap<>();


    static {
        try (InputStream inputStream = new ClassPathResource(FILE_PATH).getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

            reader.lines().skip(1).forEach(line -> {
                String[] parts = line.split(",");
                if (parts.length == 4) {  // Adjusted for name, city, longitude and latitude
                    String name = parts[0].trim();
                    String city = parts[1].trim();
                    double longitude = Double.parseDouble(parts[2].trim());
                    double latitude = Double.parseDouble(parts[3].trim());

                    // Populate airportCoordinatesMap
                    airportCoordinatesMap.put(name, new double[]{longitude, latitude});
                    // Populate airportCityMap
                    airportCityMap.put(name, city);
                }
            });
        } catch (IOException | NullPointerException e) {
            throw new RuntimeException("Failed to load airport data", e);
        }
    }

    public static double[] getCoordinates(String airportCode) {
        return airportCoordinatesMap.get(airportCode);
    }

    public static double getLongitude(String airportCode) {
        double[] coords = getCoordinates(airportCode);
        return coords != null ? coords[0] : null; // 返回经度
    }

    public static double getLatitude(String airportCode) {
        double[] coords = getCoordinates(airportCode);
        return coords != null ? coords[1] : null; // 返回经度
    }


    public static Map<String, double[]> getAllCoordinates() {
        return airportCoordinatesMap;
    }

    public static String getCity(String airportCode) {
        return airportCityMap.get(airportCode);
    }

    public static Map<String, String> getAllCities() {
        return airportCityMap;
    }
}
