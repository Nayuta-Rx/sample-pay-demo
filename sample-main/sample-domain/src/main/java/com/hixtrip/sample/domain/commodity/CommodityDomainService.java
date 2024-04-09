package com.hixtrip.sample.domain.commodity;

import com.hixtrip.sample.domain.commodity.model.Commodity;
import com.hixtrip.sample.domain.commodity.repository.CommodityRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * 商品领域服务
 */
@Component
public class CommodityDomainService {

    @Resource
    private CommodityRepository repository;

    public BigDecimal getSkuPrice(String skuId) {
        Commodity commodity = repository.queryCommodityBySkuId(skuId);
        if (commodity.getPrice() == null) {
            return BigDecimal.ZERO;
        }
        return commodity.getPrice();
    }

    public Commodity getSku(String skuId) {
        return repository.queryCommodityBySkuId(skuId);
    }

}
