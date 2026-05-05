/*
 * @ (#) f.java     1.0    05-May-26
 *
 * Copyright (c) 2026 IUH. All rights reserved.
 */

package iuh.fit.inventoryservice.service;

/*
 * @description:
 * @author: Nguyen Quoc Huy
 * @date:05-May-26
 * @version: 1.0
 */
import iuh.fit.inventoryservice.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class InventoryService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public Product getStock(String productId) {
        return (Product) redisTemplate.opsForHash().get("products", productId);
    }

    /**
     * Giảm stock nguyên tử bằng Lua script
     * @return true nếu thành công, false nếu không đủ hàng
     */
    public boolean decreaseStock(String productId, int quantity) {
        String script =
                "local stock = redis.call('hget', KEYS[1], ARGV[1]); " +
                        "if stock and tonumber(stock) >= tonumber(ARGV[2]) then " +
                        "  redis.call('hincrby', KEYS[1], ARGV[1], -ARGV[2]); " +
                        "  return 1; " +
                        "else " +
                        "  return 0; " +
                        "end";

        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptText(script);
        redisScript.setResultType(Long.class);

        Long result = redisTemplate.execute(
                redisScript,
                Collections.singletonList("products"),
                productId, String.valueOf(quantity)
        );
        return result == 1;
    }
}