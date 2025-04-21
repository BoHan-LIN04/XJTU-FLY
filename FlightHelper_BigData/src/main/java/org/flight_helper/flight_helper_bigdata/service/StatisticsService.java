package org.flight_helper.flight_helper_bigdata.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;
import org.flight_helper.flight_helper_bigdata.common.SparkService;
import org.flight_helper.flight_helper_bigdata.dbService.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class StatisticsService {

    @Autowired
    private SparkSession sparkSession;
    @Autowired
    private SparkService sparkService;
    @Autowired
    private WeeklyStatisticsServiceImpl weeklyStatisticsService;
    @Autowired
    private MonthlyStatisticsServiceImpl monthlyStatisticsService;
    @Autowired
    private TimePeriodStatisticsServiceImpl timePeriodStatisticsService;
    @Autowired
    private WeeklyHourStatisticsServiceImpl weeklyHourStatisticsService;
    @Autowired
    private StopStatisticsServiceImpl stopStatisticsService;
    @Autowired
    private FestivalStatisticsServiceImpl festivalStatisticsService;
    @Autowired
    private SeasonStatisticsServiceImpl seasonStatisticsService;

    @Transactional
    public void updateWeeklyStatistics(String startDate, String endDate) {
        weeklyStatisticsService.updateLatestToZero();
        sparkSession.sql("USE " + sparkService.getDatabase());

        String query = "SELECT " +
                "startingairport AS departure_city, " +
                "destinationairport AS arrival_city, " +
                "DAYOFWEEK(first(flightdate)) AS day_of_week, " +
                "AVG(passenger_capacity) AS passenger_flow, " +
                "AVG(totalfare) AS avg_price, " +
                "MIN(totalfare) AS lowest_price, " +
                "COUNT(*) AS throughput " +
                "FROM dw_truth " +
                "WHERE flightdate BETWEEN '" + startDate + "' AND '" + endDate + "' " +
                "GROUP BY startingairport, destinationairport";

        Dataset<Row> result = sparkSession.sql(query);

        // 添加额外的列：latest、start_time、end_time
        Dataset<Row> processedResult = result
                .withColumn("latest", functions.lit(1))
                .withColumn("start_time", functions.lit(startDate))
                .withColumn("end_time", functions.lit(endDate));

        log.info("New Data to save to weekly_statistics:");
        processedResult.show();

        sparkService.writeToMySQL(processedResult, "weekly_statistics");
        log.info("Updated weekly_statistics Successfully");
    }

    @Transactional
    public void updateMonthlyStatistics(String startDate, String endDate) {
        monthlyStatisticsService.updateLatestToZero();
        sparkSession.sql("USE " + sparkService.getDatabase());

        String query = "SELECT " +
                "startingairport AS departure_city, " +
                "destinationairport AS arrival_city, " +
                "MONTH(flightdate) AS month, " +
                "COUNT(*) AS throughput, " +
                "AVG(passenger_capacity) AS passenger_flow, " +
                "AVG(totalfare) AS avg_price, " +
                "MIN(totalfare) AS lowest_price " +
                "FROM dw_truth " +
                "WHERE flightdate BETWEEN '" + startDate + "' AND '" + endDate + "' " +
                "GROUP BY startingairport, destinationairport, MONTH(flightdate)";

        Dataset<Row> result = sparkSession.sql(query);

        // 添加额外的列：latest、start_time、end_time
        Dataset<Row> processedResult = result
                .withColumn("latest", functions.lit(1))
                .withColumn("start_time", functions.lit(startDate))
                .withColumn("end_time", functions.lit(endDate));

        log.info("New Data to save to monthly_statistics:");
        processedResult.show();

        sparkService.writeToMySQL(processedResult, "monthly_statistics");
        log.info("Updated monthly_statistics Successfully");
    }

    @Transactional
    public void updateTimePeriodStatistics(String startDate, String endDate) {
        timePeriodStatisticsService.updateLatestToZero();
        sparkSession.sql("USE " + sparkService.getDatabase());

        String query = "SELECT " +
                "startingairport AS departure_city, " +
                "destinationairport AS arrival_city, " +
                "CASE " +
                "  WHEN HOUR(segments_departure_time_raw) >= 0 AND HOUR(segments_departure_time_raw) < 6 THEN 1 " +
                "  WHEN HOUR(segments_departure_time_raw) >= 6 AND HOUR(segments_departure_time_raw) < 12 THEN 2 " +
                "  WHEN HOUR(segments_departure_time_raw) >= 12 AND HOUR(segments_departure_time_raw) < 18 THEN 3 " +
                "  WHEN HOUR(segments_departure_time_raw) >= 18 AND HOUR(segments_departure_time_raw) < 24 THEN 4 " +
                "END AS time_period, " +  // 根据小时数划分时间段
                "COUNT(*) AS throughput, " +
                "AVG(passenger_capacity) AS passenger_flow, " +
                "AVG(totalfare) AS avg_price, " +
                "MIN(totalfare) AS lowest_price " +
                "FROM dw_truth " +
                "WHERE flightdate BETWEEN '" + startDate + "' AND '" + endDate + "' " +
                "GROUP BY startingairport, destinationairport, time_period";

        Dataset<Row> result = sparkSession.sql(query);

        // 添加额外的列：latest、start_time、end_time
        Dataset<Row> processedResult = result
                .withColumn("latest", functions.lit(1))
                .withColumn("start_time", functions.lit(startDate))
                .withColumn("end_time", functions.lit(endDate));

        log.info("New Data to save to time_period_statistics:");
        processedResult.show();

        sparkService.writeToMySQL(processedResult, "time_period_statistics");
        log.info("Updated time_period_statistics Successfully");
    }

    @Transactional
    public void updateWeeklyHourStatistics(String startDate, String endDate) {
        weeklyHourStatisticsService.updateLatestToZero();
        sparkSession.sql("USE " + sparkService.getDatabase());

        String query = "SELECT " +
                "startingairport AS departure_city, " +
                "destinationairport AS arrival_city, " +
                "DAYOFWEEK(first(flightdate)) AS day_of_week, " +   // 获取星期几，1=Sunday, 7=Saturday
                "HOUR(first(segments_departure_time_raw)) AS hour, " +  // 获取小时数（0-23）
                "COUNT(*) AS throughput, " +
                "AVG(passenger_capacity) AS passenger_flow, " +
                "AVG(totalfare) AS avg_price, " +  // 计算每组的平均价格
                "MIN(totalfare) AS lowest_price " +   // 计算每组的最低价格
                "FROM dw_truth " +
                "WHERE flightdate BETWEEN '" + startDate + "' AND '" + endDate + "' " +
                "GROUP BY startingairport, destinationairport, day_of_week, hour";

        Dataset<Row> result = sparkSession.sql(query);

        // 添加额外的列：latest、start_time、end_time
        Dataset<Row> processedResult = result
                .withColumn("latest", functions.lit(1))
                .withColumn("start_time", functions.lit(startDate))
                .withColumn("end_time", functions.lit(endDate));

        log.info("New Data to save to weekly_hour_statistics:");
        processedResult.show();

        sparkService.writeToMySQL(processedResult, "weekly_hour_statistics");
        log.info("Updated weekly_hour_statistics Successfully");
    }

    @Transactional
    public void updateStopStatistics(String startDate, String endDate) {
        stopStatisticsService.updateLatestToZero();
        sparkSession.sql("USE " + sparkService.getDatabase());

        String query = "SELECT " +
                "startingairport AS departure_city, " +
                "destinationairport AS arrival_city, " +
                "SIZE(SPLIT(segments_airline_code, '\\|\\|')) - 1 AS times, " +  // 计算经停次数
                "COUNT(*) AS count " +
                "FROM ods " +
                "WHERE flightdate BETWEEN '" + startDate + "' AND '" + endDate + "' " +
                "GROUP BY startingairport, destinationairport, times";

        Dataset<Row> result = sparkSession.sql(query);

        // 添加额外的列：latest、start_time、end_time
        Dataset<Row> processedResult = result
                .withColumn("latest", functions.lit(1))
                .withColumn("start_time", functions.lit(startDate))
                .withColumn("end_time", functions.lit(endDate));

        log.info("New Data to save to stop_statistics:");
        processedResult.show();

        sparkService.writeToMySQL(processedResult, "stop_statistics");
        log.info("Updated stop_statistics Successfully");
    }

    @Transactional
    public void updateFestivalStatistics(String startDate, String endDate) {
        festivalStatisticsService.updateLatestToZero();
        sparkSession.sql("USE " + sparkService.getDatabase());
        String query = "SELECT " +
                "    dt.startingairport AS departure_city, " +
                "    dt.destinationairport AS arrival_city, " +
                "    festival, " +
                "    period, " +
                "    COUNT(*) AS throughput, " +
                "    AVG(dt.passenger_capacity) AS passenger_flow, " +
                "    (AVG(dt.totalfare) - avg_prices.yearly_avg_price) / avg_prices.yearly_avg_price * 100 AS price_increase, " +
                "    start_time, " +
                "    end_time, " +
                "    CURRENT_TIMESTAMP AS update_time, " +
                "    TRUE AS latest " +
                "FROM " +
                "    dw_truth dt " +
                "JOIN " +
                "    (SELECT departure_city, arrival_city, AVG(avg_price) AS yearly_avg_price " +
                "     FROM monthly_statistics " +
                "     GROUP BY departure_city, arrival_city) avg_prices " +
                "ON " +
                "    dt.startingairport = avg_prices.departure_city " +
                "    AND dt.destinationairport = avg_prices.arrival_city " +
                "CROSS JOIN " +
                "    (SELECT " +
                "         'April Fool\\'s Day' AS festival, '2022-04-01' AS period, TIMESTAMP '2022-04-01 00:00:00' AS start_time, TIMESTAMP '2022-04-01 23:59:59' AS end_time " +
                "     UNION ALL " +
                "     SELECT 'Easter', '2022-04-17', TIMESTAMP '2022-04-17 00:00:00', TIMESTAMP '2022-04-17 23:59:59' " +
                "     UNION ALL " +
                "     SELECT 'Mother\\'s Day', '2022-05-08', TIMESTAMP '2022-05-08 00:00:00', TIMESTAMP '2022-05-08 23:59:59' " +
                "     UNION ALL " +
                "     SELECT 'Memorial Day', '2022-05-30', TIMESTAMP '2022-05-30 00:00:00', TIMESTAMP '2022-05-30 23:59:59' " +
                "     UNION ALL " +
                "     SELECT 'Father\\'s Day', '2022-06-19', TIMESTAMP '2022-06-19 00:00:00', TIMESTAMP '2022-06-19 23:59:59' " +
                "     UNION ALL " +
                "     SELECT 'Independence Day', '2022-07-04', TIMESTAMP '2022-07-04 00:00:00', TIMESTAMP '2022-07-04 23:59:59' " +
                "     UNION ALL " +
                "     SELECT 'Labor Day', '2022-09-05', TIMESTAMP '2022-09-05 00:00:00', TIMESTAMP '2022-09-05 23:59:59' " +
                "     UNION ALL " +
                "     SELECT 'Columbus Day', '2022-10-10', TIMESTAMP '2022-10-10 00:00:00', TIMESTAMP '2022-10-10 23:59:59' " +
                "    ) holidays " +
                "WHERE " +
                "    dt.flightdate = holidays.period " +
                "    AND dt.flightdate BETWEEN '" + startDate + "' AND '" + endDate + "' " + // 过滤 flightdate 范围
                "GROUP BY " +
                "    dt.startingairport, dt.destinationairport, holidays.festival, holidays.period, holidays.start_time, holidays.end_time, avg_prices.yearly_avg_price";


        Dataset<Row> resultWithIncrease = sparkSession.sql(query);

        // 添加额外的列：latest、start_time、end_time
        Dataset<Row> processedResult = resultWithIncrease
                .withColumn("latest", functions.lit(1))
                .withColumn("start_time", functions.lit(startDate))
                .withColumn("end_time", functions.lit(endDate));

        log.info("New Data to save to festival_statistics:");
        processedResult.show();

        sparkService.writeToMySQL(processedResult, "festival_statistics");
        log.info("Updated festival_statistics Successfully");
    }

    @Transactional
    public void updateSeasonStatistics(String startDate, String endDate) {
        seasonStatisticsService.updateLatestToZero();
        sparkSession.sql("USE " + sparkService.getDatabase());

        String detailedQuery =
                "WITH MonthlyAvg AS ( " +
                    "SELECT " +
                        "startingairport, " +
                        "destinationairport, " +
                        "MONTH(flightdate) AS month, " +
                        "AVG(totalfare) AS monthly_avg_price " +
                    "FROM dw_truth " +
                        "WHERE flightdate BETWEEN '" + startDate + "' AND '" + endDate + "' " +
                    "GROUP BY startingairport, destinationairport, MONTH(flightdate) " +
                "), " +
                "OverallAvg AS ( " +
                    "SELECT " +
                        "startingairport, " +
                        "destinationairport, " +
                        "AVG(totalfare) AS overall_avg_price " +
                    "FROM dw_truth " +
                        "WHERE flightdate BETWEEN '" + startDate + "' AND '" + endDate + "' " +
                    "GROUP BY startingairport, destinationairport " +
                "), " +
                "Classified AS ( " +
                    "SELECT " +
                        "m.startingairport, " +
                        "m.destinationairport, " +
                        "m.month, " +
                        "m.monthly_avg_price, " +
                        "o.overall_avg_price, " +
                    "CASE " +
                        "WHEN m.monthly_avg_price > o.overall_avg_price * 1.1 THEN 3 " +
                        "WHEN m.monthly_avg_price BETWEEN o.overall_avg_price * 0.9 AND o.overall_avg_price * 1.1 THEN 2 " +
                        "WHEN m.monthly_avg_price < o.overall_avg_price * 0.9 THEN 1 " +
                        "ELSE 'else' " +
                    "END AS season_category " +
                    "FROM MonthlyAvg m " +
                    "JOIN OverallAvg o " +
                    "ON m.startingairport = o.startingairport AND m.destinationairport = o.destinationairport " +
                ") " +
                "SELECT " +
                    "c.startingairport AS departure_city, " +
                    "c.destinationairport AS arrival_city, " +
                    "c.season_category AS type, " +
                    "COUNT(*) AS count, " +
                    "AVG(t.totalfare) AS avg_price, " +
                    "MIN(t.totalfare) AS lowest_price " +
                "FROM dw_truth t " +
                "JOIN Classified c " +
                "ON t.startingairport = c.startingairport " +
                "AND t.destinationairport = c.destinationairport " +
                "AND MONTH(t.flightdate) = c.month " +
                "WHERE flightdate BETWEEN '" + startDate + "' AND '" + endDate + "' " +
                "GROUP BY c.startingairport, c.destinationairport, c.season_category";

        Dataset<Row> resultWithIncrease = sparkSession.sql(detailedQuery);

        // 添加额外的列：latest、start_time、end_time
        Dataset<Row> processedResult = resultWithIncrease
                .withColumn("latest", functions.lit(1))
                .withColumn("start_time", functions.lit(startDate))
                .withColumn("end_time", functions.lit(endDate));

        log.info("New Data to save to season_statistics:");
        processedResult.show();

        sparkService.writeToMySQL(processedResult, "season_statistics");
        log.info("Updated season_statistics Successfully");
    }
}
