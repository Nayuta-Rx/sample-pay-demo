package com.hixtrip.sample.domain.pay.service;

import com.alipay.api.AlipayApiException;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.domain.pay.model.PayNotify;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

/**
 * @Author: Nayuta-Rx
 * @Description: 支付策略抽象类
 * @DataTime: 2024/4/9 15:44
 **/
public interface PayPloy {

    CommandPay doPrepayOrder(String userId, String skuId, String orderId, BigDecimal price) throws AlipayApiException;

    // 加载支付回调信息
    PayNotify loadPayInfo(Map<String, String> params) throws IOException;

}
