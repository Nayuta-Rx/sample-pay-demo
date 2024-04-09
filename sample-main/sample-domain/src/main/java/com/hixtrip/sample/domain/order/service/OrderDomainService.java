package com.hixtrip.sample.domain.order.service;

import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 订单领域服务
 * todo 只需要实现创建订单即可
 */
@Component
public class OrderDomainService {

    @Resource
    private OrderRepository repository;


    /**
     * todo 需要实现
     * 创建待付款订单
     */
    public void createOrder(Order order) {
        //需要你在infra实现, 自行定义出入参
        Order unPayOrder = repository.queryUnPayOrder(order);
        if (null != unPayOrder) {
            return;
        }

        repository.doSaveOrder(order);
    }

    /**
     * todo 需要实现
     * 待付款订单支付成功
     */
    public void orderPaySuccess(String orderId) {
        //需要你在infra实现, 自行定义出入参
        repository.changeOrderPaySuccess(orderId);
    }

    /**
     * todo 需要实现
     * 待付款订单支付失败
     */
    public void orderPayFail(Order order) {
        //需要你在infra实现, 自行定义出入参
        repository.updateOrderPayInfo(order);
    }
}
