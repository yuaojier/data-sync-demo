package com.kedacom.extractor.properties;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "sync")
@Getter
@Setter
public class WebserviceProperties {
    @NotEmpty
    private String webServiceUrl;
    @NotEmpty
    private String userCode;
    @NotEmpty
    private String passWord;

}
