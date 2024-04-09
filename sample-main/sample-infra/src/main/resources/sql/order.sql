DROP TABLE IF EXISTS `order`;

CREATE TABLE `order` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `sku_id` varchar(16) NOT NULL COMMENT '商品ID',
  `order_id` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单ID',
  `total_amount` decimal(8,2) unsigned DEFAULT NULL COMMENT '订单金额',
  `pay_status` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单状态；create-创建完成、pay_wait-等待支付、pay_success-支付成功、deal_done-交易完成、close-订单关单',
  `pay_url` varchar(2014) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '支付信息',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_order_id` (`order_id`),
  KEY `idx_user_id_product_id` (`user_id`,`sku_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


/*
基于userId获取hsahcode 中间通过斐波那契 也就是计算黄金分割点进行相乘 再与2的n次方减一做与计算确保数据能够均匀散列到各个库表中 （threadLocal内置map的散列原理）
订单的主键需要通过雪花（mysql、zookeep）计算唯一id 防止id重复，以及对后期数据迁移产生影响。
数据库表的某一张表数据超过400W或者单库数据超过5000W行，数据库的性能会急速下降。就需要进行分库分表

垂直分表  可以将订单和支付单进行拆分
水平分表
    基于两千万数据时
    可以使用1库8表或者2库4表来做分表 数据可以存储在虚拟机不会浪费太多服务资源

    后期数据量超过十亿 按照时间节点可以将3个月或半年以上的数据存储在数仓（HBASE）中，减小数据的查询范围
    后期虚拟机的数据也需要转移至物理机上，采用binlog+canal同步数据的方式进行扩容，并逐步把新数据写入到新库中，当两方数据库完全同步后，开始重启实例进行切换。当然这个成本和风险是会有的

主从复制 读写分离
  */
