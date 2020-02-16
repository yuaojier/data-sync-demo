package com.kedacom.synccore.event;

import biz.RemoteBizEvent;
import biz.RemoteBizState;
import lombok.NonNull;
import request.CaseInformation;
import request.CaseInformationRequest;

import javax.validation.constraints.NotNull;
import java.util.List;

public class CaseEvent extends RemoteBizEvent<CaseInformationRequest,List<CaseInformation>> {

    public CaseEvent(@NotNull String bizName, CaseInformationRequest request, RemoteBizState state) {
        super(bizName, request, state);
    }
}
