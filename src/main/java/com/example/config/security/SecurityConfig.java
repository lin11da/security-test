package com.example.config.security;

import com.example.config.security.custom.MyAccessDeniedHandler;
import com.example.config.security.filter.JsonAuthenticationFilter;
import com.example.service.UserServiceImpl;
import com.example.untlis.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @author chen
 * @version 1.0
 * @date 2021/11/24 17:47
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    DataSource dataSource;

    @Autowired
    PersistentTokenRepository persistentTokenRepository;


    // 用于密码加解密，如果你需要自定义用户则需要提供一个 PasswordEncoder
    // 在身份校验时 Spring Security 会使用它来对密码进行解密
    // 在获取用户密码时，密码必须是加密状态的，所以在用户注册时也需要使用 PasswordEncoder 进行加密
    // 否则 PasswordEncoder 向一个未加密的密码进行解密，会导致密码错误
    @Bean
    public PasswordEncoder pw(){
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 将 userService 设置到 AuthenticationManagerBuilder 即可
        auth.userDetailsService(userService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 所有请求都需要身份验证，关闭 CSRF
        http.authorizeRequests()
                //放行
                .antMatchers("/login").permitAll()
                //什么路径需要权限  严格区分大小写
//                .antMatchers("/toMain").hasAuthority("admin")
                //什么路径需要权限  严格区分大小写
//                .antMatchers("/toMain").hasAnyRole("user")
                //所有的请求都必须被验证（登录）
//                .anyRequest().authenticated()
                //自定义access代替  .anyRequest().authenticated()  根据当前访问的url 判断用户里有没有权限，没有的话返回为false 不能访问
                // 可以做路径访问控制，也就是谁可以访问哪个路径
                .anyRequest().access("@myAccessServiceImpl.hasPermission(request,authentication)")
                .and()
                .csrf().disable();


//        http.sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);  //禁用session


        //403自定义异常处理
        http.exceptionHandling().accessDeniedHandler(new MyAccessDeniedHandler());


        //退出登录
        http.logout()
                //自定义退出登录url
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login");

        //记住我  rememberme功能
        http.rememberMe()
                //自定义参数
                //自定义失效时间
//                .tokenValiditySeconds()
                //自定义登录逻辑
                .userDetailsService(userService)
                //指定存储的位置
                .tokenRepository(persistentTokenRepository);
        //前端的input 的 name必须要是  remember-me

        // 配置认证异常处理
        // 因为 AuthenticationEntryPoint 是函数式接口（只有一个方法的接口），
        // 所以我们可以使用 Lambda 表达式进行实现，之前的类可以删除了。
        // 如果不使用 Lambda 表达式，就直接传入一个实现类的实例既可。
        http.exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
            // JSON 信息
            Map<String, Object> map = new HashMap<String, Object>(3);
            map.put("code", 401);
            map.put("message", "未登陆");
            map.put("success",false);
            map.put("data", authException.getMessage());

            // 将 JSON 信息写入响应
            ResponseUtil.send(response, map);
        });




        // 创建 AuthenticationFilter 实例
        UsernamePasswordAuthenticationFilter authenticationFilter =
                new JsonAuthenticationFilter();

        // 添加登陆 成功/失败 处理器  //
        // 因为这两个处理器也是函数式接口，所以这里同样使用 Lambda 表达式
        // 如果不使用 Lambda 表达式，就直接传入一个实现类的实例既可。
        authenticationFilter.setAuthenticationSuccessHandler(new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                Map<String, Object> map = new HashMap<String, Object>(3);
                map.put("code", 200);
                map.put("msg", "登陆成功");
                map.put("success",true);
                map.put("data", authentication);

                // 将 JSON 信息写入响应
                ResponseUtil.send(httpServletResponse, map);
            }
        });



        authenticationFilter.setAuthenticationFailureHandler((request, response, exception) -> {
            Map<String, Object> map = new HashMap<String, Object>(3);

            map.put("code", 401);
            if (exception instanceof LockedException){
                map.put("msg", "账户被锁定");
            }else if (exception instanceof CredentialsExpiredException){
                map.put("msg", "密码过期");
            }else if (exception instanceof AccountExpiredException){
                map.put("msg", "账户过期");
            }else if (exception instanceof DisabledException){
                map.put("msg", "账户被禁用");
            }else if (exception instanceof BadCredentialsException){
                map.put("msg", "用户名或者密码输入错误");
            }
            map.put("data", "");
            map.put("success",false);
            // 将 JSON 信息写入响应
            ResponseUtil.send(response, map);
        });





        // 配置 AuthenticationManager
        authenticationFilter.setAuthenticationManager(authenticationManagerBean());
        // 替换过滤器
        http.addFilterAt(authenticationFilter, UsernamePasswordAuthenticationFilter .class);

    }



    //remember-me 失效时间  2周
    @Bean
    public PersistentTokenRepository tokenRepository(){
//            基于内存
//        InMemoryTokenRepositoryImpl inMemoryTokenRepository = new InMemoryTokenRepositoryImpl();
//
//        inMemoryTokenRepository.createNewToken();
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        //设置数据源
        tokenRepository.setDataSource(dataSource);
        //启动时是否创建表，第一次要，之后注释掉
//        tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }

}
