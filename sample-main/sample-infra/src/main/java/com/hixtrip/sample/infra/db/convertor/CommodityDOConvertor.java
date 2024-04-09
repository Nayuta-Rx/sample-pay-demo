package com.hixtrip.sample.infra.db.convertor;

import com.hixtrip.sample.domain.commodity.model.Commodity;
import com.hixtrip.sample.domain.sample.model.Sample;
import com.hixtrip.sample.infra.db.dataobject.CommodityDO;
import com.hixtrip.sample.infra.db.dataobject.SampleDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Author: Nayuta-Rx
 * @Description: 商品领域转换器
 * @DataTime: 2024/4/9 2:40
 **/
@Mapper
public interface CommodityDOConvertor {
    CommodityDOConvertor INSTANCE = Mappers.getMapper(CommodityDOConvertor.class);

    Commodity doToDomain(CommodityDO commodityDO);
}
