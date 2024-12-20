package org.example.dubbo.config;

import jakarta.servlet.Filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import brave.Tracing;
import brave.http.HttpTracing;
import brave.jakarta.servlet.TracingFilter;
import brave.spring.webmvc.SpanCustomizingAsyncHandlerInterceptor;

@Configuration
@Import(SpanCustomizingAsyncHandlerInterceptor.class)
public class WebMvcConfig implements WebMvcConfigurer {

    // @Autowired
    // private SpanCustomizingAsyncHandlerInterceptor serverInterceptor;

    /**
     * 追踪过滤器
     */
    @Bean
    public Filter tracingFilter() {
        Tracing tracing = Tracing.newBuilder()
                .localServiceName("my-service")
                .build();
        return TracingFilter.create(HttpTracing.create(tracing));
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
    // @Override
    // public void addInterceptors(InterceptorRegistry registry) {
    //     registry.addInterceptor(serverInterceptor);
    // }

}
