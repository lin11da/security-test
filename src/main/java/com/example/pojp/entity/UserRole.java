package com.example.pojp.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * (UserRole)实体类
 *
 * @author makejava
 * @since 2021-11-24 17:56:12
 */
@TableName("user_role")
public class UserRole implements Serializable {
    private static final long serialVersionUID = 633896441175547407L;
    
    private Integer id;
    /**
    * 用户id
    */
    private String userid;
    /**
    * 用户权限
    */
    private String userRole;
    
    private String updatime;
    
    private String createtime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUpdatime() {
        return updatime;
    }

    public void setUpdatime(String updatime) {
        this.updatime = updatime;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

}