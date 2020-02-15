package autoconfiguration.swagger;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.AllowableListValues;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.swagger2.configuration.Swagger2DocumentationConfiguration;
import swagger.DocketInfo;
import swagger.Swagger2Properties;
import swagger.validator.HibernateValidatorRequiredPlugin;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static autoconfiguration.swagger.Swagger2Configuration.CONFIG_PREFIX;


/**
 * swagger 自动配置
 *
 */
@Configuration
@EnableSwagger2
@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = CONFIG_PREFIX, name = "enabled", matchIfMissing = true)
@ConditionalOnClass({Docket.class, Swagger2DocumentationConfiguration.class, DocketInfo.class})
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
@Import(BeanValidatorPluginsConfiguration.class)
public class Swagger2Configuration implements BeanFactoryAware {
    static final String CONFIG_PREFIX = "spring.swagger2";

    /**
     * swagger-ui访问静态资源必须为根路径, 如果mvc静态资源路径配置为其他路径后, swagger-ui访问不到接口json文档
     * <p/>
     * {@link WebMvcAutoConfigurationAdapter}中判断是否修改了静态路径, 如果修改后则重新注册根路径/**
     */
    static final String SWAGGER_UI_REQUIRE_STATIC_PATH_PATTERN = "/**";

    private BeanFactory beanFactory;

    @Bean
    @ConfigurationProperties(prefix = CONFIG_PREFIX)
    public Swagger2Properties swagger2Properties() {
        return new Swagger2Properties();
    }

    private Docket buildDocket(String groupName, DocketInfo docketInfo, List<Parameter> parameters, Class[] ignoreTypes) {
        ConfigurableBeanFactory configurableBeanFactory = (ConfigurableBeanFactory) beanFactory;
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title(docketInfo.getTitle())
                .description(docketInfo.getDescription())
                .version(docketInfo.getVersion())
                .license(docketInfo.getLicense())
                .licenseUrl(docketInfo.getLicenseUrl())
                .contact(
                        new Contact(
                                docketInfo.getContact().getName(),
                                docketInfo.getContact().getUrl(),
                                docketInfo.getContact().getEmail()
                        )
                )
                .termsOfServiceUrl(docketInfo.getTermsOfServiceUrl())
                .build();

        // base-path处理
        // 当没有配置任何path的时候，解析/**
        if (docketInfo.getBasePath().isEmpty()) {
            docketInfo.getBasePath().add("/**");
        }
        List<Predicate<String>> basePath = Lists.newArrayList();
        for (String path : docketInfo.getBasePath()) {
            basePath.add(PathSelectors.ant(path));
        }

        // exclude-path处理
        List<Predicate<String>> excludePath = Lists.newArrayList();
        for (String path : docketInfo.getExcludePath()) {
            excludePath.add(PathSelectors.ant(path));
        }

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .host(docketInfo.getHost())
                .apiInfo(apiInfo)
                .globalOperationParameters(parameters)
                .groupName(groupName)
                .select()
                .apis(requestHandlerPredicate(docketInfo.getBasePackage()))
                .paths(
                        Predicates.and(
                                Predicates.not(Predicates.or(excludePath)),
                                Predicates.or(basePath)
                        )
                )
                .build();
        if (ignoreTypes != null) {
            docket.ignoredParameterTypes(ignoreTypes);
        }
        configurableBeanFactory.registerSingleton(groupName, docket);
        return docket;
    }

    private Predicate<RequestHandler> requestHandlerPredicate(String[] basePackages) {
        List<Predicate<RequestHandler>> predicates = Lists.newArrayList();
        for (String basePackage : basePackages) {
            predicates.add(RequestHandlerSelectors.basePackage(basePackage));
        }
        return Predicates.or(predicates);
    }

