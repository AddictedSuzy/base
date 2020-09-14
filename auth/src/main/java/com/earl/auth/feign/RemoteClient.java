package com.earl.auth.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "consumer")
public interface RemoteClient {

    @RequestMapping("/remote")
    String remote();
}
