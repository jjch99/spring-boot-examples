package org.example.config;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class OkHttpClientConfig {

    @Bean
    public OkHttpClient okHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(5, TimeUnit.SECONDS);
        builder.writeTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.retryOnConnectionFailure(true);
        builder.followRedirects(true);

        // builder.connectionPool(new ConnectionPool());

        OkHttpClient client = builder.build();
        client.dispatcher().setMaxRequests(200);
        client.dispatcher().setMaxRequestsPerHost(200);
        return builder.build();
    }

}
