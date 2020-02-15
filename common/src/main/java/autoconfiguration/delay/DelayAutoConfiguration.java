package autoconfiguration.delay;

import delay.*;
import org.springframework.boot.autoconfigure.cache.CacheType;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

@Configuration
@EnableConfigurationProperties(DelayProperties.class)
@Import(DelayAutoConfiguration.DelayConfigurationImportSelector.class)
public class DelayAutoConfiguration {
    static final String CONF_PREFIX = "delay";

    @Bean
    @ConditionalOnMissingBean
    public TaskStorageFactory memoryTaskStorageFactory() {
        return new MemoryTaskStorageFactory();
    }

    @Bean
    @ConditionalOnMissingBean
    public DelayTaskStorage defaultDelayTaskStorage(TaskStorageFactory taskStorageFactory) {
        DefaultDelayTaskStorage delayTaskStorage = new DefaultDelayTaskStorage();
        delayTaskStorage.setPaddingTask(taskStorageFactory.getPaddingStorage());
        delayTaskStorage.setRecoverTask(taskStorageFactory.getRecoverStorage());
        delayTaskStorage.setRemoveTask(taskStorageFactory.getRemoveStorage());
        return delayTaskStorage;
    }


    @Bean
    public DelayTaskExecutor delayTaskExecutor() {
        DelayTaskExecutor delayTaskExecutor = new DelayTaskExecutor();
        return delayTaskExecutor;
    }

    /**
     * {@link ImportSelector} to add {@link CacheType} configuration classes.
     */
    static class DelayConfigurationImportSelector implements ImportSelector {

        @Override
        public String[] selectImports(AnnotationMetadata importingClassMetadata) {
            ChannelType[] types = ChannelType.values();
            String[] imports = new String[types.length];
            for (int i = 0; i < types.length; i++) {
                imports[i] = DelayConfigurations.getConfigurationClass(types[i]);
            }
            return imports;
        }

    }

}
