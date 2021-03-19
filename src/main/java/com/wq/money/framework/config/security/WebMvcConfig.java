package com.wq.money.framework.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Autowired
    private StaticPagePathFinder staticPagePathFinder;


    @Override
    protected void addViewControllers(ViewControllerRegistry registry) {
        try{
            for(StaticPagePathFinder.PagePaths pagePaths :staticPagePathFinder.findPath()){
                String urlPath = pagePaths.getUrlPath();
                String filePath = pagePaths.getFilePath();
                registry.addViewController(urlPath).setViewName(filePath);
            }
        }catch(Exception e){

        }
        registry.addViewController("/").setViewName("/auth/loadPage.html");
        registry.addViewController("/main").setViewName("/main.html");
    }
    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:/META-INF/resources/", "classpath:/resources/",
            "classpath:/static/", "classpath:/public/"
    };

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        if (!registry.hasMappingForPattern("/webjars/**")) {

            registry.addResourceHandler("/webjars/**").addResourceLocations(
                    "classpath:/META-INF/resources/webjars/");
        }
        if (!registry.hasMappingForPattern("/**")) {

            registry.addResourceHandler("/**").addResourceLocations(
                    CLASSPATH_RESOURCE_LOCATIONS);
        }

    }
}
