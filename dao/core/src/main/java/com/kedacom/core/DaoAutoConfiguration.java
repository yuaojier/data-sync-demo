package com.kedacom.core;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = {"com.kedacom"})
@ComponentScan
public class DaoAutoConfiguration {
}
