package com.kedacom.synccore.api.biz;

import com.kedacom.core.api.CaseInfoService;
import com.kedacom.synccore.convertor.RequestConvertor;
import com.kedacom.synccore.dao.CaseInfoDao;
import com.kedacom.synccore.event.CaseEvent;
import entity.CaseInfo;
import event.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import request.CaseInformation;
import request.CaseInformationRequest;

import java.util.List;

@Component
public class CaseSyncHandler implements EventHandler<CaseEvent> {
//    @Autowired
//    private CaseInfoService caseInfoService;
    @Autowired
    private RequestConvertor requestConvertor;
    @Autowired
    private CaseInfoDao caseInfoDao;
    @Override
    public boolean supports(CaseEvent event) {
        return true;
    }

    @Override
    public void handler(CaseEvent event) throws Throwable {
        String sourceTag = event.getSourceTag();
        CaseInformationRequest request = event.getRequest();
        List<CaseInformation> response = event.getResponse();
        List<CaseInfo> caseInfos = requestConvertor.requestToEntity(request.getCaseInformationList());
        caseInfoDao.updateOrInsert(caseInfos);
    }
}
