package com.hixtrip.sample.domain.commodity.repository;

import com.hixtrip.sample.domain.commodity.model.Commodity;

/**
 * @Author: Nayuta-Rx
 * @Description: 商品仓储服务
 * @DataTime: 2024/4/8 16:59
 **/
public interface CommodityRepository {

    Commodity queryCommodityBySkuId(String skuId);
}
