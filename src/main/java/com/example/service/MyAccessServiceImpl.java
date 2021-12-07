package com.example.service;

import com.example.service.service.MyAccessService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * TODO
 * 自定义access
 * @author chen
 * @version 1.0
 * @date 2021/11/26 15:49
 */
@Service
public class MyAccessServiceImpl implements MyAccessService {
    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        String requestURI = request.getRequestURI();

        Object principal = authentication.getPrincipal();
        //判断是不是属于UserDetails
        if (principal instanceof UserDetails){
            UserDetails userDetails = (UserDetails) principal;
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
            return authorities.contains(new SimpleGrantedAuthority(requestURI));
        }
        return false;
    }
}
