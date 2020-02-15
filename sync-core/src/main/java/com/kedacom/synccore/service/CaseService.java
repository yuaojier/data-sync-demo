package com.kedacom.synccore.service;

import com.kedacom.synccore.event.CaseEvent;


public interface CaseService {
    /**
     * 案件信息同步
     */
    void sync(CaseEvent caseEvent);
}
