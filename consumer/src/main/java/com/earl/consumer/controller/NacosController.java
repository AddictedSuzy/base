package com.earl.consumer.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RefreshScope
public class NacosController {

//    @Value("${user.age}")
    private String config;

    @RequestMapping("/getConfig")
    public String directGetConfig(){
        return config;
    }

    @RequestMapping("/remote")
    public String remote(){
        return "remote";
    }

    @RequestMapping("/errors")
    public int error(){
        return 1/0;
    }
}
