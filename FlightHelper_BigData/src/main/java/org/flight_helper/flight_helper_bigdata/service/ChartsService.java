package org.flight_helper.flight_helper_bigdata.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.spark.sql.*;
import org.flight_helper.flight_helper_bigdata.common.SparkService;
import org.flight_helper.flight_helper_bigdata.dbService.impl.AirlineChartsServiceImpl;
import org.flight_helper.flight_helper_bigdata.dbService.impl.AirportChartsServiceImpl;
import org.flight_helper.flight_helper_bigdata.dbService.impl.CityChartsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.Locale;

@Slf4j
@Service
public class ChartsService {

    @Autowired
    private SparkSession sparkSession;
    @Autowired
    private SparkService sparkService;
    @Autowired
    private AirlineChartsServiceImpl airlineChartsService;
    @Autowired
    private AirportChartsServiceImpl airportChartsService;
    @Autowired
    private CityChartsServiceImpl cityChartsService;

    @Transactional
    public void updateAirlineCharts(String startDate, String endDate) {
        airlineChartsService.updateLatestToZero();
        sparkSession.sql("USE " + sparkService.getDatabase());

        // 执行查询，筛选出指定时间段的数据
        String query = "SELECT " +
                "segments_airline_code AS name, " +
                "COUNT(*) AS flights " +
                "FROM dw_truth " +
                "WHERE flightdate BETWEEN '" + startDate + "' AND '" + endDate + "' " +
                "GROUP BY segments_airline_code";

        // 执行Spark SQL查询
        Dataset<Row> result = sparkSession.sql(query);

        String type = determineChartType(startDate, endDate);

        // 添加额外的列：latest、start_time、end_time、update_time、type
        Dataset<Row> processedResult = result.withColumn("latest", functions.lit(1))  // 假设全部是最新数据
                .withColumn("start_time", functions.lit(startDate))
                .withColumn("end_time", functions.lit(endDate))
                .withColumn("type", functions.lit(type));

        log.info("New Data to save to airline_charts:");
        processedResult.show();

        sparkService.writeToMySQL(processedResult, "airline_charts");
        log.info("Updated airline_charts Successfully");
    }

    @Transactional
    public void updateAirportCharts(String startDate, String endDate) {
        airportChartsService.updateLatestToZero();
        sparkSession.sql("USE " + sparkService.getDatabase());

        // 执行查询，筛选出指定时间段的数据
        String query = "SELECT " +
                "startingairport AS name, " +
                "COUNT(*) AS throughput " +
                "FROM dw_truth " +
                "WHERE flightdate BETWEEN '" + startDate + "' AND '" + endDate + "' " +
                "GROUP BY startingairport";

        // 执行Spark SQL查询
        Dataset<Row> result = sparkSession.sql(query);

        String type = determineChartType(startDate, endDate);

        // 添加额外的列：longitude、latitude、latest、start_time、end_time、update_time、type
        Dataset<Row> processedResult = result
                .withColumn("latest", functions.lit(1))
                .withColumn("start_time", functions.lit(startDate))
                .withColumn("end_time", functions.lit(endDate))
                .withColumn("type", functions.lit(type));

        log.info("New Data to save to airport_charts:");
        processedResult.show();

        sparkService.writeToMySQL(processedResult, "airport_charts");
        log.info("Updated airport_charts Successfully");
    }

    @Transactional
    public void updateCityCharts(String startDate, String endDate) {
        cityChartsService.updateLatestToZero();
        sparkSession.sql("USE " + sparkService.getDatabase());

        // 执行查询，筛选出指定时间段的数据
        String query = "SELECT\n" +
                "    t.name,\n" +
                "    SUM(t.flights) AS flights,\n" +
                "    COUNT(DISTINCT t.airport_code) AS airports,\n" +
                "    FALSE AS latest,\n" +
                "    DATE '2022-01-01' AS start_time,\n" +
                "    DATE '2022-12-31' AS end_time,\n" +
                "    CURRENT_TIMESTAMP AS update_time,\n" +
                "    '年榜' AS type\n" +
                "FROM (\n" +
                "    SELECT\n" +
                "        CASE\n" +
                "            WHEN startingairport IN ('JFK', 'LGA') THEN 'New York'\n" +
                "            WHEN startingairport = 'ATL' THEN 'Atlanta'\n" +
                "            WHEN startingairport = 'BOS' THEN 'Boston'\n" +
                "            WHEN startingairport = 'CLT' THEN 'Charlotte'\n" +
                "            WHEN startingairport = 'DEN' THEN 'Denver'\n" +
                "            WHEN startingairport = 'DFW' THEN 'Dallas/Fort Worth'\n" +
                "            WHEN startingairport = 'DTW' THEN 'Detroit'\n" +
                "            WHEN startingairport = 'EWR' THEN 'Newark'\n" +
                "            WHEN startingairport = 'IAD' THEN 'Washington Dulles'\n" +
                "            WHEN startingairport = 'LAX' THEN 'Los Angeles'\n" +
                "            WHEN startingairport = 'MIA' THEN 'Miami'\n" +
                "            WHEN startingairport = 'OAK' THEN 'Oakland'\n" +
                "            WHEN startingairport = 'ORD' THEN 'Chicago'\n" +
                "            WHEN startingairport = 'PHL' THEN 'Philadelphia'\n" +
                "            WHEN startingairport = 'SFO' THEN 'San Francisco'\n" +
                "            ELSE startingairport\n" +
                "        END AS name,\n" +
                "        startingairport AS airport_code,\n" +
                "        COUNT(*) AS flights\n" +
                "    FROM dw_truth\n" +
                "    WHERE flightdate BETWEEN '" + startDate + "' AND '" + endDate + "' " +
                "    GROUP BY startingairport\n" +
                ") t\n" +
                "GROUP BY t.name";
        // 执行Spark SQL查询
        Dataset<Row> result = sparkSession.sql(query);

        String type = determineChartType(startDate, endDate);

        Dataset<Row> groupedResult = result
                .withColumn("latest", functions.lit(1))  // 假设全部是最新数据
                .withColumn("start_time", functions.lit(startDate))
                .withColumn("end_time", functions.lit(endDate))
                .withColumn("type", functions.lit(type));

        log.info("New Data to save to city_charts:");
        groupedResult.show();

        sparkService.writeToMySQL(groupedResult, "city_charts");
        log.info("Updated city_charts Successfully");
    }


    static String determineChartType(String startTime, String endTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(startTime, formatter);
        LocalDate endDate = LocalDate.parse(endTime, formatter);

        long yearsBetween = ChronoUnit.YEARS.between(startDate, endDate);

        // 获取所在年的周数
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int startWeek = startDate.get(weekFields.weekOfWeekBasedYear());
        int endWeek = endDate.get(weekFields.weekOfWeekBasedYear());

        if (startWeek == endWeek && startDate.getYear() == endDate.getYear()) {
            return "weekly"; // 同一年的同一周
        } else if (yearsBetween == 0 && startDate.getMonth() == endDate.getMonth()) {
            return "monthly"; // 同一年同月，但跨周
        } else if (yearsBetween == 0) {
            return "yearly"; // 同一年但跨月
        } else {
            return "不支持的时间范围";
        }
    }
}
