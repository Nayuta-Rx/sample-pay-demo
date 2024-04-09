package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.model.OrderStatusVO;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.infra.db.convertor.OrderDOConvertor;
import com.hixtrip.sample.infra.db.dataobject.OrderDO;
import com.hixtrip.sample.infra.db.mapper.OrderMapper;

import javax.annotation.Resource;

/**
 * @Author: Nayuta-Rx
 * @Description: 订单仓储服务实现类
 * @DataTime: 2024/4/9 2:44
 **/
public class OrderRepositoryImpl implements OrderRepository {

    @Resource
    private OrderMapper orderMapper;

    @Override
    public Order queryUnPayOrder(Order order) {
        OrderDO orderDO = new OrderDO();
        orderDO.setSkuId(order.getSkuId());
        orderDO.setUserId(order.getUserId());

        OrderDO unPayOrderDO = orderMapper.queryUnPayOrder(orderDO);
        if (null == unPayOrderDO) return null;

        Order payOrder = OrderDOConvertor.INSTANCE.doToDomain(orderDO);
        payOrder.setPayStatus(OrderStatusVO.valueOf(unPayOrderDO.getPayStatus()));
        return payOrder;
    }

    @Override
    public void doSaveOrder(Order order) {
        OrderDO orderDO = new OrderDO();
        orderDO.setId(order.getId());
        orderDO.setUserId(order.getUserId());
        orderDO.setSkuId(order.getSkuId());
        orderDO.setAmount(order.getAmount());
        orderDO.setMoney(order.getMoney());
        orderDO.setPayTime(order.getPayTime());
        orderDO.setPayStatus(order.getPayStatus().getCode());
        orderDO.setPayUrl(order.getPayUrl());
        orderDO.setDelFlag(order.getDelFlag());
        orderDO.setCreateBy(order.getCreateBy());
        orderDO.setCreateTime(order.getCreateTime());
        orderDO.setUpdateBy(order.getUpdateBy());
        orderDO.setUpdateTime(order.getUpdateTime());

        orderMapper.insert(orderDO);
    }

    @Override
    public void updateOrderPayInfo(Order order) {
        OrderDO orderDO = new OrderDO();
        orderDO.setUserId(order.getUserId());
        orderDO.setId(order.getId());
        orderDO.setPayUrl(order.getPayUrl());
        orderDO.setPayStatus(order.getPayStatus().getCode());
        orderMapper.updateOrderPayInfo(orderDO);
    }

    @Override
    public void changeOrderPaySuccess(String orderId) {
        OrderDO orderDO = new OrderDO();
        orderDO.setId(orderId);
        orderDO.setPayStatus(OrderStatusVO.PAY_SUCCESS.getCode());
        orderMapper.changeOrderPaySuccess(orderDO);
    }
}
