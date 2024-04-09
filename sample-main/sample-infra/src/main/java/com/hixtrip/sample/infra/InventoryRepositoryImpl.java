package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * infra层是domain定义的接口具体的实现
 */
@Component
public class InventoryRepositoryImpl implements InventoryRepository {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Integer getInventory(String key) {
        return Integer.parseInt((String) redisTemplate.opsForValue().get(key));
    }

    @Override
    public Boolean changeInventory(String key, Integer sellableQuantity, Integer withholdingQuantity, Integer occupiedQuantity) {
        try {
            int stockUsedCount = (int) decr(key, withholdingQuantity);
            if (stockUsedCount < 0) {
                incr(key, 1);
                throw new RuntimeException("跟新库存失败");
            }
            // 并发处理 库存扣减
            /**
             * 方案 redis ＋ 分布式锁 （滑块锁）， 通过increment(key, delta)方法让数据扣减动作发生在redis（单线程无并发问题）
             * 库存扣减成功的用户拿到滑块锁（Redis.setNx分布式锁），细粒度最细是用户个体， 不会有争抢锁的行为（因为每一个用户有都有锁）
             *「给incr的结果加一层分段锁，在不影响性能的情况下，会可靠。以往压测tps 2500 ~ 5000 计算结果 1 + 100万，最终结果不是100万，不过可能因环境导致」
             * 性能比较好 不会有超卖
             *
             * 因为没用使用事物
             * 业务要求强一致的或并发量非常高的情况下 可以使用lua脚本来确保原子性 不过性能会有所下降
             */
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 递增
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     * @return
     */
    private long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递减
     * @param key   键
     * @param delta 要减少几(小于0)
     * @return
     */
    private long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }

}
