package entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 案件信息(CaseInfo)实体类
 *
 * @author makejava
 * @since 2020-02-15 15:09:37
 */
public class CaseInfo implements Serializable {
    private static final long serialVersionUID = -85509933416134572L;
    
    private Long id;
    /**
    * 案件标识
    */
    private String caseIdentify;
    /**
    * 案件编号
    */
    private String caseNo;
    /**
    * 原始编号（警情编号）
    */
    private String origNo;
    /**
    * 案件名称
    */
    private String caseName;
    /**
    * 案件状态
    */
    private String caseStatus;
    /**
    * 案件类型
    */
    private String caseCategory;
    /**
    * 案件来源
    */
    private String caseSource;
    /**
    * 危害程度
    */
    private String harmLevel;
    /**
    * 案发开始时间
    */
    private Date beginTime;
    /**
    * 案发结束时间
    */
    private Date endTime;
    /**
    * 保密状态
    */
    private String classifyStatus;
    /**
    * 案件标签
    */
    private String caseTags;
    /**
    * 案发地点
    */
    private String crimeLocale;
    /**
    * 经度
    */
    private Double longitude;
    /**
    * 纬度
    */
    private Double latitude;
    /**
    * 所属辖区code
    */
    private String adminRegionCd;
    /**
    * 所属辖区value
    */
    private String adminRegionValue;
    /**
    * 行政区code
    */
    private String cantonCode;
    /**
    * 行政区名称
    */
    private String cantonName;
    /**
    * 归类
    */
    private String classification;
    /**
    * 损失情况
    */
    private String lossSituation;
    /**
    * 报警时间
    */
    private Date alarmTime;
    /**
    * 报警人员
    */
    private String alarmPeople;
    /**
    * 报警单位
    */
    private String alarmUnit;
    /**
    * 报警电话
    */
    private String alarmCall;
    /**
    * 报警途径
    */
    private String alarmWay;
    /**
    * 报警事由
    */
    private String alarmReasons;
    /**
    * 报警人陈述
    */
    private String alarmPeopleState;
    /**
    * 接报人员
    */
    private String reportedPeople;
    /**
    * 接报单位
    */
    private String reportedUnit;
    /**
    * 受理人员
    */
    private String acceptPeople;
    /**
    * 受理单位
    */
    private String acceptUnit;
    /**
    * 受理时间
    */
    private Date acceptTime;
    /**
    * 案件描述
    */
    private String caseDescription;
    /**
    * 作案时机
    */
    private String crimeTime;
    /**
    * 选择场所
    */
    private String siteSelection;
    /**
    * 伤害部位
    */
    private String injuryPart;
    /**
    * 作案人数
    */
    private String perpetratorCount;
    /**
    * 选择对象
    */
    private String targetSelection;
    /**
    * 手段特点
    */
    private String crimeFeature;
    /**
    * 上传时间
    */
    private Date uploadTime;
    /**
    * 上传用户
    */
    private String uploadUser;
    /**
    * 上传人员
    */
    private String uploadRole;
    /**
    * 更新时间
    */
    private Date updateTime;
    /**
    * 更新用户
    */
    private String updateUser;
    /**
    * 更新人员
    */
    private String updateRole;
    /**
    * 现场勘验号
    */
    private String crimeSceneInvestigateNo;
    /**
    * 案件属性
    */
    private String caseAttributes;
    /**
    * 案件性质
    */
    private String caseNature;
    /**
    * 立案日期
    */
    private Date recordCaseTime;
    /**
    * 破案日期
    */
    private Date solveCaseTime;
    /**
    * 破案单位
    */
    private String solveUnit;
    /**
    * 破案单位名称
    */
    private String solveUnitName;
    /**
    * 案件录入用户
    */
    private String createUser;
    /**
    * 案件录入用户所属单位
    */
    private String createDepart;
    /**
    * 状态码(D-删除,A-活跃)
    */
    private String statusCd;
    /**
    * 可见部门
    */
    private String visibleDepts;
    /**
    * 是否已串并（1-已串并，0-未串并）
    */
    private Object isSerial;
    /**
    * 是否有现场
    */
    private Object isScene;
    /**
    * 是否勘察现场
    */
    private Object isInvestigation;
    
