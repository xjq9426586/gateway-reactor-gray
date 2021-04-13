
package com.github.xjq.ribbongray.rule;

import com.github.xjq.ribbongray.common.GrayscaleUtils;
import com.google.common.base.Optional;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ZoneAvoidanceRule;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.util.CollectionUtils;

import java.util.Iterator;
import java.util.List;

public class MetadataCanaryRuleHandler extends ZoneAvoidanceRule {
    private static final Logger log = LoggerFactory.getLogger(MetadataCanaryRuleHandler.class);
    @Autowired
    private DiscoveryClient discoveryClient;
    @Value("${grayscale.defaultVersion:#{null}}")
    public String defaultVersion;

    public MetadataCanaryRuleHandler() {
    }

    public Server choose(Object key) {
        String version = GrayscaleUtils.getGVersion();
        ILoadBalancer lb = this.getLoadBalancer();
        List<Server> upList = lb.getReachableServers();
        Server server1 = this.getServer(version, upList);
        if (null != server1) {
            return server1;
        } else {
            Server server2 = this.getServer(this.defaultVersion, upList);
            if (null != server2) {
                return server2;
            } else {
                log.info(String.format("GrayscaleLoadBalancerRule >>> choose(String key, List<Server> upList,List<Server> allList) >>> 头部版本号与已部署待路由服务版本号不一致"));
                Optional<Server> server_opt = this.getPredicate().chooseRoundRobinAfterFiltering(lb.getAllServers(), key);
                return server_opt.isPresent() ? (Server)server_opt.get() : null;
            }
        }
    }

    private Server getServer(String version, List<Server> upList) {
        if (!StringUtils.isEmpty(version) && !CollectionUtils.isEmpty(upList) && upList.size() >= 1) {
            String[] split = ((Server)upList.get(0)).getMetaInfo().getAppName().split("@");
            String appName = split[split.length - 1];
            List<ServiceInstance> sis = this.discoveryClient.getInstances(appName);
            Iterator var6 = upList.iterator();

            while(var6.hasNext()) {
                Server server = (Server)var6.next();
                Iterator var8 = sis.iterator();

                while(var8.hasNext()) {
                    ServiceInstance si = (ServiceInstance)var8.next();
                    if (server.getHost().equals(si.getHost()) && server.getPort() == si.getPort() && version.equals(si.getMetadata().get("version"))) {
                        return server;
                    }
                }
            }
        }

        return null;
    }
}
