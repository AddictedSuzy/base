package com.earl.auth.controlller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleController {

    @RequestMapping("/admin/get")
    public String adminGet(){
        return "admin";
    }

    @RequestMapping("/user/get")
    public String userGet(){
        return "user";
    }
}
