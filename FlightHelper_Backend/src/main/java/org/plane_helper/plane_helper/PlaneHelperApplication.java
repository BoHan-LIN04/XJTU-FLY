package org.plane_helper.plane_helper;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.plane_helper.plane_helper.dao")
public class PlaneHelperApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlaneHelperApplication.class, args);
    }

}
