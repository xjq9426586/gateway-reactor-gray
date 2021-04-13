package com.github.xjq.helloprovider;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * @Author: xujunqian
 * @Date: 2021/4/13 11:04
 * @Description:
 */
@FeignClient(name = "hello-provider")
public interface ProviderApi {
    @PostMapping("/doSomething")
    String doSomething(@RequestBody Map<String, Object> map);
}
