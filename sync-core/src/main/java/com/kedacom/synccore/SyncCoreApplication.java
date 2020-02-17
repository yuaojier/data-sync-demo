package com.kedacom.synccore;

import com.kedacom.synccore.api.biz.CaseSyncInvoker;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class SyncCoreApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(SyncCoreApplication.class);
//        springApplication.setLogStartupInfo(true);
        springApplication.setAddCommandLineProperties(true);
        springApplication.run(args);
        System.out.println("启动完成");
    }

    @Bean
    CaseSyncInvoker caseSyncInvoker(){
        return new CaseSyncInvoker();
    }
}
