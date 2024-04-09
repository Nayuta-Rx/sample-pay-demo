package com.hixtrip.sample.infra.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hixtrip.sample.infra.db.dataobject.OrderDO;

/**
 * @Author: Nayuta-Rx
 * @Description: 订单查询
 * @DataTime: 2024/4/9 11:22
 **/
public interface OrderMapper extends BaseMapper<OrderDO> {

    int insert(OrderDO orderDO);

    OrderDO queryUnPayOrder(OrderDO orderDO);

    void updateOrderPayInfo(OrderDO orderDO);

    void changeOrderPaySuccess(OrderDO orderDO);
}
