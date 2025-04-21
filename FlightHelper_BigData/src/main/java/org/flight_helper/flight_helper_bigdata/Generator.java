package org.flight_helper.flight_helper_bigdata;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

public class Generator {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://192.168.100.184:3306/plane_helper?useSSL=false&serverTimezone=UTC", "root", "password") // jdbc连接
                .globalConfig(builder -> {
                    builder.author("xuezhihengg") // 设置作者
                            .outputDir("src/main/java"); // 指定输出目录
                })
                .packageConfig(builder ->
                        builder.parent("org.flight_helper") // 设置父包名
                                .moduleName("flight_helper_bigdata") // 设置父包模块名
                                .entity("model") // 设置 Entity 包名
                                .service("dbService") // 设置 Service 包名
                                .serviceImpl("dbService.impl") // 设置 Service Impl 包名
                                .mapper("dao") // 设置 Mapper 包名
                                .xml("mappers") // 设置 Mapper XML 包名
                                .controller("controller") // 设置 Controller 包名
                )
                .strategyConfig(builder -> {
                    builder.entityBuilder()
                            .enableLombok() // 启用 Lombok
                            .enableTableFieldAnnotation() // 启用字段注解
                            .controllerBuilder()
                            .enableRestStyle(); // 启用 REST 风格
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}