    private Object isSycn;
    /**
    * 创建者
    */
    private String createdBy;
    /**
    * 更新者
    */
    private String updatedBy;
    /**
    * 创建时间
    */
    private Date createdTime;
    /**
    * 更新时间
    */
    private Date updatedTime;
    /**
    * 版本控制
    */
    private Long version;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCaseIdentify() {
        return caseIdentify;
    }

    public void setCaseIdentify(String caseIdentify) {
        this.caseIdentify = caseIdentify;
    }

    public String getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(String caseNo) {
        this.caseNo = caseNo;
    }

    public String getOrigNo() {
        return origNo;
    }

    public void setOrigNo(String origNo) {
        this.origNo = origNo;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public String getCaseStatus() {
        return caseStatus;
    }

    public void setCaseStatus(String caseStatus) {
        this.caseStatus = caseStatus;
    }

    public String getCaseCategory() {
        return caseCategory;
    }

    public void setCaseCategory(String caseCategory) {
        this.caseCategory = caseCategory;
    }

    public String getCaseSource() {
        return caseSource;
    }

    public void setCaseSource(String caseSource) {
        this.caseSource = caseSource;
    }

    public String getHarmLevel() {
        return harmLevel;
    }

    public void setHarmLevel(String harmLevel) {
        this.harmLevel = harmLevel;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getClassifyStatus() {
        return classifyStatus;
    }

    public void setClassifyStatus(String classifyStatus) {
        this.classifyStatus = classifyStatus;
    }

    public String getCaseTags() {
        return caseTags;
    }

    public void setCaseTags(String caseTags) {
        this.caseTags = caseTags;
    }

    public String getCrimeLocale() {
        return crimeLocale;
    }

    public void setCrimeLocale(String crimeLocale) {
        this.crimeLocale = crimeLocale;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getAdminRegionCd() {
        return adminRegionCd;
    }

    public void setAdminRegionCd(String adminRegionCd) {
        this.adminRegionCd = adminRegionCd;
    }

    public String getAdminRegionValue() {
        return adminRegionValue;
    }

    public void setAdminRegionValue(String adminRegionValue) {
        this.adminRegionValue = adminRegionValue;
    }

    public String getCantonCode() {
        return cantonCode;
    }

    public void setCantonCode(String cantonCode) {
        this.cantonCode = cantonCode;
    }

    public String getCantonName() {
        return cantonName;
    }

    public void setCantonName(String cantonName) {
        this.cantonName = cantonName;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getLossSituation() {
        return lossSituation;
    }

    public void setLossSituation(String lossSituation) {
        this.lossSituation = lossSituation;
    }

    public Date getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(Date alarmTime) {
        this.alarmTime = alarmTime;
    }

    public String getAlarmPeople() {
        return alarmPeople;
    }

    public void setAlarmPeople(String alarmPeople) {
        this.alarmPeople = alarmPeople;
    }

    public String getAlarmUnit() {
        return alarmUnit;
    }

    public void setAlarmUnit(String alarmUnit) {
        this.alarmUnit = alarmUnit;
    }

    public String getAlarmCall() {
        return alarmCall;
    }

    public void setAlarmCall(String alarmCall) {
        this.alarmCall = alarmCall;
    }

    public String getAlarmWay() {
        return alarmWay;
    }

    public void setAlarmWay(String alarmWay) {
        this.alarmWay = alarmWay;
    }

    public String getAlarmReasons() {
        return alarmReasons;
    }

    public void setAlarmReasons(String alarmReasons) {
        this.alarmReasons = alarmReasons;
    }

    public String getAlarmPeopleState() {
        return alarmPeopleState;
    }

    public void setAlarmPeopleState(String alarmPeopleState) {
        this.alarmPeopleState = alarmPeopleState;
    }

    public String getReportedPeople() {
        return reportedPeople;
    }

    public void setReportedPeople(String reportedPeople) {
        this.reportedPeople = reportedPeople;
    }

    public String getReportedUnit() {
        return reportedUnit;
    }

    public void setReportedUnit(String reportedUnit) {
        this.reportedUnit = reportedUnit;
    }

    public String getAcceptPeople() {
        return acceptPeople;
    }

    public void setAcceptPeople(String acceptPeople) {
        this.acceptPeople = acceptPeople;
    }

    public String getAcceptUnit() {
        return acceptUnit;
    }

    public void setAcceptUnit(String acceptUnit) {
        this.acceptUnit = acceptUnit;
    }

    public Date getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(Date acceptTime) {
        this.acceptTime = acceptTime;
    }

    public String getCaseDescription() {
        return caseDescription;
    }

    public void setCaseDescription(String caseDescription) {
        this.caseDescription = caseDescription;
    }

    public String getCrimeTime() {
        return crimeTime;
    }

    public void setCrimeTime(String crimeTime) {
        this.crimeTime = crimeTime;
    }

    public String getSiteSelection() {
        return siteSelection;
    }

    public void setSiteSelection(String siteSelection) {
        this.siteSelection = siteSelection;
    }

    public String getInjuryPart() {
        return injuryPart;
    }

    public void setInjuryPart(String injuryPart) {
        this.injuryPart = injuryPart;
    }

    public String getPerpetratorCount() {
        return perpetratorCount;
    }

    public void setPerpetratorCount(String perpetratorCount) {
        this.perpetratorCount = perpetratorCount;
    }

    public String getTargetSelection() {
        return targetSelection;
    }

    public void setTargetSelection(String targetSelection) {
        this.targetSelection = targetSelection;
    }

    public String getCrimeFeature() {
        return crimeFeature;
    }

    public void setCrimeFeature(String crimeFeature) {
        this.crimeFeature = crimeFeature;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getUploadUser() {
        return uploadUser;
    }

    public void setUploadUser(String uploadUser) {
        this.uploadUser = uploadUser;
    }

    public String getUploadRole() {
        return uploadRole;
    }

    public void setUploadRole(String uploadRole) {
        this.uploadRole = uploadRole;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getUpdateRole() {
        return updateRole;
    }

    public void setUpdateRole(String updateRole) {
        this.updateRole = updateRole;
    }

    public String getCrimeSceneInvestigateNo() {
        return crimeSceneInvestigateNo;
    }

    public void setCrimeSceneInvestigateNo(String crimeSceneInvestigateNo) {
        this.crimeSceneInvestigateNo = crimeSceneInvestigateNo;
    }

    public String getCaseAttributes() {
        return caseAttributes;
    }

    public void setCaseAttributes(String caseAttributes) {
        this.caseAttributes = caseAttributes;
    }

    public String getCaseNature() {
        return caseNature;
    }

    public void setCaseNature(String caseNature) {
        this.caseNature = caseNature;
    }

    public Date getRecordCaseTime() {
        return recordCaseTime;
    }

    public void setRecordCaseTime(Date recordCaseTime) {
        this.recordCaseTime = recordCaseTime;
    }

    public Date getSolveCaseTime() {
        return solveCaseTime;
    }

    public void setSolveCaseTime(Date solveCaseTime) {
        this.solveCaseTime = solveCaseTime;
    }

    public String getSolveUnit() {
        return solveUnit;
    }

    public void setSolveUnit(String solveUnit) {
        this.solveUnit = solveUnit;
    }

    public String getSolveUnitName() {
        return solveUnitName;
    }

    public void setSolveUnitName(String solveUnitName) {
        this.solveUnitName = solveUnitName;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateDepart() {
        return createDepart;
    }

    public void setCreateDepart(String createDepart) {
        this.createDepart = createDepart;
    }

    public String getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(String statusCd) {
        this.statusCd = statusCd;
    }

    public String getVisibleDepts() {
        return visibleDepts;
    }

    public void setVisibleDepts(String visibleDepts) {
        this.visibleDepts = visibleDepts;
    }

    public Object getIsSerial() {
        return isSerial;
    }

    public void setIsSerial(Object isSerial) {
        this.isSerial = isSerial;
    }

    public Object getIsScene() {
        return isScene;
    }

    public void setIsScene(Object isScene) {
        this.isScene = isScene;
    }

    public Object getIsInvestigation() {
        return isInvestigation;
    }

    public void setIsInvestigation(Object isInvestigation) {
        this.isInvestigation = isInvestigation;
    }

    public Object getIsSycn() {
        return isSycn;
    }

    public void setIsSycn(Object isSycn) {
        this.isSycn = isSycn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

}