package com.hixtrip.sample.infra.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hixtrip.sample.infra.db.dataobject.CommodityDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: Nayuta-Rx
 * @Description: 商品查询
 * @DataTime: 2024/4/8 17:10
 **/
@Mapper
public interface CommodityMapper extends BaseMapper<CommodityDO> {

    CommodityDO selectCommodity(String skuId);
}
