package autoconfiguration.delay;

import cn.hutool.core.util.CharUtil;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;

public class DelayCondition extends SpringBootCondition {

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context,
                                            AnnotatedTypeMetadata metadata) {
        String sourceClass = "";
        if (metadata instanceof ClassMetadata) {
            sourceClass = ((ClassMetadata) metadata).getClassName();
        }
        ConditionMessage.Builder message = ConditionMessage.forCondition("delay channel",
                sourceClass);
        RelaxedPropertyResolver resolver = new RelaxedPropertyResolver(
                context.getEnvironment(), DelayAutoConfiguration.CONF_PREFIX + CharUtil.DOT);
        if (!resolver.containsProperty("type")) {
            return ConditionOutcome.match(message.because("automatic delay channel"));
        }
        ChannelType cacheType = DelayConfigurations
                .getType(((AnnotationMetadata) metadata).getClassName());
        String value = resolver.getProperty("type").replace('-', '_').toUpperCase();
        if (value.equals(cacheType.name())) {
            return ConditionOutcome.match(message.because(value + " delay channel"));
        }
        return ConditionOutcome.noMatch(message.because(value + " delay channel"));
    }
}