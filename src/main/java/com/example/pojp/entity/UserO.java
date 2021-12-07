package com.example.pojp.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * (User)实体类
 *
 * @author makejava
 * @since 2021-11-24 17:56:11
 */
@TableName("user")
public class UserO {
    private static final long serialVersionUID = -31000208614239952L;
    
    private Integer id;
    
    private String userid;
    
    private String number;
    
    private String password;
    /**
    * 盐
    */
    private String salt;
    
    private String name;
    
    private Date updatetime;
    
    private Date createtime;

    /**
     * 角色信息
     */
    //表示表中并没有相应的字段与之对应。
    @TableField(exist = false)
    private List<String> roles;


    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

}