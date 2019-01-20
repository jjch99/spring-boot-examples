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

    /**
     * 创建订单
     * <p>
     * 根据用户ID分库，根据订单号分表，所以需要设置用户ID，订单ID可以由ID生成器生成。
     * 
     * @param order
     */
    public void createOrder(Order order) {
        orderMapper.insertSelective(order);
    }

    /**
     * 查询
     * <p>
     * 根据用户ID分库，根据订单号分表，所以查询时至少要传入这两个参数。
     * 
     * @param userId 用户ID
     * @param id
     * @return
     */
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

    /**
     * 从汇总库根据ID查询
     * 
     * @param id
     * @return
     */
    public Order getOrderFromSummary(Long id) {
        return orderMapper.selectByPrimaryKeyFromSummary(id);
    }

}
