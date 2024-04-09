package com.hixtrip.sample.domain.inventory.repository;

/**
 *
 */
public interface InventoryRepository {

    Integer getInventory(String key);

    Boolean changeInventory(String skuId, Integer sellableQuantity, Integer withholdingQuantity, Integer occupiedQuantity);
}
