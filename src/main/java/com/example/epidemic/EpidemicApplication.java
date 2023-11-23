package com.example.epidemic;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.epidemic.mapper")
public class EpidemicApplication {

    private static Logger logger = LoggerFactory.getLogger(EpidemicApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(EpidemicApplication.class, args);
        logger.info("===========================================\n");
        logger.info("   The Epidemic Service has started...\n");
        logger.info("===========================================");
    }
}
