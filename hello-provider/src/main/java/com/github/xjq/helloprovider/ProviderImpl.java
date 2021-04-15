package com.github.xjq.helloprovider;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author: xujunqian
 * @Date: 2021/4/13 11:05
 * @Description:
 */
@RestController
public class ProviderImpl implements ProviderApi{
    @Override
    public String doSomething(@RequestParam Map<String, Object> map) {
        return "sss";
    }
}
