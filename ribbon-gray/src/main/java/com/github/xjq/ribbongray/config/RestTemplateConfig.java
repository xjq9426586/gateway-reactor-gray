
package com.github.xjq.ribbongray.config;

import com.github.xjq.ribbongray.interceptor.RestTemplateRequestGrayscaleInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@ConditionalOnProperty(
    prefix = "grayscale",
    name = {"enabled"},
    havingValue = "true"
)
@ConditionalOnBean({RestTemplate.class})
@Component
@Order
public class RestTemplateConfig {
    private static final Logger log = LoggerFactory.getLogger(RestTemplateConfig.class);

    public RestTemplateConfig() {
    }

    @Bean
    public Object replaceWithCloudRestTemplate(RestTemplate restTemplate, RestTemplateRequestGrayscaleInterceptor interceptor) {
        log.info("grayscale init start, bean restTemplate add interceptor!");
        if (interceptor != null) {
            restTemplate.getInterceptors().add(interceptor);
        }

        return new Object();
    }
}
