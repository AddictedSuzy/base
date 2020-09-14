package com.earl.auth.service.impl;

import com.earl.auth.entity.Account;
import com.earl.auth.service.DBUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DBUserDetailServiceImpl implements DBUserDetailService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = queryUserByUsername(username);
        UserDetails user = null;
        if(account != null) {
            // TODO: 2020/9/1 添加用户权限 以及 账户状态信息
            user = new User(account.getUsername(), passwordEncoder.encode(account.getPassword()), account.getAuthorities());
        }

        System.out.println("user::" + user);
        return user;
    }

    @Override
    public Account queryUserByUsername(String username) {
        if(username.equals("root")){
            return new Account(username, "123456", AuthorityUtils.commaSeparatedStringToAuthorityList("user"));
        }
        // 这里需要添加权限
        return new Account(username, "123456", AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}
