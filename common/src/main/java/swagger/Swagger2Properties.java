package swagger;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Configuration class of Swagger2Configuration
 *
 * @author Nick
 * @since 0.0.1
 */
@Data
public class Swagger2Properties extends DocketInfo {

    /**
     * 是否开启swagger
     **/
    private Boolean enabled;

    /**
     * 分组文档
     **/
    private Map<String, DocketInfo> docket = new LinkedHashMap<>();


    /**
     * 全局参数配置
     **/
    private List<GlobalOperationParameter> globalOperationParameters;

    private Class[] ignoreTypes;

    @Data
    @NoArgsConstructor
    public static class GlobalOperationParameter {
        /**
         * 参数名
         **/
        private String name;

        /**
         * 描述信息
         **/
        private String description;

        /**
         * 指定参数类型
         **/
        private String modelRef;

        /**
         * 参数放在哪个地方:header,query,path,body.form
         **/
        private String parameterType;

        /**
         * 参数是否必须传
         **/
        private String required;

        /**
         * 默认值
         */
        private String defValue;

        private String[] allowValues;

    }

    @Data
    @NoArgsConstructor
    public static class Contact {

        /**
         * 联系人
         **/
        private String name = "";

        /**
         * 联系人url
         **/
        private String url = "";

        /**
         * 联系人email
         **/
        private String email = "";

    }
}
