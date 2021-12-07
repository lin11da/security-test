package com.example.config.security.error;

import com.alibaba.fastjson.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 * 自定义认证处理异常
 * @author chen
 * @version 1.0
 * @date 2021/11/25 10:17
 */
public class JsonAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException authentication) throws IOException, ServletException {
        // JSON 信息
        Map<String, Object> map = new HashMap<String, Object>(3);
        map.put("code", 401);
        map.put("message", "尚未登陆");
        map.put("success",false);
        map.put("data", authentication.getMessage());

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
        //do nothing
        }finally {
            if (out != null){
                out.close();
            }
        }


    }
}
