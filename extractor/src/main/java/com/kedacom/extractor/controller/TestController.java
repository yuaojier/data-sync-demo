package com.kedacom.extractor.controller;

import cn.hutool.core.date.DateTime;
import com.kedacom.extractor.feign.SyncFeignClient;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import request.CaseInformation;
import request.CaseInformationRequest;

import java.util.ArrayList;
import java.util.Date;

@RestController("/hello")
@Api(tags = "测试")
public class TestController {
    @Autowired
    private SyncFeignClient syncFeignClient;
    @GetMapping("test")
    public void testSync(){
        CaseInformationRequest caseInformationRequest = new CaseInformationRequest();
        CaseInformation caseInformation = new CaseInformation();
        caseInformation.setCaseIdentify("6666585545674");
        ArrayList<CaseInformation> objects = new ArrayList<>();
        objects.add(caseInformation);
        caseInformationRequest.setCaseInformationList(objects);
        caseInformationRequest.setDate(new DateTime());
        caseInformationRequest.setSource("my");
        caseInformationRequest.setIdempotent("002511");
        String url = "1111";

        syncFeignClient.syncCaseDate(caseInformationRequest);
    }
    @GetMapping("testDate")
    public Date testDate(){
        return syncFeignClient.getQueryTime();
    }
}
