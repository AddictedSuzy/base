package com.earl.auth.controlller;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
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

    @RequestMapping("/update/user")
    public String userUpdate(User user, Authentication authentication){
        SecurityContextHolder.getContext()
                .setAuthentication(new UsernamePasswordAuthenticationToken(user, authentication.getPrincipal(), authentication.getAuthorities()));
        return "ok";
    }
}
