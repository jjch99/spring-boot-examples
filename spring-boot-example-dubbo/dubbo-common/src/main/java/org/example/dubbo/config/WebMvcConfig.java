package org.example.dubbo.config;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import brave.http.HttpTracing;
import brave.servlet.TracingFilter;
import brave.spring.webmvc.SpanCustomizingAsyncHandlerInterceptor;

@Configuration
@Import(SpanCustomizingAsyncHandlerInterceptor.class)
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private HttpTracing httpTracing;

    @Autowired
    private SpanCustomizingAsyncHandlerInterceptor serverInterceptor;

    /**
     * 追踪过滤器
     */
    @Bean
    public Filter tracingFilter() {
        return TracingFilter.create(httpTracing);
    }

    /**
     * 注册 追踪过滤器
     */
    @Bean
    public FilterRegistrationBean tracingFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(tracingFilter());
        registration.addUrlPatterns("/*");
        registration.setName("zipkin-tracing-filter");
        registration.setOrder(1);
        return registration;
    }

    /**
     * adds tracing to the application-defined web controller
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(serverInterceptor);
    }

}
