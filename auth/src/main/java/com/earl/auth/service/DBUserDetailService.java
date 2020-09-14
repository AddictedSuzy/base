package com.earl.auth.service;

import com.earl.auth.entity.Account;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface DBUserDetailService extends UserDetailsService {

    Account queryUserByUsername(String username);
}
