package org.example.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.NoConnectionReuseStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpClientConfig {

    @Value("${httpclient.maxTotal:200}")
    private Integer maxTotal;

    @Value("${httpclient.defaultMaxPerRoute:200}")
    private Integer defaultMaxPerRoute;

    @Value("${httpclient.connectionRequestTimeout:3000}")
    private Integer connectionRequestTimeout;

    @Value("${httpclient.connectTimeout:5000}")
    private Integer connectTimeout;

    @Value("${httpclient.socketTimeout:20000}")
    private Integer socketTimeout;

    @Bean(destroyMethod = "close")
    public PoolingHttpClientConnectionManager httpClientConnectionManager() {
        PoolingHttpClientConnectionManager httpClientConnectionManager = new PoolingHttpClientConnectionManager();
        // 最大连接数
        httpClientConnectionManager.setMaxTotal(maxTotal);
        // 并发数
        httpClientConnectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
        return httpClientConnectionManager;
    }

    @Bean(destroyMethod = "close")
    public CloseableHttpClient httpClient() {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        httpClientBuilder.setConnectionManager(httpClientConnectionManager());
        // 使用短连接(关闭连接复用)
        httpClientBuilder.setConnectionReuseStrategy(NoConnectionReuseStrategy.INSTANCE);
        httpClientBuilder.setDefaultRequestConfig(requestConfig());
        return httpClientBuilder.build();
    }

    @Bean
    public RequestConfig requestConfig() {
        RequestConfig.Builder builder = RequestConfig.custom();
        builder.setConnectionRequestTimeout(connectionRequestTimeout);
        builder.setConnectTimeout(connectTimeout);
        builder.setSocketTimeout(socketTimeout);
        return builder.build();
    }
}
