# 1. Sử dụng nginx phiên bản mới nhất làm base image
FROM nginx:latest

# 2. (Tùy chọn) Xóa các file mặc định của Nginx để đảm bảo sạch sẽ
RUN rm -rf /usr/share/nginx/html/*

# 3. Sao chép file index.html từ máy tính vào thư mục mặc định của Nginx trong container
COPY index.html /usr/share/nginx/html/index.html

# 4. Nginx mặc định chạy trên cổng 80
EXPOSE 80

# 5. Không cần lệnh CMD vì image nginx:latest đã tự động chạy nginx khi khởi động