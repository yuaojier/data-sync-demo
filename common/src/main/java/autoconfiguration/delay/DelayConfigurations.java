package autoconfiguration.delay;

import com.google.common.collect.Maps;
import org.springframework.util.Assert;

import java.util.Map;

final class DelayConfigurations {
    private static Map<ChannelType, Class<?>> MAPPINGS;

    static {
        MAPPINGS = Maps.newHashMap();
        MAPPINGS.put(ChannelType.LOCAL, HashedWheelDelayConfiguration.class);
    }

    public static String getConfigurationClass(ChannelType channelType) {
        Class<?> configurationClass = MAPPINGS.get(channelType);
        Assert.state(configurationClass != null, "Unknown delay channel " + channelType);
        return configurationClass.getName();
    }

    public static ChannelType getType(String configurationClassName) {
        for (Map.Entry<ChannelType, Class<?>> entry : MAPPINGS.entrySet()) {
            if (entry.getValue().getName().equals(configurationClassName)) {
                return entry.getKey();
            }
        }
        throw new IllegalStateException(
                "Unknown configuration class " + configurationClassName);
    }
}
