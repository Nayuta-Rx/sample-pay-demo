package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.commodity.model.Commodity;
import com.hixtrip.sample.domain.commodity.repository.CommodityRepository;
import com.hixtrip.sample.infra.db.convertor.CommodityDOConvertor;
import com.hixtrip.sample.infra.db.dataobject.CommodityDO;
import com.hixtrip.sample.infra.db.mapper.CommodityMapper;

import javax.annotation.Resource;

/**
 * @Author: Nayuta-Rx
 * @Description: 商品仓储服务实现类
 * @DataTime: 2024/4/8 17:07
 **/
public class CommodityRepositoryImpl implements CommodityRepository {

    @Resource
    private CommodityMapper commodityMapper;

    @Override
    public Commodity queryCommodityBySkuId(String skuId) {
        CommodityDO commodityDO = commodityMapper.selectCommodity(skuId);
        return CommodityDOConvertor.INSTANCE.doToDomain(commodityDO);
    }
}
