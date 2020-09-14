package com.earl.auth.controlller;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Properties;
import java.util.concurrent.Executor;

@RestController
@RefreshScope
public class NacosClientController {

    @Autowired
    ConfigurableApplicationContext configurableApplicationContext;

    // 使用@Value 获取nacos的值不能实现动态获取 在配合@RefreshScope使用才能实现动态
    @Value("${user.age}")
    private String config;
    @Value("${config.name}")
    private String configName;

    @RequestMapping("/getConfig")
    public String directGetConfig(){
        return configName;
    }

    @RequestMapping("/dynamicConfig")
    public String dynamicConfig(){
        return configurableApplicationContext.getEnvironment().getProperty("user.age");
    }

    @RequestMapping("/config")
    public String getConfig() throws NacosException {
        // nacos配置信息
        String dataId = "simple.yaml";
        String group = "DEFAULT_GROUP";
        String serverAddr = "localhost:8848";

        // 获取配置
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR, serverAddr);
        // 默认为public的namespace
//        properties.put("namespace", namespace);
        ConfigService configService = NacosFactory.createConfigService(properties);
        String config = configService.getConfig(dataId, group, 1000);
        // 配置文件修改时 获取被修改信息
        // 客户端会开启一个定时任务 去调用nacos服务器来判断配置是否更新，如果更新了则会回调这个方法
        configService.addListener(dataId, group, new Listener() {
            @Override
            public Executor getExecutor() {
                return null;
            }

            @Override
            public void receiveConfigInfo(String configInfo) {
                System.out.println("config exchanged: " + configInfo);
            }
        });

        System.out.println(config);
        return config;
    }
}
