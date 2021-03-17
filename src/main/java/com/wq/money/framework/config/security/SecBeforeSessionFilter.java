package com.wq.money.framework.config.security;
import com.wq.money.framework.config.security.service.SecUserDetailsService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SecBeforeSessionFilter extends OncePerRequestFilter {
    private final Logger log = LoggerFactory.getLogger(SecBeforeSessionFilter.class);
    @Value("${jwt.header}")
    private String jwtHeader = "";
    private static final String JWT_PARAMETER = "xxxx";
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Resource(name = "secUserDetailService")
    private UserDetailsService secUserDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.info("请求地址: '{}'", request.getRequestURL());
        String requestHeader = request.getHeader(jwtHeader);
        if (StringUtils.isBlank(requestHeader)){
            requestHeader = request.getParameter(JWT_PARAMETER);
        }
        if (!StringUtils.isBlank(requestHeader)){
            // 校验 token 是否合法
            String username = jwtTokenUtil.getUserLoginFromToken(requestHeader);
            UserDetails userDetails = secUserDetailsService.loadUserByUsername(username);
            if (jwtTokenUtil.validateToken(requestHeader, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                log.info("authorized user '{}', setting security context", username);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request,response);
    }
}