    @Bean
    @ConditionalOnMissingBean
    public List<Docket> createRestApi(Swagger2Properties swaggerProperties) {
        List<Docket> docketList = new LinkedList<>();
        if (!(StringUtils.isEmpty(swaggerProperties.getBasePackage()) && StringUtils.isEmpty(swaggerProperties.getBasePath()))) {
            docketList.add(buildDocket("default", swaggerProperties, buildGlobalOperationParametersFromSwagger2Properties(swaggerProperties.getGlobalOperationParameters()), swaggerProperties.getIgnoreTypes()));
        }

        // 分组创建
        for (String groupName : swaggerProperties.getDocket().keySet()) {
            DocketInfo docketInfo = swaggerProperties.getDocket().get(groupName);
            BeanUtil.copyProperties(swaggerProperties, docketInfo, CopyOptions.create().setIgnoreError(true).setEditable(DocketInfo.class).setIgnoreNullValue(true).setIgnoreProperties("description", "title", "basePackage", "basePath", "excludePath"));
            docketList.add(buildDocket(groupName, docketInfo, assemblyGlobalOperationParameters(swaggerProperties.getGlobalOperationParameters(), docketInfo.getGlobalOperationParameters()), swaggerProperties.getIgnoreTypes()));
        }
        return docketList;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    private List<Parameter> buildGlobalOperationParametersFromSwagger2Properties(
            List<Swagger2Properties.GlobalOperationParameter> globalOperationParameters) {
        List<Parameter> parameters = Lists.newArrayList();

        if (Objects.isNull(globalOperationParameters)) {
            return parameters;
        }
        for (Swagger2Properties.GlobalOperationParameter globalOperationParameter : globalOperationParameters) {
            Parameter parameter = new ParameterBuilder()
                    .name(globalOperationParameter.getName())
                    .description(globalOperationParameter.getDescription())
                    .modelRef(new ModelRef(globalOperationParameter.getModelRef()))
                    .parameterType(globalOperationParameter.getParameterType())
                    .required(Boolean.parseBoolean(globalOperationParameter.getRequired()))
                    .defaultValue(globalOperationParameter.getDefValue())
                    .allowableValues(ArrayUtil.isEmpty(globalOperationParameter.getAllowValues()) ? null : new AllowableListValues(Arrays.asList(globalOperationParameter.getAllowValues()), "string"))
                    .build();
            parameters.add(parameter);

        }
        return parameters;
    }

    /**
     * 局部参数按照name覆盖局部参数
     *
     * @param globalOperationParameters
     * @param docketOperationParameters
     * @return
     */
    private List<Parameter> assemblyGlobalOperationParameters(
            List<Swagger2Properties.GlobalOperationParameter> globalOperationParameters,
            List<Swagger2Properties.GlobalOperationParameter> docketOperationParameters) {
        List<Swagger2Properties.GlobalOperationParameter> merged = Lists.newArrayList();

        if (CollectionUtil.isNotEmpty(docketOperationParameters)) {
            CollectionUtil.addAll(merged, docketOperationParameters);
        }

        if (CollectionUtil.isNotEmpty(globalOperationParameters)) {
            CollectionUtil.addAll(merged, globalOperationParameters.stream()
                    .filter(parameter -> merged.stream()
                            .noneMatch(mergeParameter -> StrUtil.equalsIgnoreCase(mergeParameter.getName(), parameter.getName()))
                    ).collect(Collectors.toList()));
        }
        return buildGlobalOperationParametersFromSwagger2Properties(merged);
    }

    @Configuration
    @ConditionalOnClass(NotEmpty.class)
    public static class Swagger2HibernateValidatorConfig {
        @Bean
        public HibernateValidatorRequiredPlugin hibernateValidatorRequiredPlugin() {
            return new HibernateValidatorRequiredPlugin();
        }
    }

    @Configuration
    @ConditionalOnProperty(prefix = CONFIG_PREFIX, name = "enabled", matchIfMissing = true)
    @EnableConfigurationProperties({WebMvcProperties.class, ResourceProperties.class})
    public static class WebMvcAutoConfigurationAdapter extends WebMvcConfigurerAdapter {
        private final ResourceProperties resourceProperties;

        private final WebMvcProperties mvcProperties;

        public WebMvcAutoConfigurationAdapter(ResourceProperties resourceProperties, WebMvcProperties mvcProperties) {
            this.resourceProperties = resourceProperties;
            this.mvcProperties = mvcProperties;
        }

        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            if (!Objects.equals(mvcProperties.getStaticPathPattern(), SWAGGER_UI_REQUIRE_STATIC_PATH_PATTERN)) {
                if (!registry.hasMappingForPattern(SWAGGER_UI_REQUIRE_STATIC_PATH_PATTERN)) {
                    registry.addResourceHandler(SWAGGER_UI_REQUIRE_STATIC_PATH_PATTERN)
                            .addResourceLocations(
                                    this.resourceProperties.getStaticLocations())
                            .setCachePeriod(resourceProperties.getCachePeriod());
                }
            }
        }

    }
}
