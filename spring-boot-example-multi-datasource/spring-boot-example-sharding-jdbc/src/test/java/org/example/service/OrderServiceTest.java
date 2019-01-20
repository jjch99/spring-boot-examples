package org.example.service;

import org.example.entity.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Test
    public void createOrder() {

        Order order = new Order();
        order.setUserId(1547956106479L);
        order.setStatus(Byte.valueOf("0"));
        order.setComments("测试");
        orderService.createOrder(order);

    }

    @Test
    public void getOrder() {

        Long userId = 1547956106479L;
        Long id = 293717223107723265L;

        // 从拆分库查询，需要传入分库&分表字段
        Order order = orderService.getOrder(userId, id);
        if (order != null) {
            log.info(JSON.toJSONString(order));
        }

        // 从汇总库直接根据ID查询
        Order order1 = orderService.getOrderFromSummary(id);
        if (order1 != null) {
            log.info(JSON.toJSONString(order1));
        }
    }

}
