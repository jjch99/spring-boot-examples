package org.example.dubbo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import brave.spring.web.TracingClientHttpRequestInterceptor;

@Configuration
@Import(TracingClientHttpRequestInterceptor.class)
public class RestClientConfig {

    @Autowired
    private TracingClientHttpRequestInterceptor clientInterceptor;

    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(5000);
        factory.setReadTimeout(20000);

        RestTemplate restTemplate = new RestTemplate(factory);
        // 增加 zipkin brave 拦截器
        restTemplate.getInterceptors().add(clientInterceptor);
        return restTemplate;
    }

}
