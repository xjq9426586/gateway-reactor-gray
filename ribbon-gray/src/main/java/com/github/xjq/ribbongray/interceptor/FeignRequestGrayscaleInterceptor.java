
package com.github.xjq.ribbongray.interceptor;

import feign.Feign;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@ConditionalOnProperty(
    prefix = "grayscale",
    name = {"enabled"},
    havingValue = "true"
)
@ConditionalOnClass({Feign.class})
@Component
@Order
public class FeignRequestGrayscaleInterceptor implements RequestInterceptor {
    public FeignRequestGrayscaleInterceptor() {
    }

    public void apply(RequestTemplate requestTemplate) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();
            String version = request.getHeader("g-version");
            if (StringUtils.isNotEmpty(version)) {
                requestTemplate.header("g-version", new String[]{version});
            }

        }
    }
}
