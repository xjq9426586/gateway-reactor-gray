package com.github.xjq.ribbongray;

import com.netflix.loadbalancer.ZoneAvoidanceRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class RibbonGrayApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(RibbonGrayApplication.class, args);
        //System.out.println(run.getBean(ZoneAvoidanceRule.class));
    }

}
