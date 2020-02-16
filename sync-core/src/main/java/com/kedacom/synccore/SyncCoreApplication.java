package com.kedacom.synccore;

import com.kedacom.synccore.api.biz.CaseSyncInvoker;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableConfigurationProperties(SyncRetryProperties.class)
@EnableAsync
@MapperScan("com.kedacom.synccore.dao")
public class SyncCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(SyncCoreApplication.class, args);
    }

    @Bean
    CaseSyncInvoker caseSyncInvoker(){
        return new CaseSyncInvoker();
    }
}
