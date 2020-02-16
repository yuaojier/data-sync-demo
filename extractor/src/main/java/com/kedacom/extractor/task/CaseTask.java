package com.kedacom.extractor.task;

import cn.hutool.Hutool;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.kedacom.extractor.axis.AxisClient;
import com.kedacom.extractor.convertor.ResultConvertor;
import com.kedacom.extractor.dto.CsAjxxQueryWhere;
import com.kedacom.extractor.feign.SyncFeignClient;
import com.kedacom.extractor.response.CaseInfo;
import com.kedacom.extractor.response.CaseInfoResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import request.CaseInformation;
import request.CaseInformationRequest;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static cn.hutool.core.date.DatePattern.NORM_DATE_FORMAT;

@Component
@Slf4j
public class CaseTask {
    private static final String CS_AJXX = "queryCsAjxx";
    @Autowired
    private AxisClient axisClient;
    @Autowired
    private ResultConvertor resultConvertor;
    @Autowired
    private SyncFeignClient syncFeignClient;
    @Scheduled(cron = "${sync.todayCorn}")
    public void syncTodayDate() {
        CsAjxxQueryWhere csAjxxQueryWhere = new CsAjxxQueryWhere();
        csAjxxQueryWhere.setSlsj(DateUtil.today() + "," + DateUtil.today());
        try {
            actuator(csAjxxQueryWhere);
        } catch (MalformedURLException | RemoteException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }
    @Scheduled(cron = "${sync.historicalData}")
    public void syncHistoricalData(){
        Date queryTime = syncFeignClient.getQueryTime();
        String beginTime ;
        String endTime;
        if (queryTime == null){
            beginTime = DateUtil.today();
        }else{
            beginTime = DateUtil.format(queryTime,NORM_DATE_FORMAT);
        }
        DateTime parse = DateUtil.parse(beginTime);
        long time = parse.getTime();
        // FIXME: 2020/2/16  30天一个案件都没有的情况下  需要使用redis 或者建新的表
        long l = time - 30 * 24 * 60 * 60 * 1000;
        CsAjxxQueryWhere csAjxxQueryWhere = new CsAjxxQueryWhere();
        csAjxxQueryWhere.setSlsj(DateUtil.date(l).toDateStr() + "," + beginTime);

    }

    private void actuator(CsAjxxQueryWhere csAjxxQueryWhere) throws MalformedURLException, RemoteException {
        String result = (String) axisClient.queryClient(CS_AJXX, csAjxxQueryWhere);
        CaseInfoResult caseInfoResult = JSON.parseObject(result, CaseInfoResult.class);
        if (caseInfoResult.isSuccess() && caseInfoResult.getTotalCount() != 0 && caseInfoResult.getCaseInfoList().size() > 0) {
            List<CaseInfo> caseInfoList = caseInfoResult.getCaseInfoList();
            List<CaseInformation> caseInformations = resultConvertor.caseResultToRequest(caseInfoList);
            CaseInformationRequest caseInformationRequest = new CaseInformationRequest();
            caseInformationRequest.setCaseInformationList(caseInformations);
            caseInformationRequest.setSource(CS_AJXX);
            caseInformationRequest.setDate(new Date());
            caseInformationRequest.setIdempotent(UUID.randomUUID().toString());
            syncFeignClient.syncCaseDate(caseInformationRequest);
        }

    }
}
