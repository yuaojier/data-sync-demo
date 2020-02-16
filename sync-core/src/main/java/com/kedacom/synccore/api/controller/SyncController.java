package com.kedacom.synccore.api.controller;

import biz.RemoteBizState;
import com.kedacom.synccore.dao.CaseInfoDao;
import com.kedacom.synccore.event.CaseEvent;
import com.kedacom.synccore.service.CaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import request.CaseInformationRequest;

import javax.annotation.Resource;

import java.util.Date;

import static constants.RequestPathConstants.*;


@RestController
@RequestMapping(SYNC)
@Api(tags = "数据同步")
public class SyncController {
    @Autowired
    private CaseInfoDao caseInfoDao;
    @Resource
    private CaseService caseService;
    public static final String SYNC_DATE_CASE = "sync-case-information";
    @PostMapping(CASE_INFORMATION)
    @ApiOperation("案件数据同步")
    public void caseInfoSync(@RequestBody CaseInformationRequest caseInformationRequest){
        // FIXME: 2020/2/15  redisson 冪等處理 考虑是拦截器方式还是现有方式
//        Date date = caseInformationRequest.getDate();
//        String formatDate = DateUtil.formatDate(date);
        CaseEvent caseEvent = new CaseEvent(SYNC_DATE_CASE,caseInformationRequest,RemoteBizState.SUCCESS);
        caseService.sync(caseEvent);
    }
    @PostMapping(EarliestTime)
    @ApiOperation("最早记录的时间")
    public Date queryEarliestTime(){
        return caseInfoDao.queryUpdateTime();
    }

}
