package com.example.pojp.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * (UserPermissions)实体类
 *
 * @author makejava
 * @since 2021-11-24 17:56:12
 */
@TableName("user_permissions")
public class UserPermissions implements Serializable {
    private static final long serialVersionUID = -68766492842274006L;
    
    private Integer id;
    
    private String userid;
    /**
    * 用户权限
    */
    private String userPermissions;
    
    private String updatetime;
    
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

    public String getUserPermissions() {
        return userPermissions;
    }

    public void setUserPermissions(String userPermissions) {
        this.userPermissions = userPermissions;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

}