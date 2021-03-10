package com.wq.money.framework.config.security;

import com.wq.money.framework.config.security.service.SecUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class SecBeforeSessionFilter extends OncePerRequestFilter {
    private final Logger log = LoggerFactory.getLogger(SecBeforeSessionFilter.class);
    @Value("${check-session-token}")
    private String checkSessionToken = "";
    @Resource
    private SecUserDetailsService secUserDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info(">>>>>>>>>>:SEC前置过滤器");
        log.info("请求地址: '{}'", request.getRequestURL());

//      Authentication authResult = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
//      SecurityContextHolder.getContext().setAuthentication(authResult);


        filterChain.doFilter(request,response);
    }
}
