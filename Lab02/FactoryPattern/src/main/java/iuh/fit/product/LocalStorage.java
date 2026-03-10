/*
 * @ (#) d.java     1.0    10-Mar-26
 *
 * Copyright (c) 2026 IUH. All rights reserved.
 */

package iuh.fit.product;

/*
 * @description:
 * @author: Nguyen Quoc Huy
 * @date:10-Mar-26
 * @version: 1.0
 */
public class LocalStorage implements Storage {
    @Override
    public void uploadFile(String fileName) {
        System.out.println("Lưu trữ [" + fileName + "] vào ổ cứng cục bộ.");
    }
}