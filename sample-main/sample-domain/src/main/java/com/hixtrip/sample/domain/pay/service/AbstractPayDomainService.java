package com.hixtrip.sample.domain.pay.service;

import com.alipay.api.AlipayApiException;
import com.google.common.eventbus.EventBus;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.domain.pay.model.PayNotify;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

/**
 * @Author: Nayuta-Rx
 * @Description: TODO
 * @DataTime: 2024/4/9 16:18
 **/
@Slf4j
public abstract class AbstractPayDomainService extends PayPloyFactory implements IPayDomainService{

    private static final String CHANNEL = "channel";

    @Resource
    private EventBus eventBus;

    /**
     * 设计思路  策略模式+简单工厂+模板方法
     * 策略加工厂方便后期多渠道扩展
     * 模板方法定义生成订单和验签的标准
     * @param channel
     * @param userId
     * @param skuId
     * @param orderId
     * @param price
     * @return
     */
    @Override
    public CommandPay doPrepayOrder(String channel, String userId, String skuId, String orderId, BigDecimal price){
        try {
            // 通过渠道选择支付策略 生成支付单
            PayPloy payPloy = getPayPloy(channel);
            CommandPay commandPay = payPloy.doPrepayOrder(userId, skuId, orderId, price);

            this.updateOrderStatus(commandPay);
            return commandPay;
        } catch (Exception e) {
            throw new RuntimeException("生成支付单失败");
        }
    }

    @Override
    public String verifySig(Map<String, String> params) throws Exception {
        try {
            PayPloy payPloy = getPayPloy(CHANNEL);

            // 加载支付回调信息
            PayNotify payNotify = payPloy.loadPayInfo(params);
            if (payNotify.isPaySuccess()) {
                CommandPay commandPay = payNotify.getServiceMode();
                // 修改订单信息
                this.payRecord(commandPay);

                // 异步通知后续动作（MQ） 如 发货流程 自动消息提醒
                eventBus.post(commandPay);
            }

            return "success";
        } catch (IOException e) {
            return "false";
        }
    }

    protected abstract void payRecord(CommandPay commandPay);

    protected abstract void updateOrderStatus(CommandPay commandPay);
}
