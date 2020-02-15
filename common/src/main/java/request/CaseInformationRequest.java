package request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaseInformationRequest implements Serializable {
    @ApiModelProperty(value = "同步案件的时间,非同步时间")
    @NotBlank
    private Date date;
    @ApiModelProperty(value = "幂等 保证时空唯一性")
    @NotBlank
    private String idempotent;
    @ApiModelProperty(value = "来源")
    @NotNull
    private String source;
    @ApiModelProperty(value = "案件信息")
    @NotNull
    private List<CaseInformation> caseInformationList;
}
