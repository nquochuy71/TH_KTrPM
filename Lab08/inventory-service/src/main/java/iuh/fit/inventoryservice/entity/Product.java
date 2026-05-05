/*
 * @ (#) g.java     1.0    05-May-26
 *
 * Copyright (c) 2026 IUH. All rights reserved.
 */

package iuh.fit.inventoryservice.entity;

/*
 * @description:
 * @author: Nguyen Quoc Huy
 * @date:05-May-26
 * @version: 1.0
 */
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable {
    private static final long serialVersionUID = 1L; // THÊM DÒNG NÀY

    private String id;
    private String name;
    private double price;
    private int stock;
}