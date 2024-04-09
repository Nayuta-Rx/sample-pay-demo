package com.hixtrip.sample.domain.pay.service;

import com.alipay.api.AlipayApiException;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.domain.pay.model.PayNotify;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

/**
 * @Author: Nayuta-Rx
 * @Description: 支付领域服务
 * @DataTime: 2024/4/9 16:18
 **/
public interface IPayDomainService {

    CommandPay doPrepayOrder(String channel, String userId, String skuId, String orderId, BigDecimal price) throws AlipayApiException, Exception;

    String verifySig(Map<String, String> params) throws Exception;
}
