package com.earl.auth.handler;

import com.earl.auth.service.DBUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Collection;

@Component
public class LoginValidateAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    DBUserDetailService dbUserDetailService;
//    DefaultUserDetailService defaultUserDetailService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("authenticate");
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        if(StringUtils.isEmpty(username)){
            throw new UsernameNotFoundException("username不可以为空");
        }
        if(StringUtils.isEmpty(password)){
            throw new BadCredentialsException("password不可以为空");
        }
        //获取用户信息
        UserDetails user = dbUserDetailService.loadUserByUsername(username);
        //获取用户权限信息
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        return new UsernamePasswordAuthenticationToken(user, password, authorities);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
