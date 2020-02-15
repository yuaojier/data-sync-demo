package autoconfiguration.delay;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(DelayAutoConfiguration.CONF_PREFIX)
public class DelayProperties {
    /**
     * 延迟通道类型
     */
    private ChannelType type;
}
