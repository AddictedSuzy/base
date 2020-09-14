package com.earl.auth.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @see UserDetailsServiceAutoConfiguration 中提供了对用户信息的自动配置
 * 对于自动生成的密码（Using generated security password）
 * 可以通过提供 AuthenticationManager，AuthenticationProvider 或者 UserDetailsService 的 bean 来覆盖默认的自动配置信息
 */
//@Service
@Slf4j
public class DefaultUserDetailService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("登录的用户： " + username);
        // 该User类是SpringSecurity自带实现UserDetails接口的一个用户类
        // 使用加密工具对密码进行加密
        // 其第三个属性为用户权限,后续说明
        return new User(username,passwordEncoder.encode("123456")
                , AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}
