package request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaseInformation implements Serializable {
    /**
     * 案件标识
     */
    @NotBlank
    @ApiModelProperty("案件标识")
    private String caseIdentify;
    /**
     * 案件编号
     */
    @ApiModelProperty("案件编号")
    private String caseNo;
    /**
     * 原始编号（警情编号）
     */
    @ApiModelProperty("原始编号（警情编号）")
    private String origNo;
    /**
     * 案件名称
     */
    @ApiModelProperty("案件名称")
    private String caseName;
    /**
     * 案件状态
     */
    @ApiModelProperty("案件状态")
    private String caseStatus;
    /**
     * 案件类型
     */
    @ApiModelProperty("案件类型")
    private String caseCategory;
    /**
     * 案件来源
     */
    @ApiModelProperty("案件来源")
    private String caseSource;
    /**
     * 危害程度
     */
    @ApiModelProperty("危害程度")
    private String harmLevel;
    /**
     * 案发开始时间
     */
    @ApiModelProperty("案发开始时间")
    private Date beginTime;
    /**
     * 案发结束时间
     */
    @ApiModelProperty("案发结束时间")
    private Date endTime;
    /**
     * 保密状态
     */
    @ApiModelProperty("保密状态")
    private String classifyStatus;
    /**
     * 案件标签
     */
    @ApiModelProperty("案件标签")
    private String caseTags;
    /**
     * 案发地点
     */
    @ApiModelProperty("案发地点")
    private String crimeLocale;
    /**
     * 经度
     */
    @ApiModelProperty("经度")
    private Double longitude;
    /**
     * 纬度
     */
    @ApiModelProperty("纬度")
    private Double latitude;
    /**
     * 所属辖区code
     */
    @ApiModelProperty("所属辖区code")
    private String adminRegionCd;
    /**
     * 所属辖区value
     */
    @ApiModelProperty("所属辖区value")
    private String adminRegionValue;
    /**
     * 行政区code
     */
    @ApiModelProperty("行政区code")
    private String cantonCode;
    /**
     * 行政区名称
     */
    @ApiModelProperty("行政区名称")
    private String cantonName;
    /**
     * 归类
     */
    @ApiModelProperty("归类")
    private String classification;
    /**
     * 损失情况
     */
    @ApiModelProperty("损失情况")
    private String lossSituation;
    /**
     * 报警时间
     */
    @ApiModelProperty("报警时间")
    private Date alarmTime;
    /**
     * 报警人员
     */
    @ApiModelProperty("报警人员")
    private String alarmPeople;
    /**
     * 报警单位
     */
    @ApiModelProperty("报警单位")
    private String alarmUnit;
    /**
     * 报警电话
     */
    @ApiModelProperty("报警电话")
    private String alarmCall;
    /**
     * 报警途径
     */
    @ApiModelProperty("报警途径")
    private String alarmWay;
    /**
     * 报警事由
     */
    @ApiModelProperty("报警事由")
    private String alarmReasons;
    /**
     * 报警人陈述
     */
    @ApiModelProperty("报警人陈述")
    private String alarmPeopleState;
    /**
     * 接报人员
     */
    @ApiModelProperty("接报人员")
    private String reportedPeople;
    /**
     * 接报单位
     */
    @ApiModelProperty("接报单位")
    private String reportedUnit;
    /**
     * 受理人员
     */
    @ApiModelProperty("受理人员")
    private String acceptPeople;
    /**
     * 受理单位
     */
    @ApiModelProperty("受理单位")
    private String acceptUnit;
    /**
     * 受理时间
     */
    @ApiModelProperty("受理时间")
    private Date acceptTime;
    /**
     * 案件描述
     */
    @ApiModelProperty("案件描述")
    private String caseDescription;
    /**
     * 作案时机
     */
    @ApiModelProperty("作案时机")
    private String crimeTime;
    /**
     * 选择场所
     */
    @ApiModelProperty("选择场所")
    private String siteSelection;
    /**
     * 伤害部位
     */
    @ApiModelProperty("伤害部位")
    private String injuryPart;
    /**
     * 作案人数
     */
    @ApiModelProperty("作案人数")
    private String perpetratorCount;
    /**
     * 选择对象
     */
    @ApiModelProperty("选择对象")
    private String targetSelection;
    /**
     * 手段特点
     */
    @ApiModelProperty("手段特点")
    private String crimeFeature;
    /**
     * 上传时间
     */
    @ApiModelProperty("上传时间")
    private Date uploadTime;
    /**
     * 上传用户
     */
    @ApiModelProperty("上传用户")
    private String uploadUser;
    /**
     * 上传人员
     */
    @ApiModelProperty("上传人员")
    private String uploadRole;
    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private Date updateTime;
    /**
     * 更新用户
     */
    @ApiModelProperty("更新用户")
    private String updateUser;
    /**
     * 更新人员
     */
    @ApiModelProperty("更新人员")
    private String updateRole;
    /**
     * 现场勘验号
     */
    @ApiModelProperty("现场勘验号")
    private String crimeSceneInvestigateNo;
    /**
     * 案件属性
     */
    @ApiModelProperty("案件属性")
    private String caseAttributes;
    /**
     * 案件性质
     */
    @ApiModelProperty("案件性质")
    private String caseNature;
    /**
     * 立案日期
     */
    @ApiModelProperty("立案日期")
    private Date recordCaseTime;
    /**
     * 破案日期
     */
    @ApiModelProperty("破案日期")
    private Date solveCaseTime;
    /**
     * 破案单位
     */
    @ApiModelProperty("破案单位")
    private String solveUnit;
    /**
     * 破案单位名称
     */
    @ApiModelProperty("破案单位名称")
    private String solveUnitName;
    /**
     * 案件录入用户
     */
    @ApiModelProperty("案件录入用户")
    private String createUser;
    /**
     * 案件录入用户所属单位
     */
    @ApiModelProperty("案件录入用户所属单位")
    private String createDepart;
    /**
     * 状态码(D-删除,A-活跃)
     */
    @ApiModelProperty("状态码(D-删除,A-活跃)")
    private String statusCd;
    /**
     * 可见部门
     */
    @ApiModelProperty("可见部门")
    private String visibleDepts;
    /**
     * 是否已串并（1-已串并，0-未串并）
     */
    @ApiModelProperty("是否已串并（1-已串并，0-未串并）")
    private int isSerial;
    /**
     * 是否有现场
     */
    @ApiModelProperty("是否有现场")
    private int isScene;
    /**
     * 是否勘察现场
     */
    @ApiModelProperty("是否勘察现场")
    private int isInvestigation;

    private int isSycn;
    /**
     * 创建者
     */
    @ApiModelProperty("创建者")
    private String createdBy;
    /**
     * 更新者
     */
    @ApiModelProperty("更新者")
    private String updatedBy;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createdTime;
    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private Date updatedTime;
    /**
     * 版本控制
     */
    @ApiModelProperty("版本控制")
    private Long version;
}
