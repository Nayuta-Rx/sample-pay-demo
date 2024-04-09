package com.hixtrip.sample.domain.pay.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.hixtrip.sample.domain.order.model.OrderStatusVO;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.domain.pay.model.PayNotify;
import com.hixtrip.sample.domain.pay.service.PayPloy;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

/**
 * @Author: Nayuta-Rx
 * @Description: 支付宝支付策略
 * @DataTime: 2024/4/9 15:58
 **/
public class AliPayPloyImpl implements PayPloy {

    @Value("${alipay.notify_url}")
    private String notifyUrl;
    @Value("${alipay.return_url}")
    private String returnUrl;
    @Resource
    private AlipayClient alipayClient;


    @Override
    public CommandPay doPrepayOrder(String userId, String skuId, String orderId, BigDecimal price)throws AlipayApiException {
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setNotifyUrl(notifyUrl);
        request.setReturnUrl(returnUrl);

        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", orderId);
        bizContent.put("total_amount", price.toString());
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");
        request.setBizContent(bizContent.toString());

        String form = alipayClient.pageExecute(request).getBody();

        CommandPay commandPay = new CommandPay();
        commandPay.setOrderId(orderId);
        commandPay.setPayUrl(form);
        commandPay.setPayStatus(OrderStatusVO.PAY_WAIT);

        return commandPay;
    }

    @Override
    public PayNotify loadPayInfo(Map<String, String> params) throws IOException {
        return new PayNotify(params);
    }
}
