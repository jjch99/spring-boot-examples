package org.example.es.config;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EsClientConfig {

    @Value("${elasticsearch.cluster-nodes}")
    private String clusterNodes;

    @Bean
    public RestClientBuilder restClientBuilder() {
        String[] nodes = StringUtils.split(clusterNodes, ',');
        List<HttpHost> list = new ArrayList<>(nodes.length);
        for (String node : nodes) {
            list.add(HttpHost.create(node));
        }
        return RestClient.builder(list.toArray(new HttpHost[list.size()]));
    }

    @Bean(destroyMethod = "close")
    public RestClient restClient() {
        return restClientBuilder().build();
    }

    @Bean(destroyMethod = "close")
    public RestHighLevelClient highLevelClient() {
        return new RestHighLevelClient(restClientBuilder());
    }

}
