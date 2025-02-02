package com.hixtrip.sample.domain.pay.service;

import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 支付领域服务
 * todo 不需要具体实现, 直接调用即可
 */
@Component
public class PayDomainService extends AbstractPayDomainService{

    /*
    @Resource
    private PayRepository repository;
    */


    /**
     * 记录支付回调结果
     * 【高级要求】至少有一个功能点能体现充血模型的使用。
     */
    @Override
    protected void payRecord(CommandPay commandPay) {
        //无需实现，直接调用即可
        //repository.changeOrderPaySuccess(commandPay.getOrderId());
    }

    @Override
    protected void updateOrderStatus(CommandPay commandPay) {
        //repository.updateOrderPayInfo(commandPay);
    }
}
