package com.github.xjq.helloprovider;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @Author: xujunqian
 * @Date: 2021/4/13 11:04
 * @Description:
 */
@FeignClient(name = "hello-provider")
public interface ProviderApi {
    @RequestMapping("/doSomething")
    String doSomething(@RequestParam Map<String, Object> map);
}
