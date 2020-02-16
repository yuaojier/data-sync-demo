package com.kedacom.extractor.feign.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;

public class BaseFeignConfiguration {
    @Bean
    private Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
