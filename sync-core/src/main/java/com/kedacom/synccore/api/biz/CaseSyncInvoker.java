package com.kedacom.synccore.api.biz;

import biz.RemoteBizInvoker;
import biz.RetryPolicy;
import biz.exception.RemoteBizFailureException;
import biz.exception.RemoteBizUnknownException;
import com.kedacom.core.api.CaseInfoService;
import com.kedacom.synccore.SyncRetryProperties;
import com.kedacom.synccore.api.dto.CaseSyncType;
import com.kedacom.synccore.convertor.RequestConvertor;
import com.kedacom.synccore.dao.CaseInfoDao;
import com.kedacom.synccore.event.CaseEvent;
import entity.CaseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import request.CaseInformation;
import request.CaseInformationRequest;

import java.util.List;
import java.util.Optional;

import static com.kedacom.synccore.api.controller.SyncController.SYNC_DATE_CASE;

public class CaseSyncInvoker implements RemoteBizInvoker<CaseInformationRequest,List<CaseInformation>,CaseEvent> {
    @Autowired
    private SyncRetryProperties syncRetryProperties;
//    @Autowired
//    private CaseInfoService caseInfoService;
    @Autowired
    private RequestConvertor requestConvertor;
    @Autowired
    private CaseInfoDao caseInfoDao;

    public static final long QUERY_DELAY_MILLIS = 5000;

    public static final int MAX_RETRY_TIME = 10;
    private static final RetryPolicy RETRY_POLICY = RetryPolicy.builder()
            .delay(QUERY_DELAY_MILLIS)
            .retryPeriod(QUERY_DELAY_MILLIS)
            .maxAttempts(MAX_RETRY_TIME)
            .build();


    @Override
    public List<CaseInformation> invoke(CaseInformationRequest caseInformationRequest) throws RemoteBizFailureException, RemoteBizUnknownException {
        return confirm(caseInformationRequest,0);
    }

    @Override
    public List<CaseInformation> confirm(CaseInformationRequest caseInformationRequest, int pollingTime) throws RemoteBizFailureException, RemoteBizUnknownException {
        List<CaseInfo> caseInfos = requestConvertor.requestToEntity(caseInformationRequest.getCaseInformationList());
        /**
         * 返回值待确定
         */
        caseInfoDao.updateOrInsert(caseInfos);
        return caseInformationRequest.getCaseInformationList();
    }

    @Override
    public Class<CaseEvent> getEventType() {
        return CaseEvent.class;
    }

    @Override
    public String getBizKey(CaseInformationRequest caseInformationRequest) {
        return caseInformationRequest.getIdempotent();
    }

    @Override
    public RetryPolicy getRetryPolicy() {
        return Optional.ofNullable(syncRetryProperties.getPolicy(CaseSyncType.SYNC)).orElse(RETRY_POLICY);
    }

    @Override
    public String getName() {
        return SYNC_DATE_CASE;
    }
}
