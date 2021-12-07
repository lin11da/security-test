package com.example.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mapper.User_;
import com.example.pojp.entity.UserO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * TODO
 *
 * @author chen
 * @version 1.0
 * @date 2021/11/24 17:48
 */
@Service
public class UserServiceImpl implements UserDetailsService {


    @Autowired
    User_ user_;

    @Autowired
    private PasswordEncoder pw;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<UserO> queryWrapper = new QueryWrapper<>();
        //1.根据username查询数据库
        UserO user = user_.selectOne(queryWrapper.eq("number", username));
        System.out.println(username+" 尝试登录");

        if (user==null){
            throw new UsernameNotFoundException("用户名或者密码错误");
        }
        //2.根据查询的对象比较密码
        String encode = pw.encode(user.getPassword());

        //3.返回用户对象
        return new User(user.getNumber(),encode,
                //权限
                AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_abc,/toMain"));
    }


}
