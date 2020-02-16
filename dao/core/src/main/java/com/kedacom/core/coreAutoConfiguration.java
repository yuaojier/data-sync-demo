package com.kedacom.core;

import com.kedacom.core.api.CaseInfoService;
import com.kedacom.core.dao.CaseInfoDao;
import entity.CaseInfo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

//@Configuration
//@MapperScan(basePackages = {"com.kedacom.core.dao"})
//@ComponentScan
public class coreAutoConfiguration {
    @Bean
    CaseInfoService caseInfoService(){
        return new CaseInfoServiceImpl();
    }
}
