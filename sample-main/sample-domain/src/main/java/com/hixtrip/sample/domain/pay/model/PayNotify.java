package com.hixtrip.sample.domain.pay.model;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.hixtrip.sample.domain.order.model.OrderStatusVO;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;

/**
 * @Author: Nayuta-Rx
 * @Description: 支付回调
 * @DataTime: 2024/4/9 16:31
 **/
public class PayNotify {

    private final Map<String, String> params;
    private boolean checkSignature;
    @Value("${alipay.alipay_public_key}")
    private String alipayPublicKey;

    public PayNotify(Map<String, String> params) {
        this.params = params;

        try {
            String tradeNo = params.get("out_trade_no");
            String gmtPayment = params.get("gmt_payment");
            String alipayTradeNo = params.get("trade_no");

            String sign = params.get("sign");
            String content = AlipaySignature.getSignCheckContentV1(params);

            checkSignature = AlipaySignature.rsa256CheckContent(content, sign, alipayPublicKey, "UTF-8"); // 验证签名
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

    }

    // 判断是否支付成功
    public boolean isPaySuccess() {
        return checkSignature;
    }

    // 验证数据的合法性
    public boolean verifyData() {
        return true;
    }

    // 获得需要的信息
    public CommandPay getServiceMode() {
        return new CommandPay().builder()
                .orderId(params.get("orderId"))
                .payStatus(OrderStatusVO.valueOf(params.get("status")))
                .userId(params.get("userId"))
                .build();
    }
}
