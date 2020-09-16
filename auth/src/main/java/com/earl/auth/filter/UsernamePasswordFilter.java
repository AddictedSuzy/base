package com.earl.auth.filter;

import com.earl.auth.entity.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

public class UsernamePasswordFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }
        String verifyCode = (String) request.getSession().getAttribute("verifyCode");
        if (request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE) || request.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE)) {
            ObjectMapper mapper = new ObjectMapper();
            LoginRequest loginRequest;
            try (InputStream jsons = request.getInputStream()){
                loginRequest = mapper.readValue(jsons, LoginRequest.class);
            } catch (IOException e) {
                throw new AuthenticationServiceException("获取请求参数失败");
            }
            String code = loginRequest.getCode();
            checkCode(response, code, verifyCode);
            String username = loginRequest.getUsername();
            String password = loginRequest.getPassword();

            if (username == null) {
                username = "";
            }
            if (password == null) {
                password = "";
            }

            username = username.trim();
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                    username, password);
            setDetails(request, authRequest);

            return this.getAuthenticationManager().authenticate(authRequest);
        } else {
            checkCode(response, request.getParameter("code"), verifyCode);
            return super.attemptAuthentication(request, response);
        }
    }

    public void checkCode(HttpServletResponse resp, String code, String verifyCode) {
        if (code == null || verifyCode == null || "".equals(code) || !verifyCode.toLowerCase().equals(code.toLowerCase())) {
            //验证码不正确
//            throw new AuthenticationServiceException("验证码不正确");
        }
    }


}
