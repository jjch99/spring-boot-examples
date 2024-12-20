package org.example.config;

import jakarta.servlet.MultipartConfigElement;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${servlet-path:}")
    private String servletPath = "";

    // servlet-path 不为空的时候，swagger-ui 的访问路径
    // http://host:port/context-path/servlet-path/swagger-ui.html

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Bean
    public ServletRegistrationBean dispatcherServletRegistration(DispatcherServlet dispatcherServlet,
            MultipartConfigElement multipartConfig) {
        String urlMapping = StringUtils.isEmpty(servletPath) ? "/" : (servletPath + "/*");
        ServletRegistrationBean registration = new ServletRegistrationBean(dispatcherServlet, urlMapping);
        registration.setMultipartConfig(multipartConfig);
        return registration;
    }

}
