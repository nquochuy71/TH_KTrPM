/*
 * @ (#) f.java     1.0    05-May-26
 *
 * Copyright (c) 2026 IUH. All rights reserved.
 */

package iuh.fit.inventoryservice.controller;

import iuh.fit.inventoryservice.entity.Product;
import iuh.fit.inventoryservice.service.InventoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stock")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/{productId}")
    public ResponseEntity<?> getStock(@PathVariable String productId) {
        Product product = inventoryService.getStock(productId);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product.getStock());
    }

    @PostMapping("/{productId}/decrease")
    public ResponseEntity<?> decreaseStock(@PathVariable String productId,
                                           @RequestParam int quantity) {
        boolean success = inventoryService.decreaseStock(productId, quantity);
        if (success) {
            return ResponseEntity.ok("Stock decreased successfully");
        } else {
            return ResponseEntity.badRequest().body("Not enough stock or product not found");
        }
    }
}