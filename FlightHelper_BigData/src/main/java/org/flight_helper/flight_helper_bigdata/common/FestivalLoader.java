package org.flight_helper.flight_helper_bigdata.common;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class FestivalLoader {
    private static final String FILE_PATH = "statics/american_festival.csv";
    // (month, day) -> festival
    private static final Map<int[], String> festivalMap = new HashMap<>();

    static {
        try (InputStream inputStream = new ClassPathResource(FILE_PATH).getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

            reader.lines().skip(1).forEach(line -> {
                String[] parts = line.split(",");
                if (parts.length == 3) {  // Adjusted for name, month, day
                    String name = parts[0].trim();
                    int month = Integer.parseInt(parts[1].trim());
                    int day = Integer.parseInt(parts[2].trim());

                    // Populate festivalMap
                    festivalMap.put(new int[]{month, day}, name);
                }
            });
        } catch (IOException | NullPointerException e) {
            throw new RuntimeException("Failed to load festival data", e);
        }
    }

    public static String getFestival(int month, int day) {

        String festival = festivalMap.get(new int[]{month, day});
        return (festival != null) ? festival : "NULL";
    }

    public static Map<int[], String> getAllFestivals() {
        return festivalMap;
    }
}
