package com.github.xjq.config;

import com.alibaba.cloud.nacos.client.NacosPropertySourceBuilder;
import com.alibaba.cloud.nacos.client.NacosPropertySourceLocator;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.bootstrap.config.PropertySourceBootstrapConfiguration;
import org.springframework.cloud.bootstrap.config.PropertySourceLocator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.List;

@Component
@Slf4j
public class NacosConfig {
    @Autowired
    private PropertySourceBootstrapConfiguration propertySourceBootstrapConfiguration;

    @Value("${spring.cloud.nacos.config.namespace}")
    private String namespace;

    @Value("${spring.cloud.nacos.config.group}")
    private String group;
    /**
     * 从nacos中读取某个配置文件的内容
     * @param group 对应到nacos中的group组
     * @param dataId 对应到nacos中的dataId
     * @param timeout
     * @return
     */
    public String getData(String group,String dataId,long timeout){
        try {
            Field propertySourceLocators = PropertySourceBootstrapConfiguration.class.getDeclaredField("propertySourceLocators");
            propertySourceLocators.setAccessible(true);
            List<PropertySourceLocator> list = (List<PropertySourceLocator>)propertySourceLocators.get(propertySourceBootstrapConfiguration);
            NacosPropertySourceLocator nacosLocator = null;
            for(PropertySourceLocator locator : list){
                if(locator instanceof NacosPropertySourceLocator){
                    nacosLocator = (NacosPropertySourceLocator)locator;
                }
            }
            if(nacosLocator == null){
                return null;
            }
            Field nacosPropertySourceBuilderField = NacosPropertySourceLocator.class.getDeclaredField("nacosPropertySourceBuilder");
            nacosPropertySourceBuilderField.setAccessible(true);
            NacosPropertySourceBuilder nacosPropertySourceBuilder = (NacosPropertySourceBuilder)nacosPropertySourceBuilderField.get(nacosLocator);
            String config = nacosPropertySourceBuilder.getConfigService().getConfig(dataId, group, timeout);
            return config;
        } catch (NoSuchFieldException | IllegalAccessException | NacosException e) {
            log.error("nacos工具异常",e);
        }
        return null;
    }

    public String getData(String dataId,long timeout){
        return this.getData(group, dataId, timeout);
    }
}