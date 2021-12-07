package com.example;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class SecurityTestApplicationTests {

    @Test
    void contextLoads() {
        final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        final String encode = bCryptPasswordEncoder.encode("234");
        System.out.println(encode);
        boolean matches = bCryptPasswordEncoder.matches("234", encode);
        System.out.println(matches);
    }

}
