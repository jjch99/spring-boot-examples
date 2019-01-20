package org.example.service;

import java.util.List;

import org.example.dao.OrderMapper;
import org.example.entity.Order;
import org.example.entity.OrderExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    public void createOrder(Order order) {
        orderMapper.insertSelective(order);
    }

    public Order getOrder(Long userId, Long id) {
        OrderExample example = new OrderExample();
        example.createCriteria().andUserIdEqualTo(userId).andIdEqualTo(id);
        List<Order> list = orderMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        } else {
            return null;
        }
    }

    public Order getOrderFromSummary(Long id) {
        return orderMapper.selectByPrimaryKeyFromSummary(id);
    }

}
