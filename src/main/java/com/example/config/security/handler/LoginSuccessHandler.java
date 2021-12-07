package com.example.config.security.handler;

import com.alibaba.fastjson.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
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
 * 登陆成功处理器实现  其实是用不到的  都在config中配置用Lambda配置好了，或者直接传入一个类方法
 * @author chen
 * @version 1.0
 * @date 2021/11/25 10:39
 */
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        // JSON 信息
        Map<String, Object> map = new HashMap<String, Object>(3);
        map.put("code", 200);
        map.put("msg", "登陆成功");
        map.put("success",true);
        map.put("data", authentication.getPrincipal().toString());

//        System.out.println(authentication.getDetails());
//        System.out.println(authentication.getCredentials());
//        System.out.println(authentication.getPrincipal());

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
