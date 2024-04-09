package com.hixtrip.sample.app.service;

import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.domain.commodity.CommodityDomainService;
import com.hixtrip.sample.domain.commodity.model.Commodity;
import com.hixtrip.sample.domain.inventory.InventoryDomainService;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.model.OrderStatusVO;
import com.hixtrip.sample.domain.order.service.OrderDomainService;
import com.hixtrip.sample.domain.pay.service.PayDomainService;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;

/**
 * app层负责处理request请求，调用领域服务
 */
@Component
public class OrderServiceImpl implements OrderService {

    @Resource
    private CommodityDomainService commodityDomainService;
    @Resource
    private InventoryDomainService inventoryDomainService;
    @Resource
    private OrderDomainService orderDomainService;
    @Resource
    private PayDomainService payDomainService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommandPayDTO createOrder(CommandOderCreateDTO dto) throws Exception {
        try {
            // 查询商品详情
            Commodity commodity = commodityDomainService.getSku(dto.getSkuId());

            // 预占库存
            Integer inventory = inventoryDomainService.getInventory(dto.getSkuId());
            if (inventory <= 0) {
                throw new RuntimeException("库存不足无法下单");
            }

            int occupied = commodity.getStock() - inventory;
            Boolean changeInventory = inventoryDomainService.changeInventory(dto.getSkuId(), inventory, dto.getAmount(), occupied);
            if (!changeInventory) {
                throw new RuntimeException("修改库存失败");
            }

            // 生成订单
            Order order = new Order().toBuilder()
                    .userId(dto.getUserId())
                    .skuId(dto.getSkuId())
                    .amount(dto.getAmount())
                    .payStatus(OrderStatusVO.CREATE)
                    .money(commodity.getPrice().multiply(new BigDecimal(dto.getAmount())))
                    .id(RandomStringUtils.randomNumeric(16))
                    .build();

            orderDomainService.createOrder(order);

            CommandPay commandPay = null;
            // 生成支付单
            if (order.checkState()) {
                // 不同渠道的重复支付需要删除原来的交易单重新创建新的交易单
                commandPay = payDomainService.doPrepayOrder(dto.getChannel(), dto.getUserId(), dto.getSkuId(), order.getId(), order.getMoney());
            }

            CommandPayDTO commandPayDTO = new CommandPayDTO();
            commandPayDTO.setOrderId(commandPay.getOrderId());
            commandPayDTO.setPayStatus(commandPay.getPayStatus().getCode());
            return commandPayDTO;
        } catch (Exception e) {
            throw new RuntimeException("创建订单失败");
        }
    }

    @Override
    public String verifySig(Map<String, String> params) throws Exception {
        if (null != params) {
            return "false";
        }
        return payDomainService.verifySig(params);
    }
}
