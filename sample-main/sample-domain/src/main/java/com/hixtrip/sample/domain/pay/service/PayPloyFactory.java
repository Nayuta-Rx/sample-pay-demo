package com.hixtrip.sample.domain.pay.service;

import com.hixtrip.sample.domain.pay.service.impl.AliPayPloyImpl;
import com.hixtrip.sample.domain.pay.service.impl.WeChatPloyImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Nayuta-Rx
 * @Description: 支付策略工厂
 * @DataTime: 2024/4/9 16:11
 **/
public class PayPloyFactory {

    private static final Map<String, PayPloy> payMap = new HashMap<>();

    static {
        payMap.put("alipay", new AliPayPloyImpl());
        payMap.put("wechat", new WeChatPloyImpl());
    }

    public static PayPloy getPayPloy(String key) {
        if (null == key) {
            return null;
        }
        return payMap.get(key);
    }
}
