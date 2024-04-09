package com.hixtrip.sample.domain.inventory;

import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 库存领域服务
 * 库存设计，忽略仓库、库存品、计量单位等业务
 */
@Component
public class InventoryDomainService {

    @Resource
    private InventoryRepository inventoryRepository;

    private static final String ORDER_STOCK_COUNT = "order_stock_count_";

    /**
     * 获取sku当前库存
     *
     * @param skuId
     */
    public Integer getInventory(String skuId) {
        //todo 需要你在infra实现，只需要实现缓存操作, 返回的领域对象自行定义
        String key = ORDER_STOCK_COUNT + skuId;
        return inventoryRepository.getInventory(key);
    }

    /**
     * 修改库存
     *
     * @param skuId
     * @param sellableQuantity    可售库存
     * @param withholdingQuantity 预占库存
     * @param occupiedQuantity    占用库存
     * @return
     */
    public Boolean changeInventory(String skuId, Integer sellableQuantity, Integer withholdingQuantity, Integer occupiedQuantity) {
        //todo 需要你在infra实现，只需要实现缓存操作。
        return null;
    }
}
