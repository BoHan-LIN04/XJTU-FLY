package org.flight_helper.flight_helper_bigdata;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan("org.flight_helper.flight_helper_bigdata.dao")
public class PlaneHelperBigdataApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlaneHelperBigdataApplication.class, args);
    }

}
