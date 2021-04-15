package com.github.xjq.controller;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/hello")
public class HelloController {

    @Value("${spring.application.name}")
    private String serviceName;

    @Value("${server.port}")
    private int servicePort;

    @Value("${spring.cloud.nacos.discovery.server-addr}")
    private String nacosServerAddr;

    @Autowired
    private ProviderApi providerApi;

    @Autowired
    private RestTemplate restTemplate;


    @GetMapping(value="/info")
    public Map<String,String> info() throws NacosException {
        restTemplate.getForEntity("http://hello-provider/doSomething", String.class);
        providerApi.doSomething(new HashMap<>());
        NamingService namingService = NacosFactory.createNamingService(nacosServerAddr);
        Map<String,String> metaData = new LinkedHashMap<>();
        List<Instance> instances =  namingService.selectInstances(serviceName,true);
        if(!CollectionUtils.isEmpty(instances)){
            for (Instance instance : instances) {
                if(servicePort == instance.getPort()){
                   metaData.put("ip",instance.getIp());
                   metaData.put("port",String.valueOf(instance.getPort()));
                   metaData.putAll(instance.getMetadata());
                }
            }
        }
       return metaData;


    }



}
