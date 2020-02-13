package entity;

import java.util.Date;
import java.io.Serializable;

/**
 * (CaseClueInfo)实体类
 *
 * @author makejava
 * @since 2020-02-11 11:10:35
 */
public class CaseClueInfo implements Serializable {
    private static final long serialVersionUID = 527854141097976861L;
    
    private Long id;
    /**
    * 线索编号
    */
    private String clueNo;
    /**
    * 线索名称
    */
    private String clueName;
    /**
    * 线索时间
    */
    private Date clueTime;
    /**
    * 研判说明、线索说明
    */
    private String judgeExplain;
    /**
    * 保密状态
    */
    private String classifyStatus;
    /**
    * 保密时限
    */
    private Date classifyTime;
    /**
    * 所属辖区
    */
    private String jurisdictions;
    /**
    * 所属辖区(编号)
    */
    private String jurisdictionCd;
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
    private String uploadPerson;
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
    private String updatePerson;
    /**
    * 线索类型
    */
    private String clueType;
    /**
    * 是否上传
    */
    private Object isUploaded;
    /**
    * 问题类型
    */
    private String problemType;
    /**
    * 问题描述
    */
    private String problemDesc;
    /**
    * 状态码(D-删除,A-活跃,I-待审核)
    */
    private String statusCd;
    /**
    * 可见部门
    */
    private String visibleDepts;
    /**
    * 行政区code
    */
    private String cantonCode;
    /**
    * 行政区名称
    */
    private String cantonName;
    /**
    * 线索来源
    */
    private String clueSource;
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

    public String getClueNo() {
        return clueNo;
    }

    public void setClueNo(String clueNo) {
        this.clueNo = clueNo;
    }

    public String getClueName() {
        return clueName;
    }

    public void setClueName(String clueName) {
        this.clueName = clueName;
    }

    public Date getClueTime() {
        return clueTime;
    }

    public void setClueTime(Date clueTime) {
        this.clueTime = clueTime;
    }

    public String getJudgeExplain() {
        return judgeExplain;
    }

    public void setJudgeExplain(String judgeExplain) {
        this.judgeExplain = judgeExplain;
    }

    public String getClassifyStatus() {
        return classifyStatus;
    }

    public void setClassifyStatus(String classifyStatus) {
        this.classifyStatus = classifyStatus;
    }

    public Date getClassifyTime() {
        return classifyTime;
    }

    public void setClassifyTime(Date classifyTime) {
        this.classifyTime = classifyTime;
    }

    public String getJurisdictions() {
        return jurisdictions;
    }

    public void setJurisdictions(String jurisdictions) {
        this.jurisdictions = jurisdictions;
    }

    public String getJurisdictionCd() {
        return jurisdictionCd;
    }

    public void setJurisdictionCd(String jurisdictionCd) {
        this.jurisdictionCd = jurisdictionCd;
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

    public String getUploadPerson() {
        return uploadPerson;
    }

    public void setUploadPerson(String uploadPerson) {
        this.uploadPerson = uploadPerson;
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

    public String getUpdatePerson() {
        return updatePerson;
    }

    public void setUpdatePerson(String updatePerson) {
        this.updatePerson = updatePerson;
    }

    public String getClueType() {
        return clueType;
    }

    public void setClueType(String clueType) {
        this.clueType = clueType;
    }

    public Object getIsUploaded() {
        return isUploaded;
    }

    public void setIsUploaded(Object isUploaded) {
        this.isUploaded = isUploaded;
    }

    public String getProblemType() {
        return problemType;
    }

    public void setProblemType(String problemType) {
        this.problemType = problemType;
    }

    public String getProblemDesc() {
        return problemDesc;
    }

    public void setProblemDesc(String problemDesc) {
        this.problemDesc = problemDesc;
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

    public String getClueSource() {
        return clueSource;
    }

    public void setClueSource(String clueSource) {
        this.clueSource = clueSource;
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