package com.kedacom.extractor.feign;

import com.kedacom.extractor.feign.config.FastjsonFeignConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import request.CaseInformationRequest;

import java.util.Date;

import static constants.RequestPathConstants.*;

@FeignClient(name = "sync-core",url = "${sync.url}",configuration = FastjsonFeignConfiguration.class)
public interface SyncFeignClient {
    @PostMapping(value = SYNC+CASE_INFORMATION, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void syncCaseDate(@RequestBody CaseInformationRequest caseInformationRequest);

    @PostMapping (value = SYNC+EarliestTime, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Date getQueryTime();
}
