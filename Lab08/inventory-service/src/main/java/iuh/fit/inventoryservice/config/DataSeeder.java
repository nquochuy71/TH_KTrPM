/*
 * @ (#) v.java     1.0    05-May-26
 *
 * Copyright (c) 2026 IUH. All rights reserved.
 */

package iuh.fit.inventoryservice.config;

/*
 * @description:
 * @author: Nguyen Quoc Huy
 * @date:05-May-26
 * @version: 1.0
 */
import iuh.fit.inventoryservice.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void run(String... args) {
        try {
            // Chỉ nạp dữ liệu nếu Redis chưa có sản phẩm nào (tránh ghi đè mỗi lần restart)
            if (redisTemplate.opsForHash().size("products") > 0) {
                System.out.println("Products already exist in Redis. Skipping seed.");
                return;
            }
        } catch (org.springframework.data.redis.RedisSystemException e) {
            System.out.println("Invalid data type for key 'products'. Deleting key to seed fresh data...");
            redisTemplate.delete("products");
        }

        // 20 sản phẩm chân thật
        Map<String, Product> products = new LinkedHashMap<>();
        products.put("P001", new Product("P001", "iPhone 15 Pro Max 256GB", 29990000.0, 50));
        products.put("P002", new Product("P002", "Samsung Galaxy S24 Ultra", 27990000.0, 70));
        products.put("P003", new Product("P003", "MacBook Air M2 2023", 25990000.0, 30));
        products.put("P004", new Product("P004", "Dell XPS 15 9530", 35000000.0, 20));
        products.put("P005", new Product("P005", "iPad Pro M2 12.9 inch", 24990000.0, 40));
        products.put("P006", new Product("P006", "Apple Watch Series 9", 10500000.0, 80));
        products.put("P007", new Product("P007", "Sony WH-1000XM5", 6990000.0, 100));
        products.put("P008", new Product("P008", "AirPods Pro 2", 5990000.0, 120));
        products.put("P009", new Product("P009", "Samsung Galaxy Tab S9+", 18990000.0, 35));
        products.put("P010", new Product("P010", "Logitech MX Master 3S", 2200000.0, 150));
        products.put("P011", new Product("P011", "Keychron K8 Pro", 2800000.0, 90));
        products.put("P012", new Product("P012", "LG UltraFine 27UN880-B", 12990000.0, 25));
        products.put("P013", new Product("P013", "Samsung T7 Shield 2TB", 3400000.0, 200));
        products.put("P014", new Product("P014", "Anker 737 Power Bank", 2300000.0, 180));
        products.put("P015", new Product("P015", "Cáp USB-C Anker Powerline+", 350000.0, 500));
        products.put("P016", new Product("P016", "Ốp lưng iPhone 15 Pro", 400000.0, 300));
        products.put("P017", new Product("P017", "Miếng dán màn hình iPad", 200000.0, 400));
        products.put("P018", new Product("P018", "Bút Apple Pencil 2", 3500000.0, 70));
        products.put("P019", new Product("P019", "Loa JBL Flip 6", 2700000.0, 110));
        products.put("P020", new Product("P020", "Camera hành trình Xiaomi 70mai A810", 1900000.0, 60));

        redisTemplate.opsForHash().putAll("products", products);
        System.out.println("Seeded 20 realistic products into Redis.");
    }
}