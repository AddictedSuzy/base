package com.earl.auth.controlller;

import com.earl.auth.feign.RemoteClient;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Iterator;

@RestController
public class RouteController {

    @Autowired
    RemoteClient remoteClient;

    @ApiOperation("index")
    @RequestMapping("/authentication/form")
    public String index(HttpServletRequest httpServletRequest){
        for(Cookie cookie: httpServletRequest.getCookies()){
            System.out.println(cookie);
            System.out.println(cookie.getName());
            System.out.println(cookie.getValue());
        }
        HttpSession session = httpServletRequest.getSession();
        System.out.println(session.getAttributeNames());
        Iterator iterator = session.getAttributeNames().asIterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }
        System.out.println(httpServletRequest);
        return "index";
    }

    @RequestMapping("/welcome")
    public String login(){
        System.out.println("processing login...");
        return "welcome";
    }

    @RequestMapping("/remote")
    public String remote(){
        return remoteClient.remote();
    }

}
