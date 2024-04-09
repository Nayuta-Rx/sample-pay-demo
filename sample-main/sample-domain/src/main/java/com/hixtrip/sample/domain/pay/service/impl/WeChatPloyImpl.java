package com.hixtrip.sample.domain.pay.service.impl;

import com.alipay.api.AlipayApiException;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.domain.pay.model.PayNotify;
import com.hixtrip.sample.domain.pay.service.PayPloy;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

/**
 * @Author: Nayuta-Rx
 * @Description: 微型支付策略
 * @DataTime: 2024/4/9 16:10
 **/
public class WeChatPloyImpl implements PayPloy {

    @Override
    public CommandPay doPrepayOrder(String userId, String skuId, String orderId, BigDecimal price) throws AlipayApiException {
        // 微信生成支付单
        return null;
    }

    @Override
    public PayNotify loadPayInfo(Map<String, String> params) throws IOException {
        // 微信解析回调参数 统一返回PayNotify
        return null;
    }
}
