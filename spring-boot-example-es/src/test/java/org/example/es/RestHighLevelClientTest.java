package org.example.es;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.percentiles.Percentiles;
import org.elasticsearch.search.aggregations.metrics.percentiles.PercentilesAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RestHighLevelClientTest {

    private RestHighLevelClient highLevelClient;

    @Before
    public void init() {
        RestClientBuilder restClientBuilder = RestClient.builder(new HttpHost("eshost", 8992, "httpclient"));
        highLevelClient = new RestHighLevelClient(restClientBuilder);
    }

    @After
    public void destory() {
        try {
            if (highLevelClient != null) {
                highLevelClient.close();
            }
        } catch (IOException e) {
        }
    }

    @Test
    public void termQuery() {
        try {
            SearchRequest searchRequest = new SearchRequest("segment_duration");
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS)); // 超时时间
            sourceBuilder.size(100); // 记录条数
            sourceBuilder.query(QueryBuilders.termQuery("ai", "4")); // 精确匹配
            sourceBuilder.sort("ddt", SortOrder.DESC); // 排序
            searchRequest.source(sourceBuilder);

            SearchResponse response = highLevelClient.search(searchRequest);
            log.info("TotalHits: {}", response.getHits().getTotalHits());

            Arrays.stream(response.getHits().getHits())
                    .forEach(i -> {
                        log.info(i.getSourceAsString());
                    });
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Test
    public void percentilesAggs() {
        try {
            SearchRequest searchRequest = new SearchRequest("segment_duration");
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
            sourceBuilder.size(0);
            sourceBuilder.query(QueryBuilders.termQuery("ai", "4"));

            PercentilesAggregationBuilder aggregationBuilder =
                    AggregationBuilders.percentiles("ddt_percentiles").field("ddt");
            aggregationBuilder.percentiles(50, 75, 90, 95, 99); // 需要统计的百分比
            sourceBuilder.aggregation(aggregationBuilder);

            searchRequest.source(sourceBuilder);

            SearchResponse response = highLevelClient.search(searchRequest);
            log.info("TotalHits: {}", response.getHits().getTotalHits());

            Aggregation aggregation = response.getAggregations().get("ddt_percentiles");
            if (aggregation instanceof Percentiles) {
                Percentiles percentiles = (Percentiles) aggregation;
                percentiles.forEach(p -> {
                    log.info("{} -> {}", p.getPercent(), p.getValue());
                });
            }

        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

}
