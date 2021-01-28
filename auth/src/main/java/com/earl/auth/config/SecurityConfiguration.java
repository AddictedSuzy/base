package com.earl.auth.config;

import com.earl.auth.filter.UsernamePasswordFilter;
import com.earl.auth.filter.VerificationCodeFilter;
import com.earl.auth.handler.DefaultAuthenticationEntryPoint;
import com.earl.auth.handler.DefaultLogoutSuccessHandler;
import com.earl.auth.handler.LoginFailureHandler;
import com.earl.auth.handler.LoginSuccessHandler;
import com.earl.auth.service.DBUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
//import org.springframework.session.FindByIndexNameSessionRepository;
//import org.springframework.session.security.SpringSessionBackedSessionRegistry;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    DefaultLogoutSuccessHandler defaultLogoutSuccessHandler;

    @Autowired
    LoginSuccessHandler loginSuccessHandler;

    @Autowired
    LoginFailureHandler loginFailureHandler;

    @Autowired
    DBUserDetailService dbUserDetailService;

    @Autowired
    DefaultAuthenticationEntryPoint defaultAuthenticationEntryPoint;

    @Resource
    VerificationCodeFilter verificationCodeFilter;

    @Autowired
    private DataSource dataSource;

//    @Resource
//    FindByIndexNameSessionRepository sessionRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin()
                .loginPage("/login.html")   // 登录表单地址
                .loginProcessingUrl("/authentication/form") // 处理表单请求的url，配置了这个地址，
                                                            // spring security会拦截该地址并对其进行用户名和密码校验
                                                            // 即使自定义了该路径的controller，在没有登陆的情况下，不会路由到该controller
//                 // 当我们自定义一个AuthenticationFilter的时候，下面的配置在这里配将不会生效，需要在新的bean里面配置
//                .defaultSuccessUrl("/welcome")  // 默认的登陆成功跳转页面
//                .failureForwardUrl("/fail") // 认证失败的页面
//                .usernameParameter("username1") // 修改默认的接收用户名的参数
//                .passwordParameter("password1") // 修改默认的接收密码的参数
                .failureHandler(loginFailureHandler)   // 登陆失败
                .successHandler(loginSuccessHandler)   // 登陆成功
                .permitAll()
                .and()
                .rememberMe()
                .tokenValiditySeconds(60 * 60)
                .userDetailsService(dbUserDetailService)
                .tokenRepository(persistentTokenRepository())
                .key("earl")
                .and()
                .logout()   // 开启注销
                .logoutSuccessHandler(defaultLogoutSuccessHandler) // 注销成功
//                .logoutUrl("/logout")   // logout地址 默认是/logout
                .logoutSuccessUrl("/login.html")   // logout成功跳转页面
                .invalidateHttpSession(true)
                .and()
                .authorizeRequests()    // 对请求进行授权
                .antMatchers("/login")
                .permitAll()
                // ant匹配模式，使用*匹配0或任意多的字符，使用**匹配任意的路径
                .antMatchers(
                        "/webjars/**",
                        "/resources/**",
                        "/swagger-ui.html",
                        "/swagger-resources/**",
                        "/v2/api-docs") // swagger路径放行
                .permitAll()
                .antMatchers("/welcome").fullyAuthenticated()
                .antMatchers("/admin/**").hasRole("admin")
                .antMatchers("/user/**").hasAnyAuthority("user")
                .anyRequest().authenticated()   // 任何请求都需要授权
                .and()
                .csrf().disable() // 关闭跨站请求伪造防护
                .exceptionHandling()
                .and()
                .sessionManagement()
                .maximumSessions(1)
                .maxSessionsPreventsLogin(true); // 当session达到最大值时，是否保留登录
//                .sessionRegistry(sessionRegistry());    // 最大存在的session数量，即最多同时登陆的账户数
//                .authenticationEntryPoint(defaultAuthenticationEntryPoint);  // 匿名用户默认拦截


        // 在用户名密码验证之前加上图形验证码验证
//        http.addFilterBefore(verificationCodeFilter, UsernamePasswordAuthenticationFilter.class);
        // 替换原有的用户名密码验证，改成json传值方式并验证验证码
//        http.addFilterAt(buildUsernamePasswordFilter(), UsernamePasswordAuthenticationFilter.class);
    }

//    @Bean
//    SpringSessionBackedSessionRegistry sessionRegistry() {
//        return new SpringSessionBackedSessionRegistry(sessionRepository);
//    }

//    @Bean
//    UsernamePasswordFilter buildUsernamePasswordFilter() throws Exception {
//        UsernamePasswordFilter filter = new UsernamePasswordFilter();
//        filter.setAuthenticationManager(authenticationManagerBean());
//        filter.setAuthenticationFailureHandler(loginFailureHandler);
//        filter.setAuthenticationSuccessHandler(loginSuccessHandler);
//        filter.setFilterProcessesUrl("/login");
//        return filter;
//    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        PasswordEncoder encoder = new BCryptPasswordEncoder();
//        // 添加内存用户
//        auth.inMemoryAuthentication()
//                .passwordEncoder(encoder)
//                .withUser("root")
//                .password(encoder.encode("root"))
//                .roles("ROOT", "USER")
//                .and()
//                .withUser("user")
//                .password(encoder.encode("user"))
//                .roles("USER");
//    }

    @Bean
    public PasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        //自动创建相关的token表
//        jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }
}
