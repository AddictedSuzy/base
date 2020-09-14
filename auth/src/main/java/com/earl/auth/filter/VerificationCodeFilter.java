package com.earl.auth.filter;

import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class VerificationCodeFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String code = httpServletRequest.getParameter("code");
        if(code == null || code.equals("123")){
            httpServletResponse.setContentType("application/json;charset=utf-8");
            httpServletResponse.getWriter().write("验证码错误");
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
