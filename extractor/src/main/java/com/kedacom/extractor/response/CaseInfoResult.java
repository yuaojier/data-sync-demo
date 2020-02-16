package com.kedacom.extractor.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaseInfoResult implements Serializable {
    private boolean success;
    private List<CaseInfo> caseInfoList;
    private int totalCount;
}
