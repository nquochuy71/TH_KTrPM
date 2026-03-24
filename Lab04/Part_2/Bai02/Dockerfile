# 1. Sử dụng Python 3.9 làm base image
FROM python:3.9

# 2. Thiết lập thư mục làm việc trong container là /app
WORKDIR /app

# 3. Sao chép file requirements.txt vào container trước
COPY requirements.txt .

# 4. Cài đặt các thư viện cần thiết
RUN pip install --no-cache-dir -r requirements.txt

# 5. Sao chép toàn bộ code còn lại vào container
COPY . .

# 6. Thông báo container sẽ dùng cổng 5000
EXPOSE 5000

# 7. Lệnh để chạy ứng dụng khi container khởi động
CMD ["python", "app.py"]