package org.flight_helper.flight_helper_bigdata.common;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.flight_helper.flight_helper_bigdata.config.SparkConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Getter
@Service
public class SparkService {

    @Autowired
    SparkConfig sparkConfig;

    public String getDatabase() {
        return sparkConfig.getDatabase();
    }

    /**
     * 将数据写入 MySQL 数据库
     *
     * @param processedResult 处理后的数据集
     * @param dbTable 要存入的数据库
     */
    public void writeToMySQL(Dataset<Row> processedResult, String dbTable) {

        // 创建临时视图
        processedResult.createOrReplaceTempView("temp_view");

        // 将数据写入 MySQL 数据库
        processedResult.write()
                .format("jdbc")
                .option("url", sparkConfig.getJdbcUrl())
                .option("driver", "com.mysql.jdbc.Driver")
                .option("dbtable", dbTable)
                .option("user", sparkConfig.getJdbcUsername())
                .option("password", sparkConfig.getJdbcPassword())
                .mode(SaveMode.Append)
                .save();

        log.info("Write to MySQL table Successfully: {}", dbTable);
    }
}
