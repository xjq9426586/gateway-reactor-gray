

package com.github.xjq.ribbongray.config;

import com.github.xjq.ribbongray.rule.MetadataCanaryRuleHandler;
import com.netflix.loadbalancer.ZoneAvoidanceRule;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.netflix.ribbon.RibbonClientConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

@Configuration
@AutoConfigureBefore({RibbonClientConfiguration.class})
@ConditionalOnProperty(
    prefix = "grayscale",
    name = {"enabled"},
    havingValue = "true"
)
public class RibbonMetaFilterAutoConfiguration {
    public RibbonMetaFilterAutoConfiguration() {
    }

    @Bean
    @ConditionalOnMissingBean
    @Scope(value = "prototype")
    public ZoneAvoidanceRule metadataAwareRule() {
        return new MetadataCanaryRuleHandler();
    }
}
