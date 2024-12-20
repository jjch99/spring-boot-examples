package org.example.config;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.http.client.config.RequestConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Autowired
    private RequestConfig requestConfig;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(httpComponentsClientHttpRequestFactory());
    }

    @Bean
    public ClientHttpRequestFactory httpComponentsClientHttpRequestFactory() {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpclient);
        factory.setConnectionRequestTimeout(requestConfig.getConnectionRequestTimeout());
        factory.setConnectTimeout(requestConfig.getConnectTimeout());
        return factory;
    }

    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(5000);
        factory.setReadTimeout(20000);
        return factory;
    }

}
