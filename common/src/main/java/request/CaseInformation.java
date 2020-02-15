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
    @ApiModelProperty(value = "线索编号")
    @NotBlank
    private String clueNo;
    @ApiModelProperty(value = "线索名称")
    private String clueName;
    @ApiModelProperty(value = "线索时间")
    private Date clueTime;
    @ApiModelProperty(value = "研判说明、线索说明")
    private String judgeExplain;
    @ApiModelProperty(value = "保密状态")
    private String classifyStatus;
    @ApiModelProperty(value = "保密时限")
    private Date classifyTime;
    @ApiModelProperty(value = "所属辖区")
    private String jurisdictions;
    @ApiModelProperty(value = "所属辖区(编号)")
    private String jurisdictionCd;
    @ApiModelProperty(value = "上传时间")
    private Date uploadTime;
    @ApiModelProperty(value = "上传用户")
    private String uploadUser;
    @ApiModelProperty(value = "上传人员")
    private String uploadPerson;
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
    @ApiModelProperty(value = "更新用户")
    private String updateUser;
    @ApiModelProperty(value = "更新人员")
    private String updatePerson;
    @ApiModelProperty(value = "线索类型")
    private String clueType;
    @ApiModelProperty(value = "是否上传")
    private Object isUploaded;
    @ApiModelProperty(value = "问题类型")
    private String problemType;
    @ApiModelProperty(value = "问题描述")
    private String problemDesc;
    @ApiModelProperty(value = "状态码(D-删除,A-活跃,I-待审核)")
    private String statusCd;
    @ApiModelProperty(value = "可见部门")
    private String visibleDepts;
    @ApiModelProperty(value = "行政区code")
    private String cantonCode;
    @ApiModelProperty(value = "行政区名称")
    private String cantonName;
    @ApiModelProperty(value = "线索来源")
    private String clueSource;
    @ApiModelProperty(value = "创建者")
    private String createdBy;
    @ApiModelProperty(value = "更新者")
    private String updatedBy;
    @ApiModelProperty(value = "创建时间")
    private Date createdTime;
    @ApiModelProperty(value = "更新时间")
    private Date updatedTime;
    @ApiModelProperty(value = "版本控制")
    private Long version;
}
