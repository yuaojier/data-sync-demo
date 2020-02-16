package com.kedacom.synccore;

import biz.RetryPolicy;
import com.kedacom.synccore.api.dto.CaseSyncType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;
import java.util.Optional;

@Data
@ConfigurationProperties(prefix = "kedacom.sync")
public class SyncRetryProperties {
    private Map<CaseSyncType, RetryPolicy> retry;

    public RetryPolicy getPolicy(CaseSyncType apiType) {
        return Optional.ofNullable(retry).map(payApiTypeRetryPolicyMap -> payApiTypeRetryPolicyMap.get(apiType)).orElse(null);
    }
}
