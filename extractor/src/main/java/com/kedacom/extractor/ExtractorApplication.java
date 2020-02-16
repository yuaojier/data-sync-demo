package com.kedacom.extractor;

import com.kedacom.extractor.properties.WebserviceProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients
//@EnableScheduling
@EnableConfigurationProperties(WebserviceProperties.class)
public class ExtractorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExtractorApplication.class, args);
    }

}
