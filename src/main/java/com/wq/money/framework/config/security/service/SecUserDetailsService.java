package com.wq.money.framework.config.security.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wq.money.framework.config.security.entity.SecUser;
import com.wq.money.system.bean.po.SysUser;
import com.wq.money.system.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.ServletRequestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Service
public class SecUserDetailsService implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(SecUserDetailsService.class);
    @Resource
    HttpServletRequest request;
    @Resource
    private SysUserService sysUserService;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        log.info("-------------> 查询用户");
        String username =  ServletRequestUtils.getStringParameter(request, "username", "###");
        String password =  ServletRequestUtils.getStringParameter(request, "password", "###");
        SysUser sysUser = sysUserService.getOne(new QueryWrapper<SysUser>().lambda().eq(SysUser::getUserName,username).eq(SysUser::getUserPass,password));
        if (null == sysUser){
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        }
        SecUser user = new SecUser();
        user.setUsername(sysUser.getUserName());
        user.setPassword(new BCryptPasswordEncoder().encode(sysUser.getUserPass()));
        user.setAuthorities(new ArrayList<>());
        return user;
    }
}
