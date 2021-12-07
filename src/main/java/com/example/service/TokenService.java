package com.example.service;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;

import java.util.Calendar;

/**
 * TODO
 *
 * @author chen
 * @version 1.0
 * @date 2021/12/7 10:28
 */
public class TokenService {
    // 令牌秘钥
    @Value("${token.secret}")
    private static String secret;

    // 令牌有效期（默认30分钟）
    @Value("${token.expireTime}")
    private int expireTime;

    protected static final long MILLIS_SECOND = 1000;

    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    private static final Long MILLIS_MINUTE_TEN = 20 * 60 * 1000L;


    /**
     * 生成Token
     * @param number
     * @param password
     * @return
     */
    public static String getToken(String number,String password){
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.MONTH,2); //默认2个星期过期



        return JWT.create()
                .withClaim("number", number)
                .withClaim("password", password)
                .withExpiresAt(instance.getTime())//指定令牌过期时间
                .sign(Algorithm.HMAC256(secret)); //签名
    }

    /**
     * 验证token
     * @param Token
     */
    public static void verify(String Token){
        JWT.require(Algorithm.HMAC256(secret)).build().verify(Token);
    }

    /**
     * 拿到token的信息
     * @param Token
     * @return
     */
    public static DecodedJWT getTokenInfo(String Token){
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(secret)).build().verify(Token);
//        Claim number = verify.getClaim("number");  拿到相应名称的参数
        return verify;
    }

}
