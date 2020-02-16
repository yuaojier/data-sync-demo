package com.kedacom.synccore.convertor;

import entity.CaseInfo;
import org.mapstruct.Mapper;
import request.CaseInformation;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RequestConvertor {

    List<CaseInfo> requestToEntity(List<CaseInformation> caseInformationList);
}
