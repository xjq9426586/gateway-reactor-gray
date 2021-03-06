package com.github.xjq.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @Author: xujunqian
 * @Date: 2021/4/13 11:04
 * @Description:
 */
@FeignClient(name = "hello-provider", fallback = ProviderFallback.class)
public interface ProviderApi {
    @RequestMapping("/doSomething")
    String doSomething(@RequestParam Map<String, Object> map);
}
