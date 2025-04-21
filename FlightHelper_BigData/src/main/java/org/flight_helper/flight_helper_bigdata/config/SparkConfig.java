package org.flight_helper.flight_helper_bigdata.config;

import lombok.Getter;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class SparkConfig {

    @Value("${spark.master}")
    private String sparkMaster;

    @Value("${spark.app.name}")
    private String appName;

    @Value("${hive.metastore.uris}")
    private String hiveMetastore;

    @Value("${spark.sql.warehouse.dir}")
    private String warehouseDir;

    @Value("${spark.sql.database}")
    private String database;

    @Value("${spring.datasource.url}")
    private String jdbcUrl;

    @Value("${spring.datasource.username}")
    private String jdbcUsername;

    @Value("${spring.datasource.password}")
    private String jdbcPassword;

    @Bean
    public SparkSession sparkSession() {

        return SparkSession.builder()
                .appName(appName)
                .master(sparkMaster)
                .config("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
                .config("spark.sql.warehouse.dir", warehouseDir)
                .config("hive.metastore.uris", hiveMetastore)
                .config("spark.executor.memory", "1g")  // 限制每个 Executor 1G 内存
                .config("spark.executor.cores", "1")    // 每个 Executor 1 核
                .config("spark.driver.memory", "2g")    // 限制 Driver 2G 内存
                .config("spark.driver.host", "192.168.100.22") // important！ 要指定客户端的IP也就是运行这个程序的机器
                .config("spark.executor.instances", "2") // 限制 Executor 数量
                .enableHiveSupport()
                .getOrCreate();
    }
}