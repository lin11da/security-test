package com.example.mapper.mybatis;

import com.example.pojp.security.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * TODO
 *
 * @author chen
 * @version 1.0
 * @date 2021/12/7 12:31
 */
@Repository
@Mapper
public interface User {
    UserDTO selectLoginUserbyuserid(String number);
}
