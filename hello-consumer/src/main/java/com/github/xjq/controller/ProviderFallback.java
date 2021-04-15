package com.github.xjq.controller;

import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author: xujunqian
 * @Date: 2021/4/15 13:55
 * @Description:
 */
@Component
public class ProviderFallback implements ProviderApi{
    @Override
    public String doSomething(Map<String, Object> map) {
        return "fall";
    }
}
