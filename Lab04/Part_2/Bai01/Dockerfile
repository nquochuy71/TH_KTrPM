# 1. Sử dụng node:18 làm base image theo yêu cầu
FROM node:18

# 2. Thiết lập thư mục làm việc bên trong container
WORKDIR /app

# 3. Sao chép package.json vào trước để tận dụng Docker cache cho các node_modules
COPY package.json ./

# 4. Cài đặt các dependencies (dù hiện tại ta chưa dùng thư viện ngoài)
RUN npm install

# 5. Sao chép toàn bộ mã nguồn còn lại vào container
COPY . .

# 6. Thông báo container sẽ lắng nghe ở cổng 3000
EXPOSE 3000

# 7. Lệnh để khởi chạy ứng dụng
CMD ["npm", "start"]