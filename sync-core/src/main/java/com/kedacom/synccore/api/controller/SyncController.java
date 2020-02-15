package com.kedacom.synccore.api.controller;

import biz.RemoteBizState;
import cn.hutool.core.date.DateUtil;
import com.kedacom.synccore.event.CaseEvent;
import com.kedacom.synccore.service.CaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import request.CaseInformationRequest;

import javax.annotation.Resource;
import java.util.Date;

import static com.kedacom.synccore.constants.RequestPathConstants.CASE_INFORMATION;
import static com.kedacom.synccore.constants.RequestPathConstants.SYNC;

@RestController
@RequestMapping(SYNC)
@Api(tags = "数据同步")
public class SyncController {
    @Resource
    private CaseService caseService;
    @PostMapping(CASE_INFORMATION)
    @ApiOperation("案件数据同步")
    public void caseInfoSync(@RequestBody CaseInformationRequest caseInformationRequest){
        // FIXME: 2020/2/15  redisson 冪等處理 考虑是拦截器方式还是现有方式
        Date date = caseInformationRequest.getDate();
        String formatDate = DateUtil.formatDate(date);
        CaseEvent caseEvent = new CaseEvent(formatDate,caseInformationRequest.getCaseInformationList(),RemoteBizState.SUCCESS);
        caseService.sync(caseEvent);
    }

}
