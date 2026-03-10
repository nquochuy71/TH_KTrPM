/*
 * @ (#) f.java     1.0    10-Mar-26
 *
 * Copyright (c) 2026 IUH. All rights reserved.
 */

package iuh.fit.factory;

import iuh.fit.product.Storage;

/*
 * @description:
 * @author: Nguyen Quoc Huy
 * @date:10-Mar-26
 * @version: 1.0
 */
public abstract class StorageFactory {
    public abstract Storage createStorage();

    public void saveData(String fileName) {
        Storage storage = createStorage();
        storage.uploadFile(fileName);
    }
}