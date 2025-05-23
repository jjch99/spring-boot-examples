package org.example.mybatis.controller;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Spring Boot Testing
 * <p>
 * https://docs.spring.io/spring-boot/3.5/reference/testing/index.html
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MerchantControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testInfo() {
        String body = this.restTemplate.getForObject("/merchant/info?id=1", String.class);
        Assertions.assertThat(body).isNull();
    }

    @Test
    public void testList() {
        List list = this.restTemplate.getForObject("/merchant/list", List.class);
        Assertions.assertThat(list).isEmpty();
    }

}
