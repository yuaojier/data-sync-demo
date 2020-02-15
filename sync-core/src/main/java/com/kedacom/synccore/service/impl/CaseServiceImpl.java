package com.kedacom.synccore.service.impl;

import com.kedacom.synccore.event.CaseEvent;
import com.kedacom.synccore.service.CaseService;
import event.EventChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;


@Service
public class CaseServiceImpl implements CaseService {
    private EventChannel eventChannel;
    @Override
    public void sync(CaseEvent caseEvent) {
        eventChannel.publishEvent(caseEvent);
    }
    @Autowired
    @Lazy
    public void setEventChannel(EventChannel eventChannel){
        this.eventChannel = eventChannel;
    }

}
