package com.dafy.config.security;

import com.dafy.config.domain.Role;
import com.dafy.config.domain.User;
import com.dafy.config.service.UserRoleService;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.Set;

/**
 * Created by de on 2017/6/19.
 */
public class SecurityUserDetailService implements UserDetailsService {

    private static final Logger LOG = LoggerFactory.getLogger(SecurityUserDetailService.class);

    @Autowired
    private UserRoleService userRoleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (LOG.isDebugEnabled()){
            LOG.debug(" > LoginUser:"+username +"");
        }
        if (username == null || "".equals(username)){
            throw new UsernameNotFoundException("["+username+"] 用户不存在");
        }
        Collection<GrantedAuthority> auths = Lists.newArrayList();
        User user = this.userRoleService.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("["+username+"] 用户不存在");
        }
        if (!user.isEnable()) {
            throw new LockedException("["+username+"] 已经被冻结");
        }

        Set<Role> roles = user.getRoles();
        for (Role role : roles) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(
                    role.getRolename());
            auths.add(authority);
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), user.isEnable(), true, true, true,
                auths);
    }
}
