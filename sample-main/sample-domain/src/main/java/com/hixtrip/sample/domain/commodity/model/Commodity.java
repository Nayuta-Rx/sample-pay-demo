package com.hixtrip.sample.domain.commodity.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

/**
 * @Author: Nayuta-Rx
 * @Description: 商品实体对象
 * @DataTime: 2024/4/8 16:50
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@SuperBuilder(toBuilder = true)
public class Commodity {

    /** 商品id */
    private String skuId;
    /** 商品名称 */
    private String commodityName;
    /** 商品描述 */
    private String commodityDesc;
    /** 商品库存 */
    private Integer stock;
    /** 商品价格 */
    private BigDecimal price;

}
