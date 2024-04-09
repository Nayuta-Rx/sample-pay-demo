package com.hixtrip.sample.domain.order.repository;

import com.hixtrip.sample.domain.order.model.Order;

/**
 *
 */
public interface OrderRepository {


    Order queryUnPayOrder(Order order);


    void doSaveOrder(Order order);


    void updateOrderPayInfo(Order orderEntity);


    void changeOrderPaySuccess(String orderId);
}
