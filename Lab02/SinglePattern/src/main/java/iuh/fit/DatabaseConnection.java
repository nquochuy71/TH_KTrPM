/*
 * @ (#) f.java     1.0    10-Mar-26
 *
 * Copyright (c) 2026 IUH. All rights reserved.
 */

package iuh.fit;

/*
 * @description:
 * @author: Nguyen Quoc Huy
 * @date:10-Mar-26
 * @version: 1.0
 */
public class DatabaseConnection {
    private static volatile DatabaseConnection instance;

    DatabaseConnection() {
        System.out.println("Khởi tạo kết nối đến Database...");
    }
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                if (instance == null) {
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }
    public void connect() {
        System.out.println("Đã kết nối thành công!");
    }
}