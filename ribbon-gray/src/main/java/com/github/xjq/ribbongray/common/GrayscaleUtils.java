package com.github.xjq.ribbongray.common;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class GrayscaleUtils {
    private static final Logger log = LoggerFactory.getLogger(GrayscaleUtils.class);

    public GrayscaleUtils() {
    }

    public static String getGVersion() {
        return getHeaderData("g-version");
    }

    public static String getHeaderData(String key) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        String value = "";
        if (requestAttributes != null) {
            HttpServletRequest httpServletRequest = ((ServletRequestAttributes)requestAttributes).getRequest();
            value = httpServletRequest.getHeader(key);
            log.info(String.format("GrayscaleUtil >>> getHeaderData(String key) >>> %s：%s", key, value));
        }

        return value;
    }
}
