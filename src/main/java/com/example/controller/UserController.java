package com.example.controller;

import com.example.pojp.entity.UserO;
import com.example.service.UserServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @author chen
 * @version 1.0
 * @date 2021/11/24 16:50
 */
@RestController
public class UserController {

    @Secured({ "ROLE_abc", "ROLE_ADMIN" })  //根据角色判断  必须以  ROLE_开头  区分大小写
                                            // ROLE_abc, ROLE_ADMIN任意一种权限就可以访问
    @PostMapping("/toMain")
    public String timian(){

        return "timain";
    }
}
