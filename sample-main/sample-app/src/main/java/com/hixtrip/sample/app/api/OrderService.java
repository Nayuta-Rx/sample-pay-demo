package com.hixtrip.sample.app.api;

import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.domain.pay.model.CommandPay;

import java.util.Map;

/**
 * 订单的service层
 */
public interface OrderService {


    CommandPayDTO createOrder(CommandOderCreateDTO shopCartEntity) throws Exception;

    String verifySig(Map<String, String> params) throws Exception;
}
