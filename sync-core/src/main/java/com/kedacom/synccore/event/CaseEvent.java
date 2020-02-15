package com.kedacom.synccore.event;

import biz.RemoteBizEvent;
import biz.RemoteBizState;
import lombok.NonNull;
import request.CaseInformation;

import java.util.List;

public class CaseEvent extends RemoteBizEvent<List<CaseInformation>,Void> {
    public CaseEvent(@NonNull String bizName, @NonNull List<CaseInformation> request, @NonNull RemoteBizState state) {
        super(bizName, request, state);
    }
}
