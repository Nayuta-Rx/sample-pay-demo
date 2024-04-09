package com.hixtrip.sample.domain.order.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单表
 */
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@SuperBuilder(toBuilder = true)
public class Order {

    /**
     * 订单号
     */
    private String id;
    /**
     * 购买人
     */
    private String userId;
    /**
     * SkuId
     */
    private String skuId;
    /**
     * 购买数量
     */
    private Integer amount;
    /**
     * 购买金额
     */
    private BigDecimal money;
    /**
     * 购买时间
     */
    private LocalDateTime payTime;
    /**
     * 支付状态
     */
    private OrderStatusVO payStatus;
    /**
     * 支付信息
     */
    private String payUrl;
    /**
     * 删除标志（0代表存在 1代表删除）
     */
    private Long delFlag;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 修改人
     */
    private String updateBy;
    /**
     * 修改时间
     */
    private LocalDateTime updateTime;


    public boolean checkState() {
        // 如果订单刚创建 则没有交易单 需要创建新的交易单
        if (null != payStatus && OrderStatusVO.CREATE.equals(payStatus)) {
            return true;
        }
        // 其他状态下不需要创建
        return false;
    }

}
