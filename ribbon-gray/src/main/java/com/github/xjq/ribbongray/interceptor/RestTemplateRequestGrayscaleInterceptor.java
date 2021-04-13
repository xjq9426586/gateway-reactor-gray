package com.github.xjq.ribbongray.interceptor;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import lombok.NonNull;
import org.apache.commons.lang.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@ConditionalOnProperty(
    prefix = "grayscale",
    name = {"enabled"},
    havingValue = "true"
)
@ConditionalOnClass({RestTemplate.class})
@Component
@Order
public class RestTemplateRequestGrayscaleInterceptor implements ClientHttpRequestInterceptor {
    public RestTemplateRequestGrayscaleInterceptor() {
    }

    @NonNull
    public ClientHttpResponse intercept(@NonNull HttpRequest httpRequest, @NonNull byte[] bytes, @NonNull ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        if (httpRequest == null) {
            throw new NullPointerException("httpRequest");
        } else if (bytes == null) {
            throw new NullPointerException("bytes");
        } else if (clientHttpRequestExecution == null) {
            throw new NullPointerException("clientHttpRequestExecution");
        } else {
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            if (requestAttributes == null) {
                return clientHttpRequestExecution.execute(httpRequest, bytes);
            } else {
                HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();
                String version = request.getHeader("g-version");
                if (StringUtils.isNotEmpty(version)) {
                    httpRequest.getHeaders().add("g-version", version);
                }

                return clientHttpRequestExecution.execute(httpRequest, bytes);
            }
        }
    }
}
