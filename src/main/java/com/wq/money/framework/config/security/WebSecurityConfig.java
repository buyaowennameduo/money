package com.wq.money.framework.config.security;

import com.wq.money.framework.config.security.service.SecUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.annotation.Resource;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final Logger log = LoggerFactory.getLogger(WebSecurityConfig.class);
    @Value("${check-session-token}")
    private String checkSessionToken = "";
    @Resource
    private SecUserDetailsService secUserDetailsService;
    @Resource
    private SecBeforeSessionFilter secBeforeSessionFilter;
    @Resource
    private SecAfterSessionFilter secAfterSessionFilter;
    @Resource
    private SecSuccessHandler secSuccessHandler;
    @Resource
    private SecAuthenticationEntryPoint secAuthenticationEntryPoint;
    @Resource
    private SecAccessDeniedHandler secAccessDeniedHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.secUserDetailsService).passwordEncoder(passwordEncoder());
    }
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        log.info("-----------> sec配置 --> 权限校验方式:'{}'",checkSessionToken);
        httpSecurity.csrf().disable();
        if ("session".equals(checkSessionToken)){
            httpSecurity.formLogin().loginPage("/auth/loadPage")
                    .loginProcessingUrl("/login")
                    .successHandler(secSuccessHandler)
                    .failureHandler(null)
                    .and().logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/auth/logPage");
            httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
        } else {
            httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);
            httpSecurity.exceptionHandling().authenticationEntryPoint(secAuthenticationEntryPoint)
                    .accessDeniedHandler(secAccessDeniedHandler);
        }
        httpSecurity.authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/").permitAll()  // 登陆页面
                .antMatchers("/auth/authSecurityKey").permitAll()  // 登陆校验
                .antMatchers("/druid/**").permitAll()
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/myBlog/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/v2/**").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .anyRequest().authenticated()
                .and().headers().cacheControl();
        httpSecurity.addFilterBefore(secBeforeSessionFilter, UsernamePasswordAuthenticationFilter.class);
        httpSecurity.addFilterBefore(secAfterSessionFilter, UsernamePasswordAuthenticationFilter.class);
        httpSecurity.headers().frameOptions().disable();
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = httpSecurity.authorizeRequests();
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        String [] ignors = {"/favicon.ico","/css/**","/images/**","/js/**","/layui/**"};
        web.ignoring().antMatchers(ignors);
    }
    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration cors = new CorsConfiguration();
        cors.setAllowCredentials(true);
        cors.addAllowedOrigin("*");
        cors.addAllowedHeader("*");
        cors.addAllowedMethod("*");
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", cors);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
