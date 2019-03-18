package com.dafy.config.domain;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by de on 2017/6/19.
 */
public class User {
    private long id;
    @Column(unique = true)
    @NotNull(message = "用户名不能为空")
    @NotBlank(message = "用户名不能为空")
    private String username;
    private String password;
    @Column(unique = true)
    @NotNull(message = "手机号码不能为空")
    @NotBlank(message = "手机号码不能为空")
    @Pattern(regexp = "^(((13[0-9]{1})|150|151|152|153|155|156|157|158|159|182|183|185|186|187|188|189|173|176|177)+\\d{8})$", message = "手机号码不合法")
    private String mobile;
    @Column(unique = true)
    @NotNull(message = "邮箱不能为空")
    @NotBlank(message = "邮箱不能为空")
    private String email;
    @NotNull(message = "名称不能为空")
    private String nickname;
    private boolean enable;
    private Date createTime;
    private Set<Role> roles = new HashSet<Role>();

    private Set<GroupInfo> groups = new HashSet<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Set<GroupInfo> getGroups() {
        return groups;
    }

    public void setGroups(Set<GroupInfo> groups) {
        this.groups = groups;
    }
}
