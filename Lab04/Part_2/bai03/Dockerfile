# 1. Sử dụng node:18-alpine làm base image để giảm dung lượng
FROM node:18-alpine

# 2. Thiết lập thư mục làm việc
WORKDIR /app

# 3. Sao chép package.json và package-lock.json (nếu có)
COPY package*.json ./

# 4. Cài đặt dependencies
RUN npm install

# 5. Sao chép toàn bộ mã nguồn vào container
COPY . .

# 6. React mặc định chạy trên cổng 3000
EXPOSE 3000

# 7. Lệnh khởi chạy ứng dụng ở chế độ development
CMD ["npm", "start"]