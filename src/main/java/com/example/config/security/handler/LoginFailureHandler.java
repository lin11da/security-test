package com.example.config.security.handler;

import com.alibaba.fastjson.JSONObject;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 * 登陆失败处理器实现
 * @author chen
 * @version 1.0
 * @date 2021/11/25 10:42
 */
public class LoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException exception) throws IOException, ServletException {
        // JSON 信息
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
        map.put("data", exception.getMessage());
        map.put("success",false);
        JSONObject json = new JSONObject(map);

        // 将 JSON 信息写入响应
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;

        try {
            out = httpServletResponse.getWriter();
            out.append(json.toString());
            out.flush();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (out != null){
                out.close();
            }
        }

    }
}
